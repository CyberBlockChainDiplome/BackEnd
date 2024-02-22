package diplome.blockchain.controllers;

import diplome.blockchain.model.Diploma;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DiplomaController {

    @RequestMapping("/diploma")
    public String diploma(Model model){
        model.addAttribute("message","Simple String from DiplomaController.");
        Diploma newDiploma = new Diploma();
        model.addAttribute("diploma", newDiploma);
        return "diploma";
    }
    @RequestMapping (value = "/addDiploma.html", method = RequestMethod.POST )
    public String addDiploma(@ModelAttribute("diploma") Diploma diploma){
        System.out.println(diploma.getDiplomaName());
        return "redirect:diploma";

    }
}
