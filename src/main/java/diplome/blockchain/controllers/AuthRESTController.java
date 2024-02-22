package diplome.blockchain.controllers;

import diplome.blockchain.model.Receiver;
import diplome.blockchain.model.Transmitter;
import diplome.blockchain.repository.ReceiverRepository;
import diplome.blockchain.repository.TransmitterRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import diplome.blockchain.message.request.LoginForm;
import diplome.blockchain.message.request.SignUpForm;
import diplome.blockchain.message.response.JwtResponse;
import diplome.blockchain.message.response.ResponseMessage;
import diplome.blockchain.model.Role;
import diplome.blockchain.model.RoleName;
import diplome.blockchain.model.User;
import diplome.blockchain.repository.RoleRepository;
import diplome.blockchain.repository.UserRepository;
import diplome.blockchain.security.jwt.JwtProvider;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/auth")
public class AuthRESTController {

    private DaoAuthenticationProvider daoAuthenticationProvider;
    private UserRepository userRepository;
    private ReceiverRepository receiverRepository;
    private TransmitterRepository transmitterRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;

    @Autowired
    public AuthRESTController(DaoAuthenticationProvider daoAuthenticationProvider, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, ReceiverRepository receiverRepository, TransmitterRepository transmitterRepository) {
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.receiverRepository = receiverRepository;
        this.transmitterRepository = transmitterRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        Authentication authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(), userDetails.getAuthorities()));
    }
    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<User> findAllUsers() { return userRepository.findAll();
    }
    @RequestMapping(value="/{username}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser (@PathVariable("username") String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("Receiver not found!");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        userRepository.findByUsername(username).get().getRoles().clear();
        userRepository.deleteById(user.get().getId());
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken."), HttpStatus.BAD_REQUEST);
        }
        // Create user account
        User user = new User(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail -> Cause: Admin Role not found."));
                    roles.add(adminRole);
                    break;
                case "receiver":
                    Role receiverRole = roleRepository.findByName(RoleName.ROLE_RECEIVER)
                            .orElseThrow(() -> new RuntimeException("Fail -> Cause: receiver Role not found."));
                    roles.add(receiverRole);
                    Receiver receiver = new Receiver(signUpRequest.getFirstname(),signUpRequest.getLastname(),
                            signUpRequest.getEmail(), signUpRequest.getTelephone(), signUpRequest.getUsername());
                    receiverRepository.save(receiver);
                    break;
                case "transmitter":
                    Role transmitterRole = roleRepository.findByName(RoleName.ROLE_TRANSMITTER)
                            .orElseThrow(() -> new RuntimeException("Fail -> Cause: transmitter Role not found."));
                    roles.add(transmitterRole);
                    Transmitter transmitter = new Transmitter(signUpRequest.getFirstname(),signUpRequest.getLastname(),
                            signUpRequest.getEmail(), signUpRequest.getTelephone(), signUpRequest.getUsername());
                    transmitterRepository.save(transmitter);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);



        return new ResponseEntity<>(new ResponseMessage("User registered successfully."), HttpStatus.OK);

    }

}
