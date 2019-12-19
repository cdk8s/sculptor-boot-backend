package com.cdk8s.sculptor.aop.norepeat;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmitToRedisson {


}
