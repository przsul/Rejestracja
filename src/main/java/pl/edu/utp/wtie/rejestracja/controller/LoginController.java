package pl.edu.utp.wtie.rejestracja.controller;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.ModelAndView;
import pl.edu.utp.wtie.rejestracja.model.*;
import pl.edu.utp.wtie.rejestracja.repository.AppointmentRepository;
import pl.edu.utp.wtie.rejestracja.repository.DoctorRepository;
import pl.edu.utp.wtie.rejestracja.repository.PatientRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/signin")
    public String showSignInForm(Login login) {
        return "sign-in";
    }

    @GetMapping("/panel")
    public String showLoggedPanel(HttpSession session, Model model) {
        if (session.getAttribute("doctor-logged") != null){
            return "doctor-panel";
        }

        if (session.getAttribute("patient-logged") != null){
            SearchDoctorModel searchDoctorModel = new SearchDoctorModel();
            model.addAttribute("searchDoctor", searchDoctorModel);
            return "patient-panel";
        }



        return "index";
    }
    @PostMapping("/panel")
    public ModelAndView searchDoctor(@Valid SearchDoctorModel searchDoctorModel, BindingResult result, ModelMap model, HttpSession session){
        if (result.hasErrors()){
            model.addAttribute("searchDoctor", searchDoctorModel);
            return new ModelAndView("patient-panel", model);
        }
        Map<Doctor, List<Appointment>> doctorsWithVisitsMap = new HashMap<>();

//        List<Doctor> searchedDoctors = doctorRepository.findByFirstNameOrLastNameOrCity(searchDoctorModel.getDoctorFirstName(),searchDoctorModel.getDoctorLastName(),searchDoctorModel.getCity());
//        searchedDoctors.forEach((element) -> {
//            doctorsWithVisitsMap.put(element, appointmentRepository.findAppointmentsByDoctorOrderByStartDateTimeDesc(element));
//        });
        List<Appointment> appointmentsWithDoctor = appointmentRepository.findByDoctorFirstNameOrDoctorLastNameOrDoctorCityOrDoctorSpecializationOrderByStartDateTimeDesc(searchDoctorModel.getDoctorFirstName(),searchDoctorModel.getDoctorLastName(),searchDoctorModel.getCity(), "Ginekolog");

        System.out.println(appointmentsWithDoctor);
        model.addAttribute("searchDoctor", searchDoctorModel);
        model.addAttribute("AppointmentsWithDoctor", appointmentsWithDoctor);
        return new ModelAndView("patient-panel", model);
    }



    @GetMapping("/")
    public String dontBack(HttpSession session, Model model) {
        if (session.getAttribute("doctor-logged") != null)
            return "doctor-panel";
        if (session.getAttribute("patient-logged") != null)
            return "patient-panel";

        return "index";
    }

    @PostMapping("/signin")
    public String showPanel(@Valid Login login, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors())
            return "sign-in";

        Iterable<Doctor> doctors = doctorRepository.findAll();
        for (Doctor d : doctors)
            if (d.getEmail().equals(login.getEmail()))
                if (passwordEncoder.matches(login.getPassword(), d.getPassword())) {
                    request.getSession().setAttribute("doctor-logged", login.getEmail());
                    return "redirect:/panel";
                }

        Iterable<Patient> patients = patientRepository.findAll();
        for (Patient p : patients)
            if (p.getEmail().equals(login.getEmail()))
                if (passwordEncoder.matches(login.getPassword(), p.getPassword())) {
                    request.getSession().setAttribute("patient-logged", login.getEmail());
                    return "redirect:/panel";
                }

        result.rejectValue("email", "error.login", "An account does not exists for this email.");
        return "sign-in";
    }

    @GetMapping("/signout")
    public String signout(HttpSession session) {
        if (session.getAttribute("patient-logged") != null)
            session.removeAttribute("patient-logged");
        if (session.getAttribute("doctor-logged") != null)
            session.removeAttribute("doctor-logged");

        return "index";
    }
}