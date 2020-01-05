package pl.edu.utp.wtie.rejestracja.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pl.edu.utp.wtie.rejestracja.model.User;
import pl.edu.utp.wtie.rejestracja.repository.UserRepository;

/**
 * UserController
 */
@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "user-sign-up";
    }

    // @PostMapping("/userPanel")
    // public String userPanel(@Valid User user, BindingResult result, Model model) {
    //     if (result.hasErrors())
    //         return "user-sign-up";
         
    //     Iterable<User> users = userRepository.findAll();
    //     for(User u : users)
    //         if(u.getEmail().equals(user.getEmail()))
    //             if(u.getPassword().equals(user.getPassword()))
    //                 return "user-panel";
        
    //     return "index";
    // }
    
    @PostMapping("/addUser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors())
            return "user-sign-up";
        
        try {
            userRepository.save(user);
        } catch(DataIntegrityViolationException e) {
            result.rejectValue("email", "error.user", "An account already exists for this email.");
            return "user-sign-up";
        }
        
        return "user-panel";
    }
}