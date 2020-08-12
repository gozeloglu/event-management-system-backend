package eventmanagementinternyte.eventmanagementsystembackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Meetup extends BaseEntity{

    @Column(name = "MEETUP_ID", unique = true)
    private String meetupID;

    @Column(name = "MEETUP_NAME")
    private String meetupName;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PLACE_NAME")
    private String placeName;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    @FutureOrPresent
    private LocalDate endDate;

    @Column(name = "QUOTA")
    private int quota;

    @Column(name = "REGISTERED_USER_COUNT")
    private int registeredUserCount;

    @ManyToMany(mappedBy = "meetups")
    private Set<Participant> participants = new HashSet<>();

    @OneToMany(mappedBy = "meetup")
    private Set<Question> questionSet = new HashSet<>();
}
