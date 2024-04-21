package com.example.main.services.appointment;

import com.example.main.dtos.AppointmentDTO;
import com.example.main.entities.*;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.InvalidDataException;
import com.example.main.repositories.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentService implements IAppointmentService{
    private final RoomRepository roomRepository;
    private final AppointmentTypeRepository appointmentTypeRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;

    private final ModelMapper modelMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Appointment createAppointment(AppointmentDTO appointmentDTO) throws IdNotFoundException, InvalidDataException {
        AppointmentType existingAppointmentType = appointmentTypeRepository
                .findByAppointmentTypeIdAndIsDeletedFalse(appointmentDTO.getAppointmentTypeId())
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment type id not found !")
                );
        Doctor existingDoctor = doctorRepository
                .findByDoctorIdAndIsDeletedFalse(appointmentDTO.getDoctorId())
                .orElseThrow(
                        () -> new IdNotFoundException("Doctor id not found !")
                );
        Room existingRoom = roomRepository
                .findByRoomIdAndIsDeletedFalse(appointmentDTO.getRoomId())
                .orElseThrow(
                        () -> new IdNotFoundException("Room id not found !")
                );

        if(appointmentDTO.getAvailableTimeStart().isAfter(appointmentDTO.getAvailableTimeEnd())) {
            throw new InvalidDataException("Appointment start datetime cannot is after appointment end datetime");
        }

        Appointment appointment = Appointment.builder()
                .availableTimeStart(appointmentDTO.getAvailableTimeStart())
                .availableTimeEnd(appointmentDTO.getAvailableTimeEnd())
                .status(appointmentDTO.getStatus())
                .noteDoctor(appointmentDTO.getNoteDoctor())
                .appointmentType(existingAppointmentType)
                .doctor(existingDoctor)
                .room(existingRoom)
                .build();

        return appointmentRepository.save(appointment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Appointment bookingAppointment(Long appointmentId, Long patientId) throws IdNotFoundException {
        Appointment existingAppointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment id not found !")
                );
        Patient existingPatient = patientRepository
                .findById(patientId)
                .orElseThrow(
                        () -> new IdNotFoundException("Patient id not found !")
                );
        existingAppointment.setPatient(existingPatient);
        return appointmentRepository.save(existingAppointment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Appointment updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) throws IdNotFoundException {
        Appointment existingAppointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment id not found !")
                );

        AppointmentType existingAppointmentType = appointmentTypeRepository
                .findByAppointmentTypeIdAndIsDeletedFalse(appointmentDTO.getAppointmentTypeId())
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment type id not found !")
                );

        Doctor existingDoctor = doctorRepository
                .findByDoctorIdAndIsDeletedFalse(appointmentDTO.getDoctorId())
                .orElseThrow(
                        () -> new IdNotFoundException("Doctor id not found !")
                );

        Room existingRoom = roomRepository
                .findByRoomIdAndIsDeletedFalse(appointmentDTO.getRoomId())
                .orElseThrow(
                        () -> new IdNotFoundException("Room id not found !")
                );

        existingAppointment.setAvailableTimeStart(appointmentDTO.getAvailableTimeStart());
        existingAppointment.setAvailableTimeEnd(appointmentDTO.getAvailableTimeEnd());
        existingAppointment.setStatus(appointmentDTO.getStatus());
        existingAppointment.setNoteDoctor(appointmentDTO.getNoteDoctor());
        existingAppointment.setRoom(existingRoom);
        existingAppointment.setAppointmentType(existingAppointmentType);
        existingAppointment.setDoctor(existingDoctor);

        return appointmentRepository.save(existingAppointment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAppointment(Long appointmentId) throws IdNotFoundException {
        Appointment existingAppointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment id not found !")
                );
        appointmentRepository.delete(existingAppointment);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancellationOfBooking(Long appointmentId)
            throws IdNotFoundException {
        Appointment existingAppointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment id not found !")
                );
        existingAppointment.setPatient(null);
        appointmentRepository.save(existingAppointment);
    }

    @Override
    public Appointment getAppointment(Long appointmentId) throws IdNotFoundException {
        return appointmentRepository
                .findById(appointmentId)
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment id not found !")
                );
    }


    @Override
    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

}
