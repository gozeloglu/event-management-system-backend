package eventmanagementinternyte.eventmanagementsystembackend.controller;

import eventmanagementinternyte.eventmanagementsystembackend.dto.AdminDTO;
import eventmanagementinternyte.eventmanagementsystembackend.dto.ParticipantDTO;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Admin;
import eventmanagementinternyte.eventmanagementsystembackend.entity.Participant;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.AdminMapper;
import eventmanagementinternyte.eventmanagementsystembackend.mapper.ParticipantMapper;
import eventmanagementinternyte.eventmanagementsystembackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminMapper adminMapper;
    private final ParticipantMapper participantMapper;

    @PostMapping(value = "/create-new-admin")
    public AdminDTO saveNewParticipantToDB(@Valid @RequestBody AdminDTO adminDTO) {
        Admin admin = adminMapper.mapToEntity(adminDTO);
        Admin newAdmin = adminService.saveNewAdminToDB(admin);
        return adminMapper.mapToDto(newAdmin);
    }

    @PostMapping(value = "/login")
    public AdminDTO login(@Valid @RequestBody Admin admin) throws Exception {
        System.out.println(admin.getUsername() + "   " + admin.getPassword());
        return adminService.login(admin.getUsername(), admin.getPassword());
    }

    @GetMapping(value = "/all-participants/{meetupID}")
    public List<ParticipantDTO> listAllParticipants(@PathVariable String meetupID) {
        try {
            Set<Participant> participantSet = adminService.listAllParticipants(meetupID);
            return participantMapper.mapToDto(new ArrayList<>(participantSet));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @PutMapping(value = "/update-profile/{identityNumber}")
    public String updateProfile(@PathVariable String identityNumber, @Valid @RequestBody Admin admin) {
        return adminService.updateProfile(identityNumber, admin);
    }

    @GetMapping(value = "/admin-details/{username}")
    public AdminDTO getAdminDetails(@PathVariable String username) {
        return adminService.getAdminDetails(username);
    }
}
