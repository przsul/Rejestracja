package pl.edu.utp.wtie.rejestracja.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import pl.edu.utp.wtie.rejestracja.model.Doctor;
import pl.edu.utp.wtie.rejestracja.model.Patient;
import pl.edu.utp.wtie.rejestracja.repository.DoctorRepository;
import pl.edu.utp.wtie.rejestracja.repository.PatientRepository;

/**
 * DoctorController
 */
@Controller
public class DoctorController {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/doctor/signup")
    public String showSignUpForm(Doctor doctor) {
        return "doctor-sign-up";
    }

    // @PostMapping("/userPanel")
    // public String userPanel(@Valid User user, BindingResult result, Model model)
    // {
    // if (result.hasErrors())
    // return "user-sign-up";

    // Iterable<User> users = userRepository.findAll();
    // for(User u : users)
    // if(u.getEmail().equals(user.getEmail()))
    // if(u.getPassword().equals(user.getPassword()))
    // return "user-panel";

    // return "index";
    // }

    @GetMapping("/doctor/add")
    public String addDoctor() {
        return "index";
    }

    @PostMapping("/doctor/add")
    public String addDoctor(@Valid Doctor doctor, BindingResult result, Model model) {
        if (result.hasErrors())
            return "doctor-sign-up";

        Iterable<Patient> patients = patientRepository.findAll();
        for (Patient p : patients)
            if (p.getEmail().equals(doctor.getEmail())) {
                result.rejectValue("email", "error.doctor", "An account already exists for this email.");
                return "doctor-sign-up";
            }

        try {
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            doctorRepository.save(doctor);
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("email", "error.doctor", "An account already exists for this email.");
            return "doctor-sign-up";
        }

        return "doctor-panel";
    }
}