package com.chengqian.module.mod.annotation;

import java.lang.annotation.*;

/**
 * @author cq
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RateLimiter {

    //在 intervalInMills 毫秒内 限制访问 limit 次，每 intervalPerPermit 毫秒 添加 一次，不超过 limit
    long intervalInMills() default 180000;
    double limit() default 3;
    double intervalPerPermit() default 60000;
    //限制频率 五次请求的时间间隙取平均数 不得小于该值 （毫秒）
    double minFrequency() default  0;
}
