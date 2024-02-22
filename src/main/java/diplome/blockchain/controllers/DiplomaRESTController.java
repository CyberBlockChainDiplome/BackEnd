package diplome.blockchain.controllers;

import diplome.blockchain.repository.TransmitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import diplome.blockchain.model.Diploma;
import diplome.blockchain.model.Transmitter;
import diplome.blockchain.repository.DiplomaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/diplomas")
public class DiplomaRESTController {

    private DiplomaRepository diplomaRepository;

    private TransmitterRepository transmitterRepository;
    @Autowired
    public DiplomaRESTController(DiplomaRepository diplomaRepository, TransmitterRepository transmitterRepository) {
        this.diplomaRepository = diplomaRepository;
        this.transmitterRepository = transmitterRepository;
    }

    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Diploma> findAllDiplomas() { return diplomaRepository.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Diploma> getDiploma (@PathVariable("id") long id) {
        Diploma diploma = diplomaRepository.findById(id);
        if (diploma == null) {
            System.out.println("Diploma not found!");
            return new ResponseEntity<Diploma>(diploma,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Diploma>(diploma,HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Diploma> addDiploma(@RequestBody Diploma diploma) {
        diplomaRepository.save(diploma);
        return new ResponseEntity<Diploma>(diploma, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Diploma> deleteDiploma (@PathVariable("id") long id) {
        Diploma diploma = diplomaRepository.findById(id);
        if (diploma == null) {
            System.out.println("Diploma not found!");
            return new ResponseEntity<Diploma>(HttpStatus.NOT_FOUND);
        }
        diplomaRepository.deleteById(id);
        return new ResponseEntity<Diploma>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Diploma> deleteDiplomas(/*@RequestBody Diploma diploma*/) {
        diplomaRepository.deleteAll();
        return new ResponseEntity<Diploma>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<Diploma> updateDiploma(@RequestBody Diploma diploma, @PathVariable("id") long id) {

        Transmitter transmitter = transmitterRepository.findById(diploma.getTransmitter().getId());
        diploma.setId(id);
        diploma.setTransmitter(transmitter);
        diplomaRepository.save(diploma);
        return new ResponseEntity<Diploma>(diploma,HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Diploma> updateAllDiplomas(@RequestBody List<Diploma> updates) {
        diplomaRepository.deleteAll();
        for(Diploma s : updates){
            diplomaRepository.save(s);
        }
        return new ResponseEntity<Diploma>(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Diploma> updatePartOfDiploma(@RequestBody Map<String, Object> updates, @PathVariable("id") long id) {
        Diploma diploma = diplomaRepository.findById(id);
        if (diploma == null) {
            System.out.println("Diploma not found!");
            return new ResponseEntity<Diploma>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(diploma,updates);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");
        return new ResponseEntity<Diploma>(HttpStatus.NO_CONTENT);
    }

    private void partialUpdate(Diploma diploma, Map<String, Object> updates) {
        if (updates.containsKey("diplomaName")) {
            diploma.setDiplomaName((String) updates.get("diplomaName"));
        }
        if (updates.containsKey("transmitter")) {
            Map<String, Object> transmitterUpdates = (Map<String, Object>) updates.get("transmitter");
            Long transmitterId = ((Integer) transmitterUpdates.get("id")).longValue();

            if (transmitterId > 0) {
                if (transmitterRepository.existsById(transmitterId)) {
                    Transmitter existingTransmitter = diploma.getTransmitter();
                    if (existingTransmitter == null || existingTransmitter.getId() != transmitterId) {
                        Optional<Transmitter> optionalTransmitter = transmitterRepository.findById(transmitterId);
                        Transmitter transmitter = optionalTransmitter.get();
                        diploma.setTransmitter(transmitter);
                    }
                } else {
                    // Transmitter with the given ID doesn't exist
                    System.out.println("Transmitter not found!");
                }
            } else {
                // Invalid transmitter ID
                System.out.println("Invalid transmitter ID!");
            }
        }
        diplomaRepository.save(diploma);
    }

}
