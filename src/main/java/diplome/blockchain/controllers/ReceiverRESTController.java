package diplome.blockchain.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import diplome.blockchain.model.Receiver;
import diplome.blockchain.repository.ReceiverRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/receivers")
public class ReceiverRESTController {

    private ReceiverRepository receiverRepository;
    @Autowired
    public ReceiverRESTController(ReceiverRepository receiverRepository) {
        this.receiverRepository = receiverRepository;
    }

    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Receiver> findAllReceivers() { return receiverRepository.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Receiver> getReceiver (@PathVariable("id") long id) {
        Receiver receiver = receiverRepository.findById(id);
        if (receiver == null) {
            System.out.println("Receiver not found!");
            return new ResponseEntity<Receiver>(receiver,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Receiver>(receiver,HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Receiver> addReceiver(@RequestBody Receiver receiver) {
        receiverRepository.save(receiver);
        return new ResponseEntity<Receiver>(receiver, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Receiver> deleteReceiver (@PathVariable("id") long id) {
        Receiver receiver = receiverRepository.findById(id);
        if (receiver == null) {
            System.out.println("Receiver not found!");
            return new ResponseEntity<Receiver>(HttpStatus.NOT_FOUND);
        }
        receiverRepository.deleteById(id);
        return new ResponseEntity<Receiver>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Receiver> deleteReceivers(/*@RequestBody Receiver receiver*/) {
        receiverRepository.deleteAll();
        return new ResponseEntity<Receiver>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<Receiver> updateReceiver(@RequestBody Receiver receiver, @PathVariable("id") long id) {
        receiver.setId(id);
        receiverRepository.save(receiver);
        return new ResponseEntity<Receiver>(receiver,HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Receiver> updateAllReceivers(@RequestBody List<Receiver> updates) {
        receiverRepository.deleteAll();
        for(Receiver s : updates){
            receiverRepository.save(s);
        }
        return new ResponseEntity<Receiver>(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    //@PatchMapping("/{id}")
    public ResponseEntity<Receiver> updatePartOfReceiver(@RequestBody Map<String, Object> updates, @PathVariable("id") long id) {
        Receiver receiver = receiverRepository.findById(id);
        if (receiver == null) {
            System.out.println("Receiver not found!");
            return new ResponseEntity<Receiver>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(receiver,updates);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");
        return new ResponseEntity<Receiver>(HttpStatus.NO_CONTENT);
    }

    private void partialUpdate(Receiver receiver, Map<String, Object> updates) {
        if (updates.containsKey("firstname")) {
            receiver.setFirstname((String) updates.get("firstname"));
        }
        if (updates.containsKey("lastname")) {
            receiver.setLastname((String) updates.get("lastname"));
        }
        if (updates.containsKey("email")) {
            receiver.setEmail((String) updates.get("email"));
        }
        if (updates.containsKey("telephone")) {
            receiver.setTelephone((String) updates.get("telephone"));
        }
        if (updates.containsKey("identifier")) {
            receiver.setIdentifier((String) updates.get("identifier"));
        }
        receiverRepository.save(receiver);
    }
}
