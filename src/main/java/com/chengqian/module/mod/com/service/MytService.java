package com.chengqian.module.mod.com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chengqian.module.frame.entity.PageParam;
import com.chengqian.module.frame.utils.PageUtils;
import com.chengqian.module.mod.com.entity.MytEntity;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cq
 * @since 2021-12-30
 */
public interface MytService extends IService<MytEntity> {

        PageUtils queryPage( PageParam<MytEntity> params);
}
