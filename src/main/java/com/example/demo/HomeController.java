package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {
@Autowired
KidRepository kidRepository;

@Autowired
    MomRepository momRepository;
@RequestMapping("/")
public String listkids(Model model){
    model.addAttribute("kids", kidRepository.findAll());
    model.addAttribute("moms", momRepository.findAll());
    return "list";
}
    @GetMapping("/add")
    public String kidsForm(Model model){
        model.addAttribute("kids", new Kid());
        model.addAttribute("moms", momRepository.findAll());
        return "kidform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Kid kid, BindingResult result,
                              Model model){
        if(result.hasErrors()){
            model.addAttribute("moms", momRepository.findAll());
            return "kidform";
        }
        kidRepository.save(kid);
        return "redirect:/";
    }
    @GetMapping("/addmom")
    public String momForm(Model model){
        model.addAttribute("mom", new Mom());
        return "mom";
    }

    @PostMapping("/processmom")
    public String processMom(@Valid Mom mom, BindingResult result,
                                 Model model){
        if(result.hasErrors()){
            return "mom";
        }
        if(momRepository.findByName(mom.getName()) != null){
            model.addAttribute("message", "You already have a mom called " +
                    mom.getName() + "!" + " Try something else.");
            return "subject";
        }
        momRepository.save(mom);
        return "redirect:/";
    }




    @RequestMapping("/detail/{id}")
    public String showKid(@PathVariable("id") long id, Model model){
        model.addAttribute("kid", kidRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateKid(@PathVariable("id") long id, Model model){
        model.addAttribute("moms", momRepository.findAll());
        model.addAttribute("kid", momRepository.findById(id).get());
        return "kidform";
    }

    @RequestMapping("/delete/{id}")
    public String delKid(@PathVariable("id") long id){
        kidRepository.deleteById(id);
        return "redirect:/";
    }

}


}

