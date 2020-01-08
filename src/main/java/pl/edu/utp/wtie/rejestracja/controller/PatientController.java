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
 * PatientController
 */
@Controller
public class PatientController {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/patient/signup")
    public String showSignUpForm(Patient patient) {
        return "patient-sign-up";
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

    @GetMapping("/patient/add")
    public String addPatient() {
        return "index";
    }

    @PostMapping("/patient/add")
    public String addPatient(@Valid Patient patient, BindingResult result, Model model) {
        if (result.hasErrors())
            return "patient-sign-up";

        Iterable<Doctor> doctors = doctorRepository.findAll();
        for (Doctor d : doctors)
            if (d.getEmail().equals(patient.getEmail())) {
                result.rejectValue("email", "error.patient", "An account already exists for this email.");
                return "patient-sign-up";
            }

        try {
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            patientRepository.save(patient);
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("email", "error.patient", "An account already exists for this email.");
            return "patient-sign-up";
        }

        return "patient-panel";
    }
}