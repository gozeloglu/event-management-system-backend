package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.repository.MeetupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public Meetup saveNewMeetup(Meetup meetup) {
        return meetupRepository.save(meetup);
    }
}
