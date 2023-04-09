package com.desafio.squad.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
@Pattern(regexp = "^([0-9]{2}[.]?[0-9]{3}[.]?[0-9]{3}-[0-9]|[0-9]{9})$")
@Documented
public @interface RG {

    String message() default "O número do registro deve ser um RG válido.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}