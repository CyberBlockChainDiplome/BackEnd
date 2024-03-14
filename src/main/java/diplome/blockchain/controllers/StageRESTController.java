package diplome.blockchain.controllers;
import diplome.blockchain.model.Subject;
import diplome.blockchain.repository.ReceiverRepository;
import diplome.blockchain.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import diplome.blockchain.model.Stage;
import diplome.blockchain.model.Receiver;
import diplome.blockchain.repository.StageRepository;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/stages")
public class StageRESTController {

    private StageRepository stageRepository;
    private ReceiverRepository receiverRepository;
    private SubjectRepository subjectRepository;
    @Autowired
    public StageRESTController(StageRepository stageRepository, ReceiverRepository receiverRepository, SubjectRepository subjectRepository) {
        this.stageRepository = stageRepository;
        this.receiverRepository = receiverRepository;
        this.subjectRepository = subjectRepository;
    }

    @RequestMapping(method = RequestMethod.GET/*, produces = "application/xml"*/)
    //@GetMapping
    public List<Stage> findAllStages() { return stageRepository.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Stage> getStage (@PathVariable("id") long id) {
        Stage stage = stageRepository.findById(id);
        if (stage == null) {
            System.out.println("Stage not found!");
            return new ResponseEntity<Stage>(stage,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Stage>(stage,HttpStatus.FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    //@PostMapping
    public ResponseEntity<Stage> addStage(@RequestBody Stage stage) {
        stageRepository.save(stage);
        return new ResponseEntity<Stage>(stage, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    //@DeleteMapping("/{id}")
    public ResponseEntity<Stage> deleteStage (@PathVariable("id") long id) {
        Stage stage = stageRepository.findById(id);
        if (stage == null) {
            System.out.println("Stage not found!");
            return new ResponseEntity<Stage>(HttpStatus.NOT_FOUND);
        }
        stageRepository.deleteById(id);
        return new ResponseEntity<Stage>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Stage> deleteStages(/*@RequestBody Stage stage*/) {
        stageRepository.deleteAll();
        return new ResponseEntity<Stage>(HttpStatus.NO_CONTENT);
    }


    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    //@PutMapping("/{id}")
    public ResponseEntity<Stage> updateStage(@RequestBody Stage stage, @PathVariable("id") long id) {
        Receiver receiver = receiverRepository.findById(stage.getReceiver().getId());
        Subject subject = subjectRepository.findById(stage.getDiploma().getId()).orElse(null);
        stage.setId(id);
        stage.setReceiver(receiver);
        stage.setDiploma(subject);
        subjectRepository.save(subject);
        return new ResponseEntity<Stage>(stage,HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Stage> updateAllStages(@RequestBody List<Stage> updates) {
        stageRepository.deleteAll();
        for(Stage s : updates){
            stageRepository.save(s);
        }
        return new ResponseEntity<Stage>(HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Stage> updatePartOfStage(@RequestBody Map<String, Object> updates, @PathVariable("id") long id) {
        Stage stage = stageRepository.findById(id);
        if (stage == null) {
            System.out.println("Stage not found!");
            return new ResponseEntity<Stage>(HttpStatus.NOT_FOUND);
        }
        partialUpdate(stage,updates);
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");
        return new ResponseEntity<Stage>(HttpStatus.NO_CONTENT);
    }


    private void partialUpdate(Stage stage, Map<String, Object> updates) {
        if (updates.containsKey("value")) {
            stage.setValue((Double) updates.get("value"));
        }
        stageRepository.save(stage);
    }
}
