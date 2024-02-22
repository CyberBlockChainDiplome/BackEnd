package diplome.blockchain.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/security")
public class SecurityRESTController {

    @GetMapping("/receiver")
    @PreAuthorize("hasRole('RECEIVER') or hasRole('ADMIN')")
    public String receiverAccess() {
        return ">>> RECEIVER Contents!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return ">>> ADMIN Contents";
    }

    @GetMapping("/transmitter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRANSMITTER')")
    public String transmitterAccess() {
        return ">>> TRANSMITTER Contents";
    }

}
