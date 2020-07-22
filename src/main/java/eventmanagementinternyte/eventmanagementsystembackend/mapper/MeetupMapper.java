package eventmanagementinternyte.eventmanagementsystembackend.mapper;

import eventmanagementinternyte.eventmanagementsystembackend.dto.MeetupDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MeetupMapper {
    MeetupDTO mapToDto(Meetup event);

    Meetup mapToEntity(MeetupDTO meetupDTO);

    List<MeetupDTO> mapToDto(List<Meetup> meetupList);

    List<Meetup> mapToEntity(List<MeetupDTO> meetupDTOList);
}
