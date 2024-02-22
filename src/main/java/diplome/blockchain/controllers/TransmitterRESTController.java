package diplome.blockchain.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import diplome.blockchain.model.Transmitter;
import diplome.blockchain.repository.TransmitterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/transmitters")
public class TransmitterRESTController {

    private TransmitterRepository transmitterRepository;
    @Autowired
    public TransmitterRESTController(TransmitterRepository transmitterRepository) {
        this.transmitterRepository = transmitterRepository;
    }

    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Transmitter> findAllTransmitters() { return transmitterRepository.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Transmitter> getTransmitter (@PathVariable("id") long id) {
        Transmitter transmitter = transmitterRepository.findById(id);
        if (transmitter == null) {
            System.out.println("Transmitter not found!");
            return new ResponseEntity<Transmitter>(transmitter,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Transmitter>(transmitter,HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Transmitter> addTransmitter(@RequestBody Transmitter transmitter) {
        transmitterRepository.save(transmitter);
        return new ResponseEntity<Transmitter>(transmitter, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Transmitter> deleteTransmitter (@PathVariable("id") long id) {
        Transmitter transmitter = transmitterRepository.findById(id);
        if (transmitter == null) {
            System.out.println("Transmitter not found!");
            return new ResponseEntity<Transmitter>(HttpStatus.NOT_FOUND);
        }
        transmitterRepository.deleteById(id);
        return new ResponseEntity<Transmitter>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Transmitter> deleteTransmitters(/*@RequestBody Transmitter transmitter*/) {
        transmitterRepository.deleteAll();
        return new ResponseEntity<Transmitter>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<Transmitter> updateTransmitter(@RequestBody Transmitter transmitter, @PathVariable("id") long id) {
        transmitter.setId(id);
        transmitterRepository.save(transmitter);
        return new ResponseEntity<Transmitter>(transmitter,HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Transmitter> updateAllTransmitters(@RequestBody List<Transmitter> updates) {
        transmitterRepository.deleteAll();
        for(Transmitter s : updates){
            transmitterRepository.save(s);
        }
        return new ResponseEntity<Transmitter>(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    //@PatchMapping("/{id}")
    public ResponseEntity<Transmitter> updatePartOfTransmitter(@RequestBody Map<String, Object> updates, @PathVariable("id") long id) {
        Transmitter transmitter = transmitterRepository.findById(id);
        if (transmitter == null) {
            System.out.println("Transmitter not found!");
            return new ResponseEntity<Transmitter>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(transmitter,updates);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");
        return new ResponseEntity<Transmitter>(HttpStatus.NO_CONTENT);
    }

    private void partialUpdate(Transmitter transmitter, Map<String, Object> updates) {
        if (updates.containsKey("firstname")) {
            transmitter.setFirstname((String) updates.get("firstname"));
        }
        if (updates.containsKey("lastname")) {
            transmitter.setLastname((String) updates.get("lastname"));
        }
        if (updates.containsKey("email")) {
            transmitter.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("telephone")) {
            transmitter.setTelephone((String) updates.get("telephone"));
        }
        if (updates.containsKey("identifier")) {
            transmitter.setIdentifier((String) updates.get("identifier"));
        }
        transmitterRepository.save(transmitter);
    }
}
