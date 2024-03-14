package diplome.blockchain.controllers;

import diplome.blockchain.model.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SubjectController {

    @RequestMapping("/diploma")
    public String diploma(Model model){
        model.addAttribute("message","Simple String from DiplomaController.");
        Subject newSubject = new Subject();
        model.addAttribute("diploma", newSubject);
        return "diploma";
    }
    @RequestMapping (value = "/addDiploma.html", method = RequestMethod.POST )
    public String addDiploma(@ModelAttribute("diploma") Subject subject){
        System.out.println(subject.getDiplomaName());
        return "redirect:diploma";

    }
}
