package com.example.main.services.patient;


import com.example.main.dtos.PatientDTO;
import com.example.main.dtos.PatientRegisterAccountDTO;
import com.example.main.entities.Patient;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.PayloadTooLargeException;
import com.example.main.exceptions.UnsupportedMediaTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPatientService {
    Patient registerPatient(PatientRegisterAccountDTO patientRegisterAccountDTO) throws IdNotFoundException;
    Patient updatePatient(Long patientId, PatientDTO patientDTO) throws IdNotFoundException;
    void deletePatient(Long patientId) throws IdNotFoundException;
    Patient getPatient(Long patientId) throws IdNotFoundException;
    List<Patient> getPatients() ;
    void uploadImage(Long patientId, MultipartFile file) throws IdNotFoundException, IOException, UnsupportedMediaTypeException, PayloadTooLargeException;

}
