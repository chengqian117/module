package com.chengqian.module.mod.aspect;

import com.chengqian.module.frame.config.ResultData;
import com.chengqian.module.frame.utils.RedisUtils;
import com.chengqian.module.mod.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 令牌通切口
 * @author cq
 */
@Aspect
@Component
@Order(5)
@Slf4j
public class RateLimiterAspect {

    @Pointcut("@annotation(com.chengqian.module.mod.annotation.RateLimiter) " +
            "|| @within(com.chengqian.module.mod.annotation.RateLimiter)")
    public void rateLimiterPointCut() {
    }

    @Around(value = "rateLimiterPointCut() && @annotation(rateLimiter)")
    public Object around(ProceedingJoinPoint point, RateLimiter rateLimiter) throws Throwable {
        boolean access=false;

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request =  sra.getRequest();
        String remoteAddr = request.getRemoteAddr();
        String replace = remoteAddr.replace(":", ".");
        String intern = replace.intern();

        access = access(intern, rateLimiter.limit(), rateLimiter.intervalInMills(), rateLimiter.intervalPerPermit(), rateLimiter.minFrequency());
        if(access){
            return point.proceed();
        }else{
            return ResultData.error(1,"请求太过频繁，稍后再试吧");
        }

    }
    @Autowired
    RedisUtils redisUtils;

    public boolean access(String key,double limit,long intervalInMills,double intervalPerPermit,double minFrequency) {
        String intern = key.intern();
        final String REDIS_CAPTCHA_PRE="redis:captcha:";

        synchronized(intern){
            String redisKey=REDIS_CAPTCHA_PRE+intern;
            TokenBucket tokenBucket = (TokenBucket)redisUtils.get(redisKey,TokenBucket.class);
            if(tokenBucket==null){
                tokenBucket=new TokenBucket(System.currentTimeMillis(), limit - 1);
                FrequencyQueue frequencyQueue = new FrequencyQueue(minFrequency);
                tokenBucket.setFrequency(frequencyQueue);
                redisUtils.set(redisKey,tokenBucket,30*60);
                return true;
            }else {
                double currentTokensRemaining=0;
                long lastRefillTime = tokenBucket.getLastTime();
                long refillTime = System.currentTimeMillis();
                long intervalSinceLast = refillTime - lastRefillTime;

                if (intervalSinceLast > intervalInMills) {
                    currentTokensRemaining = limit;
                } else {
                    currentTokensRemaining = intervalSinceLast / intervalPerPermit;

                    currentTokensRemaining = Math.min(currentTokensRemaining + tokenBucket.getRemainder(), limit);
                }
                log.debug(Double.toString(currentTokensRemaining));
                tokenBucket.setLastTime(refillTime);
                tokenBucket.getFrequency().put(intervalSinceLast+0.0);
                log.debug(tokenBucket.toString());

                boolean needStop = tokenBucket.getFrequency().isNeedStop();
                if(needStop){
                    return false;
                }
                assert currentTokensRemaining >= 0;
                if (currentTokensRemaining <1) {
                    return false;
                } else {
                    tokenBucket.setRemainder(currentTokensRemaining-1);
                    redisUtils.set(redisKey,tokenBucket);
                    long expire = redisUtils.getExpire(redisKey);
                    if(expire<300){
                        redisUtils.expire(redisKey,30,TimeUnit.MINUTES);
                    }
                    return true;
                }
            }
        }
    }
}
