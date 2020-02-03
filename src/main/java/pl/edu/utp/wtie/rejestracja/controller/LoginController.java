package pl.edu.utp.wtie.rejestracja.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.utp.wtie.rejestracja.model.Appointment;
import pl.edu.utp.wtie.rejestracja.model.Doctor;
import pl.edu.utp.wtie.rejestracja.model.Login;
import pl.edu.utp.wtie.rejestracja.model.Patient;
import pl.edu.utp.wtie.rejestracja.model.SearchDoctorModel;
import pl.edu.utp.wtie.rejestracja.repository.AppointmentRepository;
import pl.edu.utp.wtie.rejestracja.repository.DoctorRepository;
import pl.edu.utp.wtie.rejestracja.repository.PatientRepository;

import java.util.Date;
import java.util.List;

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
    public String showLoggedPanel(HttpSession session, Model model, @Valid SearchDoctorModel searchDoctorModel) {
        if (session.getAttribute("doctor-logged") != null){
            Doctor currentDoctor = doctorRepository.findByEmail(session.getAttribute("doctor-logged").toString());
            Long time = new Date().getTime();
            Date date = new Date(time - time % (24 * 60 * 60 * 1000));
            List<Appointment> todayVisits = appointmentRepository.findByDoctorAndStartDateTimeGreaterThanOrderByStartDateTimeDesc(currentDoctor, date);
            System.out.println(date);
            System.out.println(todayVisits);
            model.addAttribute("todayVisits", todayVisits);
            return "doctor-panel";
        }

        if (session.getAttribute("patient-logged") != null){
            model.addAttribute("searchDoctor", searchDoctorModel);
            return "patient-panel";
        }
        return "index";
    }

    @PostMapping("/panel/{page}")
    public ModelAndView searchDoctor(@Valid SearchDoctorModel searchDoctorModel, BindingResult result, ModelMap model, @PathVariable int page){
        if (result.hasErrors()){
            model.addAttribute("searchDoctor", searchDoctorModel);
            return new ModelAndView("patient-panel", model);
        }
        Page<Appointment> appointmentsWithDoctor = appointmentRepository.findByDoctorFirstNameOrDoctorLastNameOrDoctorCityOrDoctorSpecializationOrderByStartDateTimeDesc(searchDoctorModel.getDoctorFirstName(),searchDoctorModel.getDoctorLastName(),searchDoctorModel.getCity(), searchDoctorModel.getSpecialization(), PageRequest.of(page, 10));
        model.addAttribute("searchDoctor", searchDoctorModel);
        model.addAttribute("AppointmentsWithDoctor", appointmentsWithDoctor);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", appointmentsWithDoctor.getTotalPages());
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

        Doctor doctorByEmail = doctorRepository.findByEmail(login.getEmail());
        Patient patientByEmail = patientRepository.findByEmail(login.getEmail());

        if (doctorByEmail != null) {
            if (passwordEncoder.matches(login.getPassword(), doctorByEmail.getPassword())) {
                request.getSession().setAttribute("doctor-logged", doctorByEmail.getEmail());
                return "redirect:/panel";
            } else {
                result.rejectValue("password", "error.login", "Podano złe hasło");
            }
        } else if (patientByEmail != null) {
            if (passwordEncoder.matches(login.getPassword(), patientByEmail.getPassword())) {
                request.getSession().setAttribute("patient-logged", patientByEmail.getEmail());
                return "redirect:/panel";
            } else {
                result.rejectValue("password", "error.login", "Podano złe hasło");
            }
        } else {
            result.rejectValue("email", "error.login", "Nie znaleziono takiego maila w bazie danych");
        }
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