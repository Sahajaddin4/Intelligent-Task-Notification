package org.taskmanagement.taskservice.customannotationrule;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.taskmanagement.taskservice.customannotationrule.implementannotations.EndTimeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EndTimeValidator.class})
public @interface ValidEndTime {
    String message() default "End time must be 2min in future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
