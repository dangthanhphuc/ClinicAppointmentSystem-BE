package com.example.main.services.doctor;

import com.example.main.dtos.DoctorDTO;
import com.example.main.dtos.DoctorRegisterAccountDTO;
import com.example.main.entities.Doctor;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.NotMatchException;
import com.example.main.exceptions.PayloadTooLargeException;
import com.example.main.exceptions.UnsupportedMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDoctorService {
    Doctor createDoctor(DoctorDTO doctorDTO) throws IdNotFoundException;
    Doctor updateDoctor(Long doctorId, DoctorDTO doctorDTO) throws IdNotFoundException;
    void deleteDoctor(Long doctorId) throws IdNotFoundException;
    Doctor getDoctor(Long doctorId) throws IdNotFoundException;
    List<Doctor> getDoctors() ;
    void uploadImage(Long doctorId, MultipartFile file) throws IdNotFoundException, IOException, UnsupportedMediaTypeException, PayloadTooLargeException;
    Doctor registerAccount(DoctorRegisterAccountDTO doctorRegisterAccountDTO) throws IdNotFoundException, NotMatchException;
}
