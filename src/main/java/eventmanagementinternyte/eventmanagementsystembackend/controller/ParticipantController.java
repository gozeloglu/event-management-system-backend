package eventmanagementinternyte.eventmanagementsystembackend.controller;

import eventmanagementinternyte.eventmanagementsystembackend.dto.ParticipantDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.ParticipantMapper;
import eventmanagementinternyte.eventmanagementsystembackend.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/participants")
public class ParticipantController {

    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;

    @GetMapping
    public List<ParticipantDTO> listAllParticipants() {
        List<Participant> participantList = participantService.listAllParticipants();
        return participantMapper.mapToDto(participantList);
    }

    @PostMapping(value = "/create-new-participant")
    public ParticipantDTO saveNewParticipantToDB(@Valid @RequestBody ParticipantDTO participantDTO) {
        Participant participant = participantMapper.mapToEntity(participantDTO);
        Participant newParticipant = participantService.saveNewParticipantToDB(participant);
        return participantMapper.mapToDto(newParticipant);
    }
}
