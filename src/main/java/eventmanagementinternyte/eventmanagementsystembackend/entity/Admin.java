package eventmanagementinternyte.eventmanagementsystembackend.entity;

import eventmanagementinternyte.eventmanagementsystembackend.validation.IdentityNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "idgen", sequenceName = "ADMIN_SEQ")
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends BaseEntity {

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
    @IdentityNumber(message = "Identity number must be valid!")
    private String identityNumber;
}
