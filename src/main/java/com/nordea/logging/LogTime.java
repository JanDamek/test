package com.nordea.logging;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*
    Job/Method run/finish marked by the annotation would be logged
 */

@Target({TYPE, ANNOTATION_TYPE, METHOD})
@Retention(RUNTIME)
@Documented
public @interface LogTime {
}
