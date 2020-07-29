package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import eventmanagementinternyte.eventmanagementsystembackend.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    /**
     * Lists all participants in the database.
     * @return List<Participant> that includes all participants
     * */
    public List<Participant> listAllParticipants() {
        return participantRepository.findAll();
    }

    /**
     * Saves a new participant to the database
     * @param participant is an entity that stores the new participant's information.
     * @return Participant object after saved it.
     * */
    public Participant saveNewParticipantToDB(Participant participant) {
        return participantRepository.save(participant);
    }
}
