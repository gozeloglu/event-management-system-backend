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
    public ParticipantService(ParticipantMapper participantMapper,
                              ParticipantRepository participantRepository,
                              MeetupRepository meetupRepository,
                              MeetupMapper meetupMapper) {
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
    public String registerToMeetup(String username, Long meetupID) {
        Optional<Meetup> meetupOptional = meetupRepository.findById(meetupID);
        Optional<Participant> participantOptional = participantRepository.findByUsername(username);

        Set<Participant> participantSet;
        Set<Meetup> meetupSet;

        Participant participant;
        Meetup meetup;

        if (meetupOptional.isPresent()) {
            meetup = meetupOptional.get();
            participantSet = meetup.getParticipants();
        } else {
            return "Meetup could not found!";
        }

        if (participantOptional.isPresent()) {
            participant = participantOptional.get();
            meetupSet = participant.getMeetups();
        } else {
            return "Participant could not found!";
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
        return "You are registered to meetup!";
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
    public MeetupDTO getMeetupDetail(Long meetupID) {
        Optional<Meetup> optionalMeetup = meetupRepository.findById(meetupID);

        if (optionalMeetup.isPresent()) {
            Meetup meetup = optionalMeetup.get();
            return meetupMapper.mapToDto(meetup);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * This method fetches the participant details and returns to client
     *
     * @param username specifies the participant who we want to get details
     * @return ParticipantDTO object which includes the personal information
     */
    public ParticipantDTO getParticipantDetails(String username) {
        Optional<Participant> optionalParticipant = participantRepository.findByUsername(username);

        if (optionalParticipant.isPresent()) {
            Participant participant = optionalParticipant.get();
            return participantMapper.mapToDto(participant);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * This method updates the participant profile by using identity number
     * Send a query and fetches the participant object in Optional type
     * Updates the object with helper function - updateParticipantFromDB
     * Saves on the database
     *
     * @param identityNumber specifies whose participant will be updated
     * @param participant    contains the participant information
     * @return Message in terms of result
     */
    @Transactional
    public String updateParticipant(String identityNumber, Participant participant) {
        Optional<Participant> optionalParticipant = participantRepository.findByIdentityNumber(identityNumber);

        if (optionalParticipant.isPresent()) {
            Participant oldProfile = optionalParticipant.get();
            participant = updateParticipantFromDB(participant, oldProfile);
            participantRepository.save(participant);
            return "Your profile is updated";
        } else {
            return "Your profile could not updated!";
        }
    }

    /**
     * This function updates the object
     *
     * @param participant contains the information
     * @param oldProfile  contains the information before updating
     * @return Participant object which includes the updated information
     */
    private Participant updateParticipantFromDB(Participant participant, Participant oldProfile) {
        oldProfile.setFirstName(participant.getFirstName());
        oldProfile.setLastName(participant.getLastName());
        oldProfile.setEmail(participant.getEmail());
        oldProfile.setUsername(participant.getUsername());
        oldProfile.setAge(participant.getAge());
        oldProfile.setIdentityNumber(participant.getIdentityNumber());
        return oldProfile;
    }

    /**
     * This method unregistered from meetup
     * Fetches the meetupSet and participantSet
     * Removes the participant from participantSet
     * Removes the meetup from meetupSet
     *
     * @param username specifies the participant that we want to unregister
     * @param meetupID specifies the meetup that we want to unregister
     * @return Appropriate string message
     * */
    public String unregisterMeetup(String username, Long meetupID) {
        Optional<Meetup> meetupOptional = meetupRepository.findById(meetupID);
        Optional<Participant> participantOptional = participantRepository.findByUsername(username);

        Set<Participant> participantSet;
        Set<Meetup> meetupSet;

        Participant participant;
        Meetup meetup;

        if (meetupOptional.isPresent()) {
            meetup = meetupOptional.get();
            participantSet = meetup.getParticipants();
        } else {
            return "Meetup could not found!";
        }

        if (participantOptional.isPresent()) {
            participant = participantOptional.get();
            meetupSet = participant.getMeetups();
        } else {
            return "Participant could not found!";
        }
        meetupSet.remove(meetup);
        participantSet.remove(participant);
        int registeredUserCount = participantSet.size();
        meetup.setRegisteredUserCount(registeredUserCount);
        participantRepository.save(participant);
        meetupRepository.save(meetup);
        return "You are unregistered to meetup!";
    }
}
