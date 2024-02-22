package diplome.blockchain.controllers;

import diplome.blockchain.model.Transmitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TransmitterController {

    @RequestMapping("/transmitter")
    public String transmitter(Model model){
        model.addAttribute("message","Simple String from TransmitterController.");
        Transmitter newTransmitter = new Transmitter();
        model.addAttribute("transmitter", newTransmitter);
        return "transmitter";
    }
    @RequestMapping (value = "/addTransmitter.html", method = RequestMethod.POST )
    public String addTransmitter(@ModelAttribute("transmitter") Transmitter transmitter){
        System.out.println(transmitter.getFirstname() + " " + transmitter.getLastname() +
                " " + transmitter.getEmail() + " " + transmitter.getTelephone());
        return "redirect:transmitter";

    }
}
