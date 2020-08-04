package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.dto.AdminDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Admin;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.AdminMapper;
import eventmanagementinternyte.eventmanagementsystembackend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private AdminRepository adminRepository;

    private AdminMapper adminMapper;

    @Autowired
    public AdminService(AdminRepository adminRepository, AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
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
}
