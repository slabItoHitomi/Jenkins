package com.dev_training.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date 形式チェックバリデータ。
 */
public class DateImpl implements ConstraintValidator<Date, String> {
    @Override
    public void initialize(Date constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) || value.isEmpty()) {
            return true;
        }
        Pattern ptn = Pattern.compile("^(\\d{4})[-/]?(\\d{2})[-/]?(\\d{2})$");
        Matcher mch = ptn.matcher(value);
        if(mch.find()){
            try {
                LocalDate.of(Integer.valueOf(mch.group(1)), Integer.valueOf(mch.group(2)), Integer.valueOf(mch.group(3)));
            } catch (Exception e) {
                return false;
            }
        }else{
            return false;
        }

        return true;
    }

}