package eventmanagementinternyte.eventmanagementsystembackend.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
@Builder
public class AdminDTO {

    @Size(max = 255, message = "Name cannot be larger than 255 characters!")
    public final String firstName;

    @Size(max = 255, message = "Last name cannot be larger than 255 characters!")
    public final String lastName;

    @Size(max = 255, message = "E-mail cannot be larger than 255 characters!")
    @Email(message = "Please enter a valid e-mail address")
    public final String email;

    @Size(max = 255, message = "Username cannot be larger than 255 characters!")
    public final String username;

    @Size(max = 255, message = "Password cannot be larger than 255 characters!")
    public final String password;

    @Min(value = 18, message = "Admin cannot be younger than 18 years old!")
    @Max(value = 150, message = "Admin cannot be older than 150 years old!")
    public final int age;

    @Size(max = 11, min = 11, message = "Identity number should be 11 digits")
    public final String identityNumber;

    @JsonCreator
    public AdminDTO(@JsonProperty("firstName") String firstName,
                    @JsonProperty("lastName") String lastName,
                    @JsonProperty("email") String email,
                    @JsonProperty("userName") String username,
                    @JsonProperty("password") String password,
                    @JsonProperty("age") int age,
                    @JsonProperty("identityNumber") String identityNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.age = age;
        this.identityNumber = identityNumber;
    }

}
