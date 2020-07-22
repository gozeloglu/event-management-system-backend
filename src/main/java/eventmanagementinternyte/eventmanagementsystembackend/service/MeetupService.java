package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.repository.MeetupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
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

    @Transactional
    public Meetup updateMeetup(String meetupID, Meetup newMeetup) {
        Optional<Meetup> meetupOptional = meetupRepository.findByMeetupID(meetupID);
        if (meetupOptional.isPresent()) {
            Meetup oldMeetup = meetupOptional.get();
            newMeetup = updateMeetupFromDB(newMeetup, oldMeetup);
            return meetupRepository.save(newMeetup);
        } else {
            throw new EntityNotFoundException();
        }
    }

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
}
