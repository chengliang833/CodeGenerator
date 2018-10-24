package com.gencode.generate.persistence.mapper;

import com.codegen.vo.Page;
import com.gencode.generate.persistence.model.Sites;
import java.util.List;

public interface SitesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Sites record);

    int insertSelective(Sites record);

    Sites selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Sites record);

    int updateByPrimaryKey(Sites record);

    List<Sites> selectAll();

    List<Sites> selectByPage(Page page);

    Integer count(Sites record);

    int deleteByCondition(Sites record);
}