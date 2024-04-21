package com.example.main.services.appointment;

import com.example.main.dtos.AppointmentDTO;
import com.example.main.entities.Appointment;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.InvalidDataException;
import lombok.extern.java.Log;

import java.util.List;

public interface IAppointmentService {
    Appointment createAppointment(AppointmentDTO appointmentDTO) throws IdNotFoundException, InvalidDataException;
    Appointment bookingAppointment(Long appointmentId, Long patientId) throws IdNotFoundException;
    void cancellationOfBooking(Long appointmentId) throws IdNotFoundException;
    Appointment updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) throws IdNotFoundException;
    void deleteAppointment(Long appointmentId) throws IdNotFoundException;
    Appointment getAppointment(Long appointmentId) throws IdNotFoundException;
    List<Appointment> getAppointments();
    List<Appointment> getAppointmentsByPatientId(Long patientId);
    List<Appointment> getAppointmentsByDoctorId(Long doctorId);
}
