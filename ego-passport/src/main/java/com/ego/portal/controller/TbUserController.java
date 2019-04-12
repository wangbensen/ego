package com.ego.portal.controller;

import com.ego.portal.service.TbUserService;
import com.ego.redis.dao.JedisDao;
import it.ego.commons.pojo.EgoResult;
import it.ego.commons.utils.CookieUtils;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @ClassName TbUserController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1110:12
 * @Version 1.0
 **/

@Controller
public class TbUserController {
    @Autowired
    private TbUserService tbUserServiceImpl;
    @Autowired
    private JedisDao jedisDaoImpl;

    /**
     * @param url
     * @param model
     * @return
     */
    @RequestMapping("user/showLogin")
    public String showLogin(@RequestHeader("Referer") String url, Model model) {
        model.addAttribute("redirect", url);
        return "login";
    }

    /**
     * 登录验证
     *
     * @return
     */
    @RequestMapping("user/login")
    @ResponseBody
    public EgoResult login(TbUser tbUser, HttpServletRequest request, HttpServletResponse response) {
        return tbUserServiceImpl.login(tbUser, request, response);
    }

    /**
     * 获取用户登录信息
     *
     * @param token
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("user/token/{token}")
    @ResponseBody
    public Object getUserInfoByToken(@PathVariable String token, HttpServletRequest request, HttpServletResponse response, String callback) {
        EgoResult er = tbUserServiceImpl.getUserInfoByToken(token, request, response);
        if (callback != null && !callback.equals("")) {
            MappingJacksonValue mjv = new MappingJacksonValue(er);
            mjv.setJsonpFunction(callback);
            return  mjv;
        }
        return er;
    }

    /**
     * 推出 当前用户
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping("user/logout/{token}")
    @ResponseBody
    public Object exit(@PathVariable String token, HttpServletRequest request, HttpServletResponse response, String callback){
        //删除reids 和cookies 中的数据
        EgoResult egoResult = tbUserServiceImpl.exit(token, request, response);
            if(callback!=null&&! callback.equals("")){
                MappingJacksonValue mjv=new MappingJacksonValue(egoResult);
                mjv.setJsonpFunction(callback);
                return  mjv;
            }
        return  egoResult;


    }
}
