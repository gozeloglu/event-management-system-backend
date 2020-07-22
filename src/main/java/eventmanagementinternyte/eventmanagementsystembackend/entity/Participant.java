package eventmanagementinternyte.eventmanagementsystembackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "PARTICIPANT_SEQ")
@AllArgsConstructor
@NoArgsConstructor
public class Participant  extends BaseEntity {

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL", unique = true)
    @Email
    private String email;

    @Column(name = "USERNAME", unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AGE")
    private int age;

    @Column(name = "IDENTITY_NUMBER", unique = true)
    // TODO Validation will be here
    private String identityNumber;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "participants_meetups",
            joinColumns = {
                @JoinColumn(name = "participant_id", referencedColumnName = "id",
                        nullable = false, updatable = false)},
                inverseJoinColumns = {
                    @JoinColumn(name = "meetup_id", referencedColumnName = "id",
                        nullable = false, updatable = false)
                }
    )
    private Set<Meetup> meetups;

}
