package com.example.main.services.appointmenttype;


import com.example.main.dtos.AppointmentDTO;
import com.example.main.dtos.AppointmentTypeDTO;
import com.example.main.entities.Appointment;
import com.example.main.entities.AppointmentType;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.InvalidDataException;

import java.util.List;

public interface IAppointmentTypeService {
    AppointmentType createAppointmentType(AppointmentTypeDTO appointmentTypeDTO) throws IdNotFoundException;
    AppointmentType updateAppointmentType(Long appointmentTypeId, AppointmentTypeDTO appointmentTypeDTO) throws IdNotFoundException;
    void deleteAppointmentType(Long appointmentTypeId) throws IdNotFoundException;
    AppointmentType getAppointmentType(Long appointmentTypeId) throws IdNotFoundException;
    List<AppointmentType> getAppointmentTypes() ;
}
