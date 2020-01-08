package pl.edu.utp.wtie.rejestracja.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pl.edu.utp.wtie.rejestracja.model.Doctor;
import pl.edu.utp.wtie.rejestracja.model.Login;
import pl.edu.utp.wtie.rejestracja.model.Patient;
import pl.edu.utp.wtie.rejestracja.repository.DoctorRepository;
import pl.edu.utp.wtie.rejestracja.repository.PatientRepository;

/**
 * LoginController
 */
@Controller
public class LoginController {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/signin")
    public String showSignInForm(Login login) {
        return "sign-in";
    }

    @PostMapping("/panel")
    public String showPanel(@Valid Login login, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors())
            return "sign-in";

        Iterable<Doctor> doctors = doctorRepository.findAll();
        for (Doctor d : doctors)
            if (d.getEmail().equals(login.getEmail()))
                if (passwordEncoder.matches(login.getPassword(), d.getPassword()))
                    return "doctor-panel";

        Iterable<Patient> patients = patientRepository.findAll();
        for (Patient p : patients)
            if (p.getEmail().equals(login.getEmail()))
                if (passwordEncoder.matches(login.getPassword(), p.getPassword()))
                    return "patient-panel";

        return "index";
    }
}