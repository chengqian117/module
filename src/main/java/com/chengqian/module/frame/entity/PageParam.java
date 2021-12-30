package com.chengqian.module.frame.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cq
 */
@Data
public class PageParam<T> {

    private Long current;
    private Long size;
    private List<OrderField> orders = new ArrayList<>();
    private T params;


}

