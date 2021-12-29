package com.cq.module.common;

import org.springframework.beans.factory.annotation.Value;

public class Constant {

    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";
    /**
     *  升序
     */
    public static final String DESC = "desc";
    /**
     *  升序
     */
    public static final String WRAPPER = "ew";

    //是否使用influxDb
    public static boolean useInfluxDb;
    @Value("${constant.useInfluxDb}")
    public void setUseInfluxDb(boolean useInfluxDb) {
        Constant.useInfluxDb = useInfluxDb;
    }
}
