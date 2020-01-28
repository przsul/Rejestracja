package pl.edu.utp.wtie.rejestracja.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import pl.edu.utp.wtie.rejestracja.model.Appointment;
import pl.edu.utp.wtie.rejestracja.model.Doctor;
import pl.edu.utp.wtie.rejestracja.repository.AppointmentRepository;
import pl.edu.utp.wtie.rejestracja.repository.DoctorRepository;


/**
 * AppointmentController
 */
@Controller
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping("/addAppointment")
    public String addAppointment(Appointment appointment, HttpSession session, Model model) {

        Doctor doctor = doctorRepository.findByEmail((String)session.getAttribute("doctor-logged"));
        appointment.setDoctor(doctor);
        appointment.setDayOfTheWeek();
        appointmentRepository.save(appointment);

        List<Appointment> appointments = appointmentRepository.findByDoctorOrderByStartDateTime(doctor);
        model.addAttribute("doctorAppointments", appointments);
        
        return "doctor-scheduler";
    }
    
}