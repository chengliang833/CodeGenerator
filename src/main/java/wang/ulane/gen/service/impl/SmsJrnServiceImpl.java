package wang.ulane.gen.service.impl;

import wang.ulane.gen.model.SmsJrn;
import wang.ulane.gen.mapper.SmsJrnDao;
import wang.ulane.gen.service.SmsJrnService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.ibatis.annotations.Param;

@Service("smsJrnService")
public class SmsJrnServiceImpl implements SmsJrnService{

	@Autowired
    private SmsJrnDao smsJrnDao;
    
    public int deleteByKey(Integer id){
        return smsJrnDao.deleteByKey(id);
    }

    public int insert(SmsJrn record){
        return smsJrnDao.insert(record);
    }

    public int insertSelective(SmsJrn record){
        return smsJrnDao.insertSelective(record);
    }

    public SmsJrn getByKey(Integer id){
        return smsJrnDao.getByKey(id);
    }

    public int updateByKeySelective(SmsJrn record){
        return smsJrnDao.updateByKeySelective(record);
    }

    public int updateByKeyWithBLOBs(SmsJrn record){
        return smsJrnDao.updateByKeyWithBLOBs(record);
    }

    public int updateByKey(SmsJrn record){
        return smsJrnDao.updateByKey(record);
    }

}
