package it.wang.ego.dubbo.service.impl;

import it.ego.commons.utils.IDUtils;
import it.wang.ego.dubbo.service.TbUserDubboService;
import it.wang.ego.mapper.TbUserMapper;
import it.wang.ego.pojo.TbUser;
import it.wang.ego.pojo.TbUserExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * @ClassName TbUserDubboServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1110:27
 * @Version 1.0
 **/
public class TbUserDubboServiceImpl implements TbUserDubboService {
    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser login(TbUser tbUser) {
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if(tbUsers!=null&&tbUsers.size()>0){
            return tbUsers.get(0);
        }
        return null;
    }

    @Override
    public int  regist(TbUser tbUser) {
       return tbUserMapper.insertSelective(tbUser);
    }

    @Override
    public int checkUserNameOrPassword(String userNameOrPassword ,int type) {
        TbUserExample tbUserExample = new TbUserExample();
        if(type==1){
            tbUserExample.createCriteria().andUsernameEqualTo(userNameOrPassword);
        }
        tbUserExample.createCriteria().andPhoneEqualTo(userNameOrPassword);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if(tbUsers.size()==0){
            return 1;
        }
        return 0;
    }
}
