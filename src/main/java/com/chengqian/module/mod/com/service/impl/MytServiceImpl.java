package com.chengqian.module.mod.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengqian.module.frame.entity.PageParam;
import com.chengqian.module.frame.utils.PageUtils;
import com.chengqian.module.frame.utils.Query;
import com.chengqian.module.mod.com.dao.MytDao;
import com.chengqian.module.mod.com.entity.MytEntity;
import com.chengqian.module.mod.com.service.MytService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cq
 * @since 2021-12-30
 */
@Service
@Slf4j
public class MytServiceImpl extends ServiceImpl<MytDao, MytEntity> implements MytService {

        @Override
        public PageUtils queryPage( PageParam<MytEntity> params) {
            MytEntity search = params.getParams();
            IPage<MytEntity> page;
            if(search==null){
                page = page(
                new Query<MytEntity>().getPage(params),
                new QueryWrapper<>()
                );
            }else{
                page = page(
                new Query<MytEntity>().getPage(params),
                new LambdaQueryWrapper<MytEntity>()
                .eq(MytEntity::getName,search.getName())
                );
            }

        return new PageUtils(page);
        }
}

//页面表格
/**
     { label: 'ID', name: 'id', width: 60, align: 'center', key: true },
     { label: '', name: 'id', width: 200, align: 'center' },
     { label: '', name: 'name', width: 200, align: 'center' },

 //页面修改table

     <tr>
     <th>ID</th>
     <td>
     <input type="text"  class="form-control required" v-model="ent.id" placeholder="请输入ID" />
     </td>
     </tr>
     <tr>
     <th></th>
     <td>
     <input type="text" class="form-control" v-model="ent.id" />
     </td>
     </tr>
     <tr>
     <th></th>
     <td>
     <input type="text" class="form-control" v-model="ent.name" />
     </td>
     </tr>
 **/
