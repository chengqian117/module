package com.chengqian.module.mod.aspect;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class FrequencyQueue implements Serializable {

    private static final long serialVersionUID = 9998L;
    private final int limit=5;

    private LinkedList<Double> frequencies;

    private boolean needStop;

    private Double minFrequency=0.0;

    public void put(Double data){
        if(frequencies==null){
            frequencies=new LinkedList<>();
        }
        frequencies.offer(data);
        if(frequencies.size()>limit){
            frequencies.pollFirst();
        }
        Double avg = frequencies.stream().collect(Collectors.averagingDouble(Double::doubleValue));
        if(avg<=minFrequency){
            needStop=true;
        }else {
            needStop=false;
        }
    }


    public boolean isNeedStop() {
        return needStop;
    }

    public FrequencyQueue(double minFrequency) {
        this.minFrequency=minFrequency;
        this.put(minFrequency);
    }

    @Override
    public String toString() {
        return "FrequencyQueue{" +
                "frequencies=" + frequencies +
                ", minFrequency=" + minFrequency +
                '}';
    }
}
