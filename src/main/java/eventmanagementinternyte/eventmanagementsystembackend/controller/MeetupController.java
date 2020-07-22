package eventmanagementinternyte.eventmanagementsystembackend.controller;


import eventmanagementinternyte.eventmanagementsystembackend.dto.MeetupDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.MeetupMapper;
import eventmanagementinternyte.eventmanagementsystembackend.service.MeetupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
///  @RequestMapping(value = "/meetups")
public class MeetupController {
    private final MeetupService meetupService;
    private final MeetupMapper meetupMapper;

    @GetMapping(value = "/meetups")
    public List<MeetupDTO> listAllMeetups() {
        System.out.println("List all meetups!");
        List<Meetup> meetupList = meetupService.listAllMeetups();
        return meetupMapper.mapToDto(meetupList);
    }

    @PostMapping(value = "/meetups/createNewMeetup")
    public MeetupDTO addNewMeetup(@Valid @RequestBody MeetupDTO meetupDTO) {
        System.out.println("Add new Meetup Controller");
        System.out.println(meetupDTO);
        Meetup meetup = meetupMapper.mapToEntity(meetupDTO);
        Meetup newMeetup = meetupService.saveNewMeetup(meetup);
        return meetupMapper.mapToDto(newMeetup);
    }
}
