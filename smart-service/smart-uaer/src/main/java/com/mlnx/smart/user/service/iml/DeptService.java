package com.mlnx.smart.user.service.iml;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlnx.common.form.PageForm;
import com.mlnx.smart.user.entity.DeptInfo;
import com.mlnx.smart.user.mapper.DeptMapper;
import com.mlnx.smart.user.service.IDeptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.shan.spring.redis.config.CacheConfig.RCManager;

/**
 * Created by amanda.shan on 2019/3/26.
 */
@CacheConfig(cacheManager = RCManager, cacheNames = "DeptService")
@Service
public class DeptService implements IDeptService {

    @Autowired
    private DeptMapper deptMapper;

    @CacheEvict(cacheNames = "ListDeptInfo", allEntries = true)
    @Override
    public void add(DeptInfo deptInfo) {
        deptMapper.insert(deptInfo);
    }

    @Override
    @Cacheable
    public DeptInfo findById(Integer id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<DeptInfo> findByIds(List<Integer> deptIds) {
        return deptMapper.selectBatchIds(deptIds);
    }

    @Cacheable(cacheNames = "ListDeptInfo")
    @Override
    public IPage<DeptInfo> list(PageForm pageForm) {
        return deptMapper.selectPage(new Page<DeptInfo>(pageForm.getCurrent(), pageForm.getSize()), new
                QueryWrapper<DeptInfo>
                ());
    }

    @Caching(
            evict = {@CacheEvict(cacheNames = "ListDeptInfo", allEntries = true)},
            put = {@CachePut(key = "#deptInfo.id")}
    )
    @Override
    public DeptInfo modify(DeptInfo deptInfo) {
        deptMapper.updateById(deptInfo);
        return deptInfo;
    }

    @Caching(
            evict = {
                    @CacheEvict(key = "#a0"),
                    @CacheEvict(cacheNames = "ListDeptInfo", allEntries = true)
            }
    )
    @Override
    public void remove(Integer id) {
        deptMapper.deleteById(id);
    }
}
