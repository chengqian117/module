
package com.chengqian.module.frame.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chengqian.module.frame.common.xss.SQLFilter;
import com.chengqian.module.frame.entity.OrderField;
import com.chengqian.module.frame.entity.PageParam;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 查询参数
 *
 */
public class Query<T> {

//    public IPage<T> getPage(Map<String, Object> params) {
//        return this.getPage(params, null, false);
//    }
//
//    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
//        //分页参数
//        long curPage = 1;
//        long limit = 10;
//
//        if(params.get(Constant.PAGE) != null){
//            curPage = Long.parseLong((String)params.get(Constant.PAGE));
//        }
//        if(params.get(Constant.LIMIT) != null){
//            limit = Long.parseLong((String)params.get(Constant.LIMIT));
//        }
//
//        //分页对象
//        Page<T> page = new Page<>(curPage, limit);
//
//        //分页参数
//        params.put(Constant.PAGE, page);
//
//        //排序字段
//        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
//        String orderField = SQLFilter.sqlInject((String)params.get(Constant.ORDER_FIELD));
//        String order = (String)params.get(Constant.ORDER);
//
//        //前端字段排序
//        if(StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)){
//            if(Constant.ASC.equalsIgnoreCase(order)) {
//                return page.setAsc(orderField);
//            }else {
//                return page.setDesc(orderField);
//            }
//        }
//        //默认排序
//        if(isAsc) {
//            page.setAsc(defaultOrderField);
//        }else {
//            page.setDesc(defaultOrderField);
//        }
//
//        return page;
//    }

    public IPage<T> getPage(PageParam params) {
        //分页参数
        long current = 1;
        long size = 10;

        if(params.getCurrent() != null){
            current = params.getCurrent();
        }
        if(params.getSize() != null){
            size = params.getSize();
        }

        //分页对象
        Page<T> page = new Page<>(current, size);

        List<OrderField> orders = params.getOrders();
        for (OrderField order:orders) {
            String column = order.getField();
            boolean asc = order.isAsc();
            if(StringUtils.isNotEmpty(column)){
                String orderField = SQLFilter.sqlInject(column);
                OrderItem orderItem = new OrderItem();
                orderItem.setColumn(orderField);
                orderItem.setAsc(asc);
                page.addOrder(orderItem);
            }
        }

        return page;
    }

}
