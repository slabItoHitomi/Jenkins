package com.dev_training.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy={DateImpl.class})
@ReportAsSingleViolation
/**
 * Date形式チェックマーカーインターフェース。
 */
public @interface Date {
    String message() default "日付の形式で入力されていません。";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({FIELD,ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        Date[] value();
    }
}

