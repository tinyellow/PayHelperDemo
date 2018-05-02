package com.littleyellow.pay.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
public @interface APPLICATION_ID {
    String value();
}
