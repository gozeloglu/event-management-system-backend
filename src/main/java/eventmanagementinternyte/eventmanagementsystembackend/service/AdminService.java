package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.dto.AdminDTO;
import eventmanagementinternyte.eventmanagementsystembackend.dto.ParticipantDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Admin;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Meetup;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.AdminMapper;
import eventmanagementinternyte.eventmanagementsystembackend.repository.AdminRepository;
import eventmanagementinternyte.eventmanagementsystembackend.repository.MeetupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminService {

    private AdminRepository adminRepository;

    private AdminMapper adminMapper;

    private MeetupRepository meetupRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, AdminMapper adminMapper, MeetupRepository meetupRepository) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
        this.meetupRepository = meetupRepository;
    }

    /**
     * Saves a new admin to the database
     *
     * @param newAdmin is an entity that stores the new admin information.
     * @return Admin object after saved it.
     */
    public Admin saveNewAdminToDB(Admin newAdmin) {
        return adminRepository.save(newAdmin);
    }

    /**
     * This function satisfies the login the system
     *
     * @param userName of the user
     * @param password of the user
     * @return AdminDTO object which includes the admin info
     */
    public AdminDTO login(String userName, String password) throws Exception {
        try {
            Admin admin = adminRepository.findByUsername(userName);
            if (admin.getPassword().equals(password)) {
                return adminMapper.mapToDto(admin);
            } else {
                throw new Exception("Password is not correct!");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new Exception("Username is not valid!");
        }
    }

    /**
     * This method fetches the all participants in the meetup which is given as a parameter
     *
     * @param meetupID is the id of the meetup which we want to fetch all registered meetups
     * @return list of participants
     * @throws Exception meetup could not found
     */
    public Set<Participant> listAllParticipants(String meetupID) throws Exception {
        Optional<Meetup> optionalMeetup = meetupRepository.findByMeetupID(meetupID);

        Set<Participant> participantSet;
        Meetup meetup;

        if (optionalMeetup.isPresent()) {
            meetup = optionalMeetup.get();
            participantSet = meetup.getParticipants();
            return participantSet;
        } else {
            throw new Exception("Meetup could not found!");
        }
    }

    /**
     * This method updates the profile of the admin
     *
     * @param identityNumber is a unique number of the admin
     * @param admin          is a Admin object which includes the information of the admin
     * @return A string message after operation
     */
    @Transactional
    public String updateProfile(String identityNumber, Admin admin) {
        Optional<Admin> optionalAdmin = adminRepository.findByIdentityNumber(identityNumber);

        if (optionalAdmin.isPresent()) {
            Admin oldProfile = optionalAdmin.get();
            admin = updateAdminFromDB(admin, oldProfile);
            adminRepository.save(admin);
            return "Your profile is updated";
        } else {
            return "Your profile could not updated!";
        }
    }

    private Admin updateAdminFromDB(Admin admin, Admin oldProfile) {
        oldProfile.setFirstName(admin.getFirstName());
        oldProfile.setLastName(admin.getLastName());
        oldProfile.setEmail(admin.getEmail());
        oldProfile.setUsername(admin.getUsername());
        oldProfile.setAge(admin.getAge());
        oldProfile.setIdentityNumber(admin.getIdentityNumber());
        return oldProfile;
    }

    /**
     * This method fetches the details of the admin
     *
     * @param username is the admin's username
     * @return AdminDTO object
     */
    public AdminDTO getAdminDetails(String username) {
        Admin admin = adminRepository.findByUsername(username);
        return adminMapper.mapToDto(admin);
    }
}
