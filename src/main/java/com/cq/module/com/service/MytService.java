package com.cq.module.com.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cq.module.com.entity.MytEntity;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cq
 * @since 2021-12-29
 */
public interface MytService extends IService<MytEntity> {

    IPage queryPage(Map<String, Object> params);

}
