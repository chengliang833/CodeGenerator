package wang.ulane.gen.mapper;

import wang.ulane.gen.model.SmsJrn;

public interface SmsJrnDao {
    int deleteByKey(Integer id);

    int insert(SmsJrn record);

    int insertSelective(SmsJrn record);

    SmsJrn getByKey(Integer id);

    int updateByKeySelective(SmsJrn record);

    int updateByKeyWithBLOBs(SmsJrn record);

    int updateByKey(SmsJrn record);
}