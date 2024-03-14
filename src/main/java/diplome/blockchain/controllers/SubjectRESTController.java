package diplome.blockchain.controllers;

import diplome.blockchain.repository.TransmitterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import diplome.blockchain.model.Subject;
import diplome.blockchain.model.Transmitter;
import diplome.blockchain.repository.SubjectRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/diplomas")
public class SubjectRESTController {

    private SubjectRepository subjectRepository;

    private TransmitterRepository transmitterRepository;
    @Autowired
    public SubjectRESTController(SubjectRepository subjectRepository, TransmitterRepository transmitterRepository) {
        this.subjectRepository = subjectRepository;
        this.transmitterRepository = transmitterRepository;
    }

    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Subject> findAllDiplomas() { return subjectRepository.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Subject> getDiploma (@PathVariable("id") long id) {
        Subject subject = subjectRepository.findById(id);
        if (subject == null) {
            System.out.println("Diploma not found!");
            return new ResponseEntity<Subject>(subject,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Subject>(subject,HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Subject> addDiploma(@RequestBody Subject subject) {
        subjectRepository.save(subject);
        return new ResponseEntity<Subject>(subject, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Subject> deleteDiploma (@PathVariable("id") long id) {
        Subject subject = subjectRepository.findById(id);
        if (subject == null) {
            System.out.println("Diploma not found!");
            return new ResponseEntity<Subject>(HttpStatus.NOT_FOUND);
        }
        subjectRepository.deleteById(id);
        return new ResponseEntity<Subject>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Subject> deleteDiplomas(/*@RequestBody Diploma diploma*/) {
        subjectRepository.deleteAll();
        return new ResponseEntity<Subject>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<Subject> updateDiploma(@RequestBody Subject subject, @PathVariable("id") long id) {

        Transmitter transmitter = transmitterRepository.findById(subject.getTransmitter().getId());
        subject.setId(id);
        subject.setTransmitter(transmitter);
        subjectRepository.save(subject);
        return new ResponseEntity<Subject>(subject,HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Subject> updateAllDiplomas(@RequestBody List<Subject> updates) {
        subjectRepository.deleteAll();
        for(Subject s : updates){
            subjectRepository.save(s);
        }
        return new ResponseEntity<Subject>(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Subject> updatePartOfDiploma(@RequestBody Map<String, Object> updates, @PathVariable("id") long id) {
        Subject subject = subjectRepository.findById(id);
        if (subject == null) {
            System.out.println("Diploma not found!");
            return new ResponseEntity<Subject>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(subject,updates);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");
        return new ResponseEntity<Subject>(HttpStatus.NO_CONTENT);
    }

    private void partialUpdate(Subject subject, Map<String, Object> updates) {
        if (updates.containsKey("diplomaName")) {
            subject.setDiplomaName((String) updates.get("diplomaName"));
        }
        if (updates.containsKey("transmitter")) {
            Map<String, Object> transmitterUpdates = (Map<String, Object>) updates.get("transmitter");
            Long transmitterId = ((Integer) transmitterUpdates.get("id")).longValue();

            if (transmitterId > 0) {
                if (transmitterRepository.existsById(transmitterId)) {
                    Transmitter existingTransmitter = subject.getTransmitter();
                    if (existingTransmitter == null || existingTransmitter.getId() != transmitterId) {
                        Optional<Transmitter> optionalTransmitter = transmitterRepository.findById(transmitterId);
                        Transmitter transmitter = optionalTransmitter.get();
                        subject.setTransmitter(transmitter);
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
        subjectRepository.save(subject);
    }

}
