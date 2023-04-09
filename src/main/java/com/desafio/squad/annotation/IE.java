package com.desafio.squad.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "^\\d{3}[.]\\d{3}[.]\\d{3}[.]\\d{3}$")
@Documented
public @interface IE {

    String message() default "O número do registro deve ser um IE válido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
