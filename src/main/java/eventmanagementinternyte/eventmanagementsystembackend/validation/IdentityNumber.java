package eventmanagementinternyte.eventmanagementsystembackend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdentityNumberValidator.class)
public @interface IdentityNumber {
    String message() default "Identity Number is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
