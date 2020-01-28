package pl.edu.utp.wtie.rejestracja.controller;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.utp.wtie.rejestracja.model.Doctor;
import pl.edu.utp.wtie.rejestracja.model.Patient;
import pl.edu.utp.wtie.rejestracja.repository.DoctorRepository;
import pl.edu.utp.wtie.rejestracja.repository.PatientRepository;

/**
 * PatientController
 */
@Controller
public class PatientController {

    @Autowired
    private JavaMailSender mailSender;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/patient/signup")
    public String showSignUpForm(Patient patient) {
        return "patient-sign-up";
    }

    @PostMapping("/patient/add")
    public ModelAndView addPatient(@Valid Patient patient, BindingResult result, ModelMap model, HttpServletRequest request) {
        if (result.hasErrors())
            return new ModelAndView("patient-sign-up", model);

        Iterable<Doctor> doctors = doctorRepository.findAll();
        for (Doctor d : doctors)
            if (d.getEmail().equals(patient.getEmail())) {
                result.rejectValue("email", "error.patient", "An account already exists for this email.");
                return new ModelAndView("patient-sign-up", model);
            }

        String passwordTmp = patient.getPassword();

        try {
            patient.setPassword(passwordEncoder.encode(patient.getPassword()));
            patientRepository.save(patient);
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("email", "error.patient", "An account already exists for this email.");
            return new ModelAndView("patient-sign-up", model);
        }

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(patient.getEmail()));
            mimeMessage.setFrom(new InternetAddress("zpo85876@gmail.com", "ZPO PROJECT"));
            mimeMessage.setSubject("Registration");
            mimeMessage.setText("Thanks <b>" + patient.getFirstName() + "</b> for your registration!<br><br>" + "<b>You login:</b> "
                    + patient.getEmail() + "<br><b>Your password:</b> " + passwordTmp, "utf-8", "html");
        };

        try {
            mailSender.send(preparator);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }

        request.getSession().setAttribute("patient-logged", patient.getEmail());
        return new ModelAndView("redirect:/panel", model);
    }
}