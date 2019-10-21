package wang.ulane.gen.service;

import wang.ulane.gen.model.SmsJrn;

import java.util.List;

public interface SmsJrnService {
    
    public int deleteByKey(Integer id);
    public int insert(SmsJrn record);
    public int insertSelective(SmsJrn record);
    public SmsJrn getByKey(Integer id);
    public int updateByKeySelective(SmsJrn record);
    public int updateByKeyWithBLOBs(SmsJrn record);
    public int updateByKey(SmsJrn record);
}
