package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.dto.MeetupDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import eventmanagementinternyte.eventmanagementsystembackend.repository.MeetupRepository;
import eventmanagementinternyte.eventmanagementsystembackend.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final MeetupRepository meetupRepository;

    /**
     * Lists all participants in the database.
     *
     * @return List<Participant> that includes all participants
     */
    public List<Participant> listAllParticipants() {
        return participantRepository.findAll();
    }

    /**
     * Saves a new participant to the database
     *
     * @param participant is an entity that stores the new participant's information.
     * @return Participant object after saved it.
     */
    public Participant saveNewParticipantToDB(Participant participant) {
        return participantRepository.save(participant);
    }

    public Set<Meetup> getParticipantMeetups(String username) {
       /* try {
            Participant participant = participantRepository.findByUsername(username);
            if (participant.isNull()) {
                Set<Meetup> set = new HashSet<>();
                return set;
            } else {
                return participant.getMeetups();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Set<Meetup> set = new HashSet<>();
            return set;
        }
        */
        return participantRepository.findByUsername(username).map(Participant::getMeetups)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Meetup addMeetupToParticipant (String username, Meetup meetup) {
        Optional<Participant> meetupOptional = participantRepository.findByUsername(username);
        if (meetupOptional.isPresent()) {
            Participant participant = meetupOptional.get();
            Set<Meetup> meetupSet = participant.getMeetups();
            meetupSet.add(meetup);
            Participant savedParticipant = participantRepository.save(participant);
            return savedParticipant
                    .getMeetups()
                    .stream()
                    .filter(it -> it.getMeetupName().equals(meetup.getMeetupName()))
                    .collect(toList())
                    .get(0);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Deletes the given user from database.
     * @param username is the username that we want to delete
     * */
    @Transactional
    public void deleteParticipant (String username) {
        participantRepository.deleteByUsername(username);
    }

    public void registerToMeetup(String username, String meetupID) {
        Optional<Meetup> meetupOptional = meetupRepository.findByMeetupID(meetupID);
        Optional<Participant> participantOptional = participantRepository.findByUsername(username);
        Set<Participant> participantSet;
        Set<Meetup> meetupSet;

        Participant participant;
        Meetup meetup;

        if (meetupOptional.isPresent()) {
            meetup = meetupOptional.get();
            participantSet = meetup.getParticipants();
        } else {
            throw new EntityNotFoundException();
        }

        if (participantOptional.isPresent()) {
            participant = participantOptional.get();
            meetupSet = participant.getMeetups();
        } else {
            throw new EntityNotFoundException();
        }
        meetupSet.add(meetup);
        participantSet.add(participant);

        participantRepository.save(participant);
        meetupRepository.save(meetup);
    }
}
