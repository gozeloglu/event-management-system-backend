package eventmanagementinternyte.eventmanagementsystembackend.mapper;

import eventmanagementinternyte.eventmanagementsystembackend.dto.ParticipantDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {

    ParticipantDTO mapToDto(Participant participant);

    Participant mapToEntity(ParticipantDTO participantDTO);

    List<ParticipantDTO> mapToDto(List<Participant> participantList);

    List<Participant> mapToEntity(List<ParticipantDTO> participantDTOList);
}
