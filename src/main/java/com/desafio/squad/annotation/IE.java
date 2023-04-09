package com.desafio.squad.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
@Pattern(regexp = "^\\d{3}[.]\\d{3}[.]\\d{3}[.]\\d{3}$")
@Documented
public @interface IE {

    String message() default "O número do registro deve ser um IE válido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
