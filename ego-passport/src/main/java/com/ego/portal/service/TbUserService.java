package com.ego.portal.service;

import it.ego.commons.pojo.EgoResult;
import it.wang.ego.pojo.TbUser;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TbUserService {
    /**
     * 根据用户名和密码判断用户是否登录
     * @param tbUser
     * @return
     */
    public EgoResult login(TbUser tbUser, HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取用户登录后的信息
     * @param token
     * @param request
     * @param response
     * @return
     */
    public EgoResult getUserInfoByToken(String token, HttpServletRequest request, HttpServletResponse response);

    /**
     * 退出当前用户
     * @param token
     * @param callback
     */
    EgoResult exit( String token, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户注册
     * @param tbUser
     * @param request
     * @param response
     * @return
     */
    EgoResult regist(TbUser tbUser, HttpServletRequest request, HttpServletResponse response);

    /**
     * 检查用户名或者手机号是否存在
     * @param usernameOrPassword
     * @param type
     * @return
     */
    EgoResult checkUserNameOrPhoneNum(String usernameOrPassword, int type);
}
