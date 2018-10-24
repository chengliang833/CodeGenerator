package com.gencode.generate.service;

import com.gencode.generate.persistence.model.Sites;
import com.gencode.generate.persistence.mapper.SitesMapper;
import com.codegen.vo.Page;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by fenglibin on 2018/10/24.
 */
@Service
public class SitesService {

	@Autowired
    private SitesMapper sitesMapper;
    
    public int deleteByPrimaryKey(Integer id){
        return sitesMapper.deleteByPrimaryKey(id);
    }

    public int insert(Sites record){
        return sitesMapper.insert(record);
    }

    public int insertSelective(Sites record){
        return sitesMapper.insertSelective(record);
    }

    public Sites selectByPrimaryKey(Integer id){
        return sitesMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(Sites record){
        return sitesMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(Sites record){
        return sitesMapper.updateByPrimaryKey(record);
    }

    public List<Sites> selectAll( ){
        return sitesMapper.selectAll();
    }

    public List<Sites> selectByPage(Page<Sites> page){
        return sitesMapper.selectByPage(page);
    }

    public Integer count(Sites record){
        return sitesMapper.count(record);
    }

    public int deleteByCondition(Sites record){
        return sitesMapper.deleteByCondition(record);
    }

}
