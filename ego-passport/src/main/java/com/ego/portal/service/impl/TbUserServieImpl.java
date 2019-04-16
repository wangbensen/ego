package com.ego.portal.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.portal.service.TbUserService;
import com.ego.redis.dao.JedisDao;
import it.ego.commons.pojo.EgoResult;
import it.ego.commons.utils.CookieUtils;
import it.ego.commons.utils.IDUtils;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.dubbo.service.TbUserDubboService;
import it.wang.ego.pojo.TbUser;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName TbUserServieImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1110:34
 * @Version 1.0
 **/
@Service
public class TbUserServieImpl implements TbUserService {
    @Reference
    private TbUserDubboService tbUserDubboServiceImpl;
    @Autowired
    private JedisDao jedisDaoImpl;

    @Override
    public EgoResult login(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        EgoResult egoResult = new EgoResult();
        TbUser user = tbUserDubboServiceImpl.login(tbUser);
        if (user != null && !user.equals("")) {
            egoResult.setStatus(200);
            egoResult.setMsg("登录成功");
            egoResult.setData(tbUser.getId());
            //当用户登录成功后把用户信息放入到 redis 中
            user.setPassword("");
            String token = UUID.randomUUID().toString();
            jedisDaoImpl.set(token, JsonUtils.objectToJson(user));
            jedisDaoImpl.expire(token, 60 * 60 * 24 * 7);
            //然后把key 放入cookie中
            CookieUtils.setCookie(request, response, "TT_TOKEN", token, 60 * 60 * 24 * 7);
            return egoResult;
        }
        egoResult.setStatus(500);
        egoResult.setMsg("用户名或者密码错误");
        return egoResult;
    }

    @Override
    public EgoResult getUserInfoByToken(String token, HttpServletRequest request, HttpServletResponse response) {
        EgoResult egoResult = new EgoResult();
        String s = jedisDaoImpl.get(token);
        if (s != null && !s.equals("")) {
        TbUser user = JsonUtils.jsonToPojo(s, TbUser.class);
            user.setPassword("");
            egoResult.setStatus(200);
            egoResult.setMsg("OK");
            egoResult.setData(user);
            return  egoResult;
        }
        return  egoResult;
    }

    @Override
    public EgoResult exit(String token, HttpServletRequest request, HttpServletResponse response) {
        Long index = jedisDaoImpl.del(token);
        CookieUtils.deleteCookie(request,response,token);
        EgoResult egoResult=new EgoResult();
        egoResult.setStatus(200);
        egoResult.setMsg("OK");
        return egoResult;
    }

    @Override
    public EgoResult regist(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        Date date = new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        tbUser.setId(IDUtils.genItemId());
        int regist = tbUserDubboServiceImpl.regist(tbUser);
        EgoResult egoResult = new EgoResult();
            if(regist==1) {
                egoResult.setStatus(200);
                egoResult.setMsg("登录成功");
                egoResult.setData(tbUser.getId());
                return egoResult;
            }
        egoResult.setStatus(500);
        egoResult.setMsg("用户名或者密码错误");
        return egoResult;
    }

    @Override
    public EgoResult checkUserNameOrPhoneNum(String usernameOrPassword, int type) {
        int i = tbUserDubboServiceImpl.checkUserNameOrPassword(usernameOrPassword, type);
        EgoResult egoResult = new EgoResult();
        if(i==1){
            egoResult.setData(true);
        }
        return egoResult;
    }
}
