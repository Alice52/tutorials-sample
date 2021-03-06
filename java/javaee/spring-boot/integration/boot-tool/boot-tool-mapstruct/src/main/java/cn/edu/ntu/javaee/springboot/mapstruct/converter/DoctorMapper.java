package cn.edu.ntu.javaee.springboot.mapstruct.converter;

import cn.edu.ntu.javaee.springboot.mapstruct.model.Doctor;
import cn.edu.ntu.javaee.springboot.mapstruct.model.DoctorDto;
import cn.edu.ntu.javaee.springboot.mapstruct.model.Education;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author zack <br>
 * @create 2020-11-08 23:11 <br>
 * @project springboot <br>
 */
@Mapper(uses = PatientMapper.class)
public interface DoctorMapper {
    DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    @Mapping(source = "doctor.specialty", target = "specialization")
    @Mapping(source = "doctor.patientList", target = "patientDtoList")
    DoctorDto toDto(Doctor doctor);

    @Mapping(source = "doctor.specialty", target = "specialization")
    @Mapping(source = "education.degreeName", target = "degree")
    DoctorDto toDto(Doctor doctor, Education education);
}
