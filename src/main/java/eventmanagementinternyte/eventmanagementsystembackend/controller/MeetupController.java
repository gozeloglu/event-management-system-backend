package eventmanagementinternyte.eventmanagementsystembackend.controller;


import eventmanagementinternyte.eventmanagementsystembackend.dto.MeetupDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.MeetupMapper;
import eventmanagementinternyte.eventmanagementsystembackend.service.MeetupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/meetups")
public class MeetupController {
    private final MeetupService meetupService;
    private final MeetupMapper meetupMapper;

    @GetMapping
    public List<MeetupDTO> listAllMeetups() {
        List<Meetup> meetupList = meetupService.listAllMeetups();
        return meetupMapper.mapToDto(meetupList);
    }

    @PostMapping(value = "/create-new-meetup")
    public MeetupDTO addNewMeetup(@Valid @RequestBody MeetupDTO meetupDTO) {
        Meetup meetup = meetupMapper.mapToEntity(meetupDTO);
        Meetup newMeetup = meetupService.saveNewMeetup(meetup);
        return meetupMapper.mapToDto(newMeetup);
    }

    @PutMapping(value = "/update-meetup/{meetupID}")
    public MeetupDTO updateMeetup(@PathVariable Long meetupID,
                                  @Valid @RequestBody MeetupDTO meetupDTO) {
        Meetup newMeetup = meetupMapper.mapToEntity(meetupDTO);
        Meetup updatedMeetup = meetupService.updateMeetup(meetupID, newMeetup);
        return meetupMapper.mapToDto(updatedMeetup);
    }

    @DeleteMapping(value = "/delete-meetup/{meetupID}")
    public void deleteMeetup(@PathVariable Long meetupID) {
        meetupService.deleteMeetup(meetupID);
    }

    @GetMapping(value = "/{meetupID}")
    public MeetupDTO getMeetup(@PathVariable Long meetupID) {
        return meetupService.getMeetup(meetupID);
    }
}