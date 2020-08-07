package eventmanagementinternyte.eventmanagementsystembackend.controller;

import eventmanagementinternyte.eventmanagementsystembackend.dto.MeetupDTO;
import eventmanagementinternyte.eventmanagementsystembackend.dto.ParticipantDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.MeetupMapper;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.ParticipantMapper;
import eventmanagementinternyte.eventmanagementsystembackend.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/participants")
public class ParticipantController {

    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;
    private final MeetupMapper meetupMapper;

    @GetMapping
    public List<ParticipantDTO> listAllParticipants() {
        // TODO Control the empty participants
        List<Participant> participantList = participantService.listAllParticipants();
        return participantMapper.mapToDto(participantList);
    }

    @PostMapping(value = "/create-new-participant")
    public ParticipantDTO saveNewParticipantToDB(@Valid @RequestBody ParticipantDTO participantDTO) {
        Participant participant = participantMapper.mapToEntity(participantDTO);
        Participant newParticipant = participantService.saveNewParticipantToDB(participant);
        return participantMapper.mapToDto(newParticipant);
    }

    @GetMapping(value = "/all-meetups/{username}")
    public List<MeetupDTO> listAllMeetups(@PathVariable String username) {
        try {
            Set<Meetup> meetups = participantService.getParticipantMeetups(username);
            return meetupMapper.mapToDto(new ArrayList<>(meetups));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @PostMapping("/add-meetup/{username}")
    public MeetupDTO addNewMeetupToParticipant(@PathVariable String username, @RequestBody MeetupDTO meetupDTO) {
        Meetup newMeetup = participantService.addMeetupToParticipant(username, meetupMapper.mapToEntity(meetupDTO));
        return meetupMapper.mapToDto(newMeetup);
    }

    @DeleteMapping("/delete-participant/{username}")
    public void deleteParticipant(@PathVariable String username) {
        participantService.deleteParticipant(username);
    }

    @PostMapping("/register-participant/{username}/{meetupID}")
    public String registerParticipantToMeetup(@PathVariable String username, @PathVariable String meetupID) {
        return participantService.registerToMeetup(username, meetupID);
    }

    @PostMapping("/login")
    public ParticipantDTO login(@Valid @RequestBody Participant participant) throws Exception {
        return participantService.login(participant.getUsername(), participant.getPassword());
    }

    @GetMapping("/all-meetups")
    public List<MeetupDTO> getAllMeetups() {
        // TODO Control the empty meetup
        List<Meetup> meetupList = participantService.getAllMeetups();
        return meetupMapper.mapToDto(meetupList);
    }

    @GetMapping("/meetup-detail/{meetupID}")
    public MeetupDTO getMeetupDetail(@PathVariable String meetupID) {
        return participantService.getMeetupDetail(meetupID);
    }

    @GetMapping("/participant-detail/{username}")
    public ParticipantDTO getParticipantDetails(@PathVariable String username) {
        return participantService.getParticipantDetails(username);
    }

    @PutMapping("/update-profile/{identityNumber}")
    public String updateProfile(@PathVariable String identityNumber, @Valid @RequestBody Participant participant) {
        return participantService.updateParticipant(identityNumber, participant);
    }

    @PostMapping("/unregister-participant/{username}/{meetupID}")
    public String unregisterMeetup(@PathVariable String username, @PathVariable String meetupID) {
        return participantService.unregisterMeetup(username, meetupID);
    }
}