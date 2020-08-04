package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Admin;
import eventmanagementinternyte.eventmanagementsystembackend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    /**
     * Saves a new admin to the database
     *
     * @param newAdmin is an entity that stores the new admin information.
     * @return Admin object after saved it.
     */
    public Admin saveNewAdminToDB(Admin newAdmin) {
        return adminRepository.save(newAdmin);
    }
}
