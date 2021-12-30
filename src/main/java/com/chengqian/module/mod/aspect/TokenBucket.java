package com.chengqian.module.mod.aspect;

import lombok.Data;

import java.io.Serializable;

@Data
public class TokenBucket implements Serializable {

    private static final long serialVersionUID = 9999L;
    //最后访问时间
    private long lastTime;
    //余量
    private double remainder;
    //时间间隔
    private FrequencyQueue frequency;

    public TokenBucket(long lastTime, double remainder) {
        this.lastTime = lastTime;
        this.remainder = remainder;
    }

    @Override
    public String toString() {
        return "TokenBucket{" +
                "lastTime=" + lastTime +
                ", remainder=" + remainder +
                ", frequency=" + frequency.toString() +
                '}';
    }
}
