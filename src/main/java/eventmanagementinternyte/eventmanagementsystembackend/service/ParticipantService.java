package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.dto.MeetupDTO;
import eventmanagementinternyte.eventmanagementsystembackend.dto.ParticipantDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.MeetupMapper;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.ParticipantMapper;
import eventmanagementinternyte.eventmanagementsystembackend.repository.MeetupRepository;
import eventmanagementinternyte.eventmanagementsystembackend.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private ParticipantRepository participantRepository;
    private MeetupRepository meetupRepository;
    private ParticipantMapper participantMapper;
    private MeetupMapper meetupMapper;

    /// TODO Attention here if there will be bug
    @Autowired
    public ParticipantService(ParticipantMapper participantMapper, ParticipantRepository participantRepository, MeetupRepository meetupRepository, MeetupMapper meetupMapper) {
        this.participantMapper = participantMapper;
        this.participantRepository = participantRepository;
        this.meetupRepository = meetupRepository;
        this.meetupMapper = meetupMapper;
    }

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

    public Meetup addMeetupToParticipant(String username, Meetup meetup) {
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
     *
     * @param username is the username that we want to delete
     */
    @Transactional
    public void deleteParticipant(String username) {
        participantRepository.deleteByUsername(username);
    }

    /**
     * Participants are registered to meetup
     *
     * @param username is the unique username of the participant
     * @param meetupID is the ID of the meetup that we want to register
     * @return Message due to success or failure
     */
    public String registerToMeetup(String username, String meetupID) {
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
            return "Meetup entity not found!";
        }

        if (participantOptional.isPresent()) {
            participant = participantOptional.get();
            meetupSet = participant.getMeetups();
        } else {
            return "Participant entity not found!";
        }
        int before = participantSet.size();
        meetupSet.add(meetup);
        participantSet.add(participant);
        int registeredUserCount = participantSet.size();
        int quota = meetup.getQuota();
        if (before == registeredUserCount) {
            return "You already registered to this meetup";
        }
        if (registeredUserCount > quota) {
            return "Quota is full!";
        }
        meetup.setRegisteredUserCount(registeredUserCount);
        participantRepository.save(participant);
        meetupRepository.save(meetup);
        return "Participant is registered to meetup!";
    }

    /**
     * This method satisfies the login operation for participants
     *
     * @param username of the participant who wants to login the system
     * @param password of the participant
     * @return ParticipantDTO object that we want to have
     */
    public ParticipantDTO login(String username, String password) throws Exception {
        Optional<Participant> optionalParticipant = participantRepository.findByUsername(username);
        if (optionalParticipant.isPresent()) {
            Participant participant = optionalParticipant.get();
            if (participant.getPassword().equals(password)) {
                return participantMapper.mapToDto(participant);
            } else {
                throw new Exception("Password is not correct!");
            }
        } else {
            throw new Exception("User not found!");
        }
    }

    /**
     * This method fetches the all meetups for participants
     *
     * @return List of Meetup object
     */
    public List<Meetup> getAllMeetups() {
        return meetupRepository.findAll();
    }

    /**
     * This method fetches the details of the given meetup id
     *
     * @param meetupID specifies the id of the meetup that we want to get details
     * @return MeetupDTO object which includes the information about given meetup
     */
    public MeetupDTO getMeetupDetail(String meetupID) {
        Optional<Meetup> optionalMeetup = meetupRepository.findByMeetupID(meetupID);

        if (optionalMeetup.isPresent()) {
            Meetup meetup = optionalMeetup.get();
            return meetupMapper.mapToDto(meetup);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
