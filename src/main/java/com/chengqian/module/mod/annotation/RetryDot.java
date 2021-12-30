package com.chengqian.module.mod.annotation;

import java.lang.annotation.*;

/**
 * @author cq
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryDot {
    /**
     * 重试次数
     * @return
     */
    int count() default 0;


    /**
     * 重试的间隔时间
     * @return
     */
    int sleep() default 0;


    /**
     * 是否支持异步重试方式
     * @return
     */
    boolean asyn() default false;
}
