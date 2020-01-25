package pl.edu.utp.wtie.rejestracja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.utp.wtie.rejestracja.model.Appointment;


/**
 * AppointmentController
 */
@Controller
public class AppointmentController {

    @PostMapping("/addAppointment")
    public String addAppointment(@RequestParam Appointment appointment) {
        return "doctor-scheduler";
    }
    
}