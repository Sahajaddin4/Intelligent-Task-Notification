package org.taskmanagement.taskservice.customannotationrule.implementannotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.taskmanagement.taskservice.customannotationrule.ValidEndTime;

import java.time.LocalDateTime;

public class EndTimeValidator implements ConstraintValidator<ValidEndTime, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if(localDateTime == null){
            return false;
        }
        return localDateTime.isAfter(LocalDateTime.now().plusMinutes(2));
    }
}
