package pl.edu.utp.wtie.rejestracja.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.utp.wtie.rejestracja.model.Appointment;
import pl.edu.utp.wtie.rejestracja.model.Doctor;
import pl.edu.utp.wtie.rejestracja.model.DoctorSettings;
import pl.edu.utp.wtie.rejestracja.model.Patient;
import pl.edu.utp.wtie.rejestracja.repository.AppointmentRepository;
import pl.edu.utp.wtie.rejestracja.repository.DoctorRepository;
import pl.edu.utp.wtie.rejestracja.repository.PatientRepository;

/**
 * DoctorController
 */
@Controller
public class DoctorController {

    @Autowired
    private JavaMailSender mailSender;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/doctor/signup")
    public String showSignUpForm(Doctor doctor) {
        return "doctor-sign-up";
    }

    @GetMapping("/panel/scheduler")
    public String showDoctorScheduler(Appointment appointment, HttpSession session, Model model) {
        if(session.getAttribute("doctor-logged") == null)
            return "index";

        String doctorEmail = (String)session.getAttribute("doctor-logged");
        List<Appointment> doctorAppointments = new ArrayList<>();

        Iterable<Appointment> appointments = appointmentRepository.findAll();

        for(Appointment a : appointments)
            if(doctorEmail.equals(a.getDoctor().getEmail()))
                doctorAppointments.add(a);
        
        model.addAttribute("doctorAppointments", doctorAppointments);

        return "doctor-scheduler";
    }

    @PostMapping("/doctor/add")
    public ModelAndView addDoctor(@Valid Doctor doctor, BindingResult result, ModelMap model, HttpServletRequest request) {
        if (result.hasErrors())
            return new ModelAndView("doctor-sign-up", model);

        Iterable<Patient> patients = patientRepository.findAll();
        for (Patient p : patients)
            if (p.getEmail().equals(doctor.getEmail())) {
                result.rejectValue("email", "error.doctor", "An account already exists for this email.");
                return new ModelAndView("doctor-sign-up", model);
            }

        String passwordTmp = doctor.getPassword();

        try {
            doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
            doctorRepository.save(doctor);
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("email", "error.doctor", "An account already exists for this email.");
            return new ModelAndView("doctor-sign-up", model);
        }

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(doctor.getEmail()));
            mimeMessage.setFrom(new InternetAddress("zpo85876@gmail.com", "ZPO PROJECT"));
            mimeMessage.setSubject("Registration");
            mimeMessage.setText("Thanks <b>" + doctor.getFirstName() + "</b> for your registration!<br><br>" + "<b>You login:</b> "
                    + doctor.getEmail() + "<br><b>Your password:</b> " + passwordTmp, "utf-8", "html");
        };

        try {
            mailSender.send(preparator);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }

        request.getSession().setAttribute("doctor-logged", doctor.getEmail());
        return new ModelAndView("redirect:/panel", model);
    }

    @GetMapping("/panel/doctor-settings")
    public String showsDoctorSettings(HttpSession session, Model model){
        if(session.getAttribute("doctor-logged") == null)
            return "index";
        String doctorEmail = (String)session.getAttribute("doctor-logged");
        Doctor doctor = doctorRepository.findByEmail(doctorEmail);
        model.addAttribute("doctor", doctor);
        return "doctor-settings";
    }
    @PostMapping("/panel/doctor-settings")
    public ModelAndView saveDoctorSettings(@Valid DoctorSettings doctorSettings, BindingResult result, ModelMap model, HttpSession session){
        String doctorEmail = (String)session.getAttribute("doctor-logged");
        Doctor currentDoctor = doctorRepository.findByEmail(doctorEmail);
        if (result.hasErrors()){
            model.addAttribute("doctor", currentDoctor);
            return new ModelAndView("doctor-settings", model);
        }
        currentDoctor.setSpecialization(doctorSettings.getSpecialization());
        currentDoctor.setCity(doctorSettings.getCity());
        currentDoctor.setPhone(doctorSettings.getPhone());
        currentDoctor.setCurrentHospital(doctorSettings.getCurrentHospital());
        doctorRepository.save(currentDoctor);
        return new ModelAndView("redirect:/panel", model);
    }
}