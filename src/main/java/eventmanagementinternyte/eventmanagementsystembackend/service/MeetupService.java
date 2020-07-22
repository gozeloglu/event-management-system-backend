package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.repository.MeetupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeetupService {

    private final MeetupRepository meetupRepository;

    /**
     * Lists all meetups in the database.
     * @return List<Meetup> that includes Meetup objects.
     * */
    public List<Meetup> listAllMeetups() {
        return meetupRepository.findAll();
    }

    /**
     * Saves the new meetup
     * @param meetup is an entity that stores the information about meetup
     * @return Meetup object which is saved
     * */
    public Meetup saveNewMeetup(Meetup meetup) {
        return meetupRepository.save(meetup);
    }

    /**
     * Updates the meetup
     * Gets the Meetup object and changes with new values
     * @param meetupID is the id of the meetup that we want to update
     * @param newMeetup is the updated meetup that we want to change
     * @return Meetup object
     * */
    @Transactional
    public Meetup updateMeetup(String meetupID, Meetup newMeetup) {
        // Get the given meetup from database
        Optional<Meetup> meetupOptional = meetupRepository.findByMeetupID(meetupID);
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
     * @param newMeetup is the updated Meetup object
     * @param oldMeetup is the previous Meetup object
     * @return updated Meetup object
     * */
    private Meetup updateMeetupFromDB(Meetup newMeetup, Meetup oldMeetup) {
        oldMeetup.setMeetupID(newMeetup.getMeetupID());
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
     * @param meetupID is the id of the meetup that we want to delete
     * */
    @Transactional
    public void deleteMeetup(String meetupID) {
        meetupRepository.deleteByMeetupID(meetupID);
    }
}
