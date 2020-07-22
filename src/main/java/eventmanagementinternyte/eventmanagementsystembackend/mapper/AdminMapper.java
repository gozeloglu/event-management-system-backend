package eventmanagementinternyte.eventmanagementsystembackend.mapper;

import eventmanagementinternyte.eventmanagementsystembackend.dto.AdminDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Admin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDTO mapToDto(Admin admin);

    Admin mapToEntity(AdminDTO adminDTO);

    List<AdminDTO> mapToDto(List<Admin> adminList);

    List<Admin> mapToEntity(List<AdminDTO> adminDTOList);
}
