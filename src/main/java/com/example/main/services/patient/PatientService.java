package com.example.main.services.patient;

import com.example.main.dtos.PatientDTO;
import com.example.main.dtos.PatientRegisterAccountDTO;
import com.example.main.entities.*;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.PayloadTooLargeException;
import com.example.main.exceptions.UnsupportedMediaTypeException;
import com.example.main.repositories.PatientRepository;
import com.example.main.repositories.RoleRepository;
import com.example.main.repositories.UserRepository;
import com.example.main.utils.StoreImageFile;
import io.jsonwebtoken.security.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService implements IPatientService{
    private final PatientRepository patientRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Patient registerPatient(PatientRegisterAccountDTO patientRegisterAccountDTO) throws IdNotFoundException {
        Role role = roleRepository.findById(2L)
                .orElseThrow(
                        () -> new IdNotFoundException("Role id PATIENT is not found")
                );

        // Encode Password
        String passwordEncoded = passwordEncoder.encode(patientRegisterAccountDTO.getPassword());
        User user = User.builder()
                .userName(patientRegisterAccountDTO.getUsername())
                .password(passwordEncoded)
                .role(role)
                .build();

        userRepository.save(user);

        Patient patient = Patient.builder()
                .email(patientRegisterAccountDTO.getEmail())
                .name(patientRegisterAccountDTO.getName())
                .address(patientRegisterAccountDTO.getAddress())
                .phoneNumber(patientRegisterAccountDTO.getPhoneNumber())
                .dateOfBirth(patientRegisterAccountDTO.getDateOfBirth())
                .gender(patientRegisterAccountDTO.isGender())
                .user(user)
                .build();

        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long patientId, PatientDTO patientDTO) throws IdNotFoundException {
        Patient existingPatient = patientRepository
                .findByPatientIdAndIsDeletedFalse(patientId)
                .orElseThrow(
                        () -> new IdNotFoundException("Patient id " + patientId + " not found")
                );


        existingPatient.setEmail(patientDTO.getEmail());
        existingPatient.setName(patientDTO.getName());
        existingPatient.setAddress(patientDTO.getAddress());
        existingPatient.setPhoneNumber(patientDTO.getPhoneNumber());
        existingPatient.setDateOfBirth(patientDTO.getDateOfBirth());
        existingPatient.setGender(patientDTO.isGender());

        return patientRepository.save(existingPatient);
    }

    @Override
    public void deletePatient(Long patientId) throws IdNotFoundException {
        Patient existingPatient = patientRepository
                .findByPatientIdAndIsDeletedFalse(patientId)
                .orElseThrow(
                        () -> new IdNotFoundException("Patient id " + patientId + " not found")
                );
        existingPatient.setDeleted(true);
        patientRepository.save(existingPatient);
    }

    @Override
    public Patient getPatient(Long patientId) throws IdNotFoundException {
        return patientRepository
                .findByPatientIdAndIsDeletedFalse(patientId)
                .orElseThrow(
                        () -> new IdNotFoundException("Patient id " + patientId + " not found")
                );
    }

    @Override
    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    @Override
    public void uploadImage(Long patientId, MultipartFile file) throws IdNotFoundException, IOException, UnsupportedMediaTypeException, PayloadTooLargeException {
        Patient existingPatient = patientRepository
                .findByPatientIdAndIsDeletedFalse(patientId)
                .orElseThrow(
                        () -> new IdNotFoundException("Patient id " + patientId + " not found")
                );

        String filename = StoreImageFile.storeImage(file);

        existingPatient.setImgUrl(filename);

        patientRepository.save(existingPatient);
    }
}
