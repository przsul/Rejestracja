package pl.edu.utp.wtie.rejestracja.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pl.edu.utp.wtie.rejestracja.model.Admin;
import pl.edu.utp.wtie.rejestracja.repository.AdminRepository;

/**
 * AdminController
 */
@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin")
    public String showSignUpForm(Admin admin) {
        return "admin-sign-in";
    }

    @PostMapping("/adminPanel")
    public String adminPanel(@Valid Admin admin, BindingResult result, Model model) {
        if (result.hasErrors())
            return "admin-sign-in";
         
        Iterable<Admin> admins = adminRepository.findAll();
        for(Admin a : admins)
            if(a.getEmail().equals(admin.getEmail()))
                if(a.getPassword().equals(admin.getPassword()))
                    return "admin-panel";
        
        return "index";
    }
}