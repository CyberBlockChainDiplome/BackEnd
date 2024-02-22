package diplome.blockchain.controllers;

import diplome.blockchain.model.Receiver;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReceiverController {

    @RequestMapping("/receiver")
    public String receiver(Model model){
        model.addAttribute("message","Simple String from ReceiverController.");
        Receiver newReceiver = new Receiver();
        model.addAttribute("receiver", newReceiver);
        return "receiver";
    }
    @RequestMapping (value = "/addReceiver.html", method = RequestMethod.POST )
    public String addReceiver(@ModelAttribute("receiver") Receiver receiver){
        System.out.println(receiver.getFirstname() + " " + receiver.getLastname() +
                " " + receiver.getEmail() + " " + receiver.getTelephone());
        return "redirect:receiver";

    }
}
