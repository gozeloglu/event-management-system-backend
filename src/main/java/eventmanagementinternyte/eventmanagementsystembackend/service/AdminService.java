package eventmanagementinternyte.eventmanagementsystembackend.service;

import eventmanagementinternyte.eventmanagementsystembackend.entity.Admin;
import eventmanagementinternyte.eventmanagementsystembackend.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
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

    public Admin login(String userName, String password) throws Exception {
        try {
            System.out.println(userName + "   "  + password);
            Admin admin = adminRepository.findByUserName(userName);
            System.out.println(admin.getUserName() + "  " + admin.getPassword());
            System.out.println(userName + " " + password);
            if (admin.getPassword().equals(password)) {
                return admin;
            } else {
                throw new Exception("Password is not correct!");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new Exception("Username is not valid!");
        }
    }
}
