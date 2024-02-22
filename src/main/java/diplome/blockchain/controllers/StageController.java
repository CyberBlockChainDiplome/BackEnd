package diplome.blockchain.controllers;

import diplome.blockchain.model.Stage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StageController {

    @RequestMapping("/stage")
    public String stage(Model model){
        model.addAttribute("message","Simple String from StageController.");
        Stage newStage = new Stage();
        model.addAttribute("stage", newStage);
        return "stage";
    }
    @RequestMapping (value = "/addStage.html", method = RequestMethod.POST )
    public String addStage(@ModelAttribute("stage") Stage stage){
        System.out.println(stage.getValue());
        return "redirect:stage";

    }
}
