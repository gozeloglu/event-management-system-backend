package eventmanagementinternyte.eventmanagementsystembackend.controller;

import eventmanagementinternyte.eventmanagementsystembackend.dto.AdminDTO;
import eventmanagementinternyte.eventmanagementsystembackend.dto.ParticipantDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Admin;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.AdminMapper;
import eventmanagementinternyte.eventmanagementsystembackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminMapper adminMapper = null;

    @PostMapping(value = "/create-new-admin")
    public AdminDTO saveNewParticipantToDB(@Valid @RequestBody AdminDTO adminDTO) {
        Admin admin = adminMapper.mapToEntity(adminDTO);
        Admin newAdmin = adminService.saveNewAdminToDB(admin);
        return adminMapper.mapToDto(newAdmin);
    }

    @PostMapping(value = "/login")
    public Admin login(@Valid @RequestBody Admin admin) throws Exception {
        System.out.println(admin.getUserName() + "   " + admin.getPassword());
        return adminService.login(admin.getUserName(), admin.getPassword());
    }
}
