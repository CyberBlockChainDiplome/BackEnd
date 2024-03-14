package diplome.blockchain.controllers;

import diplome.blockchain.model.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SubjectController {

    @RequestMapping("/subject")
    public String subject(Model model){
        model.addAttribute("message","Simple String from SubjectController.");
        Subject newSubject = new Subject();
        model.addAttribute("subject", newSubject);
        return "subject";
    }
    @RequestMapping (value = "/addSubject.html", method = RequestMethod.POST )
    public String addSubject(@ModelAttribute("subject") Subject subject){
        System.out.println(subject.getSubjectName());
        return "redirect:subject";

    }
}
