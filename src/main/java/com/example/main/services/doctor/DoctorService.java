package com.example.main.services.doctor;

import com.example.main.dtos.DoctorDTO;
import com.example.main.dtos.DoctorRegisterAccountDTO;
import com.example.main.entities.*;
import com.example.main.entities.Doctor;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.NotMatchException;
import com.example.main.exceptions.PayloadTooLargeException;
import com.example.main.exceptions.UnsupportedMediaTypeException;
import com.example.main.repositories.ClinicRepository;
import com.example.main.repositories.DoctorRepository;
import com.example.main.repositories.RoleRepository;
import com.example.main.repositories.UserRepository;
import com.example.main.utils.StoreImageFile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorService implements IDoctorService{
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final ClinicRepository clinicRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional(rollbackOn =  Exception.class)
    @Override
    public Doctor createDoctor(DoctorDTO doctorDTO) throws IdNotFoundException {
        Clinic existingClinic = clinicRepository
                .findByClinicIdAndIsDeletedFalse(doctorDTO.getClinicId())
                .orElseThrow(
                        () -> new IdNotFoundException("Clinic id " + doctorDTO.getClinicId() + " is not found")
                );

        Doctor doctor = Doctor.builder()
                .email(doctorDTO.getEmail())
                .name(doctorDTO.getName())
                .address(doctorDTO.getAddress())
                .phoneNumber(doctorDTO.getPhoneNumber())
                .dateOfBirth(doctorDTO.getDateOfBirth())
                .gender(doctorDTO.isGender())
                .clinic(existingClinic)
                .build();

        return doctorRepository.save(doctor);
    }

    @Transactional(rollbackOn =  Exception.class)
    @Override
    public Doctor updateDoctor(Long doctorId, DoctorDTO doctorDTO) throws IdNotFoundException {
        Doctor existingDoctor = doctorRepository
                .findByDoctorIdAndIsDeletedFalse(doctorId)
                .orElseThrow(
                        () -> new IdNotFoundException("Doctor id " + doctorId + " not found")
                );

        Clinic existingClinic = clinicRepository
                .findByClinicIdAndIsDeletedFalse(doctorDTO.getClinicId())
                .orElseThrow(
                        () -> new IdNotFoundException("Clinic id " + doctorDTO.getClinicId() + " is not found")
                );

        existingDoctor.setEmail(doctorDTO.getEmail());
        existingDoctor.setName(doctorDTO.getName());
        existingDoctor.setAddress(doctorDTO.getAddress());
        existingDoctor.setPhoneNumber(doctorDTO.getPhoneNumber());
        existingDoctor.setDateOfBirth(doctorDTO.getDateOfBirth());
        existingDoctor.setGender(doctorDTO.isGender());
        existingDoctor.setClinic(existingClinic);

        return doctorRepository.save(existingDoctor);
    }

    @Transactional(rollbackOn =  Exception.class)
    @Override
    public void deleteDoctor(Long doctorId) throws IdNotFoundException {
        Doctor existingDoctor = doctorRepository
                .findByDoctorIdAndIsDeletedFalse(doctorId)
                .orElseThrow(
                        () -> new IdNotFoundException("Doctor id " + doctorId + " not found")
                );
        existingDoctor.setDeleted(true);
        doctorRepository.save(existingDoctor);
    }

    @Override
    public Doctor getDoctor(Long doctorId) throws IdNotFoundException {
        return doctorRepository
                .findByDoctorIdAndIsDeletedFalse(doctorId)
                .orElseThrow(
                        () -> new IdNotFoundException("Doctor id " + doctorId + " not found")
                );
    }

    @Override
    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional(rollbackOn =  Exception.class)
    @Override
    public void uploadImage(Long doctorId, MultipartFile file) throws IdNotFoundException, IOException, UnsupportedMediaTypeException, PayloadTooLargeException {
        Doctor existingDoctor = doctorRepository
                .findByDoctorIdAndIsDeletedFalse(doctorId)
                .orElseThrow(
                        () -> new IdNotFoundException("Doctor id " + doctorId + " not found")
                );

        String filename = StoreImageFile.storeImage(file);

        existingDoctor.setImgUrl(filename);

        doctorRepository.save(existingDoctor);
    }

    @Override
    public Doctor registerAccount(DoctorRegisterAccountDTO doctorRegisterAccountDTO) throws IdNotFoundException, NotMatchException {

        Doctor existingDoctor = doctorRepository
                .findByDoctorIdAndIsDeletedFalse(doctorRegisterAccountDTO.getDoctorId())
                .orElseThrow(
                        () -> new IdNotFoundException("Doctor id is not found !")
                );

        // Kiểm tra clinic id có match không ?
        if(!doctorRegisterAccountDTO.getClinicId().equals(existingDoctor.getClinic().getClinicId())){
            throw new NotMatchException("Clinic id of input not match with existing doctor !");
        }

        Role role = roleRepository.findById(1L)
                .orElseThrow(
                        () -> new IdNotFoundException("Role id is not found !")
                );

        // Encode password
        String passwordEncoded = passwordEncoder.encode(doctorRegisterAccountDTO.getPassword());
        // Tạo User
        User user = User.builder()
                .userName(doctorRegisterAccountDTO.getUsername())
                .password(passwordEncoded)
                .role(role)
                .build();

        userRepository.save(user);

        // Tạo Doctor Account
        existingDoctor.setUser(user);
        return doctorRepository.save(existingDoctor);
    }

}
