package com.example.main.services.appointmenttype;

import com.example.main.dtos.AppointmentTypeDTO;
import com.example.main.entities.Appointment;
import com.example.main.entities.AppointmentType;
import com.example.main.entities.Doctor;
import com.example.main.entities.Room;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.InvalidDataException;
import com.example.main.repositories.AppointmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppointmentTypeService implements IAppointmentTypeService{
    private final AppointmentTypeRepository appointmentTypeRepository;

    private final ModelMapper modelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AppointmentType createAppointmentType(AppointmentTypeDTO appointmentTypeDTO) throws IdNotFoundException {

        AppointmentType appointmentType = new AppointmentType();

        modelMapper.map(appointmentTypeDTO, appointmentType);

        return appointmentTypeRepository.save(appointmentType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AppointmentType updateAppointmentType(Long appointmentTypeId, AppointmentTypeDTO appointmentTypeDTO) throws IdNotFoundException {
        AppointmentType existingAppointmentType = appointmentTypeRepository
                .findByAppointmentTypeIdAndIsDeletedFalse(appointmentTypeId)
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment type id is not found")
                );

        modelMapper.map(appointmentTypeDTO, existingAppointmentType);

        return appointmentTypeRepository.save(existingAppointmentType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAppointmentType(Long appointmentTypeId) throws IdNotFoundException {
        AppointmentType existingAppointmentType = appointmentTypeRepository
                .findByAppointmentTypeIdAndIsDeletedFalse(appointmentTypeId)
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment type id is not found")
                );
        existingAppointmentType.setDeleted(true);
        appointmentTypeRepository.save(existingAppointmentType);
    }

    @Override
    public AppointmentType getAppointmentType(Long appointmentTypeId) throws IdNotFoundException {
        return appointmentTypeRepository
                .findByAppointmentTypeIdAndIsDeletedFalse(appointmentTypeId)
                .orElseThrow(
                        () -> new IdNotFoundException("Appointment type id " + appointmentTypeId + " is not found")
                );
    }

    @Override
    public List<AppointmentType> getAppointmentTypes()  {
        return appointmentTypeRepository.findAll();
    }
}
