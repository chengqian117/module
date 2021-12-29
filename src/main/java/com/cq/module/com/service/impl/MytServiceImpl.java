package com.cq.module.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cq.module.com.dao.MytDao;
import com.cq.module.com.entity.MytEntity;
import com.cq.module.com.service.MytService;
import com.cq.module.utils.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cq
 * @since 2021-12-29
 */
@Service
public class MytServiceImpl extends ServiceImpl<MytDao, MytEntity> implements MytService {

    @Override
    public IPage queryPage(Map<String, Object> params) {
        String paramKey = (String)params.get("paramKey");
        IPage<MytEntity> page = this.page(
                new Query<MytEntity>().getPage(params),
                new QueryWrapper<MytEntity>()
                .like(StringUtils.isNotBlank(paramKey),"unitName", paramKey)
        //.eq("status", 1)
        );

        return page;
    }
}
