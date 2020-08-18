package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.dto.MeetupDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.MeetupMapper;
import eventmanagementinternyte.eventmanagementsystembackend.repository.MeetupRepository;
import eventmanagementinternyte.eventmanagementsystembackend.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MeetupService {

    private final MeetupRepository meetupRepository;
    private final ParticipantRepository participantRepository;
    private final MeetupMapper meetupMapper;

    /**
     * Lists all meetups in the database.
     *
     * @return List<Meetup> that includes Meetup objects.
     */
    public List<Meetup> listAllMeetups() {
        return meetupRepository.findAll();
    }

    /**
     * Saves the new meetup
     *
     * @param meetup is an entity that stores the information about meetup
     * @return Meetup object which is saved
     */
    public Meetup saveNewMeetup(Meetup meetup) {
        return meetupRepository.save(meetup);
    }

    /**
     * Updates the meetup
     * Gets the Meetup object and changes with new values
     *
     * @param meetupID  is the id of the meetup that we want to update
     * @param newMeetup is the updated meetup that we want to change
     * @return Meetup object
     */
    @Transactional
    public Meetup updateMeetup(Long meetupID, Meetup newMeetup) {
        // Get the given meetup from database
        Optional<Meetup> meetupOptional = meetupRepository.findById(meetupID);
        if (meetupOptional.isPresent()) {
            // If meetup is found, assign the Optional object to Meetup object
            // Update the object with helper function
            // Save it the database
            Meetup oldMeetup = meetupOptional.get();
            newMeetup = updateMeetupFromDB(newMeetup, oldMeetup);
            return meetupRepository.save(newMeetup);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Updates the Meetup object attributes with setter methods
     *
     * @param newMeetup is the updated Meetup object
     * @param oldMeetup is the previous Meetup object
     * @return updated Meetup object
     */
    private Meetup updateMeetupFromDB(Meetup newMeetup, Meetup oldMeetup) {
        // oldMeetup.setMeetupID(newMeetup.getMeetupID());
        oldMeetup.setMeetupName(newMeetup.getMeetupName());
        oldMeetup.setDetails(newMeetup.getDetails());
        oldMeetup.setAddress(newMeetup.getAddress());
        oldMeetup.setPlaceName(newMeetup.getPlaceName());
        oldMeetup.setStartDate(newMeetup.getStartDate());
        oldMeetup.setEndDate(newMeetup.getEndDate());
        oldMeetup.setQuota(newMeetup.getQuota());
        oldMeetup.setRegisteredUserCount(newMeetup.getRegisteredUserCount());
        return oldMeetup;
    }

    /**
     * Deletes meetup that is given as a parameter
     *
     * @param meetupID is the id of the meetup that we want to delete
     */
    @Transactional
    public void deleteMeetup(Long meetupID) {
        // Fetch the all participants
        List<Participant> participantList = participantRepository.findAll();

        // Iterate over the participant list
        for (int i = 0; i < participantList.size(); i++) {

            // Get meetup set of the each participant
            Set<Meetup> meetupSet = participantList.get(i).getMeetups();

            if (meetupSet.size() > 0) {

                // Convert set to meetup array
                Object[] meetups = meetupSet.toArray();

                // Traverse the meetup array
                for (int j = 0; j < meetups.length; j++) {

                    // Assign each element to Meetup object
                    Meetup meetup = (Meetup) meetups[j];
                    if (meetup.getId().equals(meetupID)) {

                        // Remove from set
                        meetupSet.remove(meetup);
                        // Update the participant's set
                        participantList.get(i).setMeetups(meetupSet);
                        // Update the participant
                        participantRepository.save(participantList.get(i));
                        break;
                    }
                }
            }
        }
        meetupRepository.deleteById(meetupID);
    }

    /**
     * Fetches the given meetup
     *
     * @param meetupID specifies the meetup id
     * @return MeetupDTO object
     */
    public MeetupDTO getMeetup(Long meetupID) {
        Optional<Meetup> optionalMeetup = meetupRepository.findById(meetupID);

        if (optionalMeetup.isPresent()) {
            Meetup meetup = optionalMeetup.get();
            return meetupMapper.mapToDto(meetup);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
