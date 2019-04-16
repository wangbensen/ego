package it.wang.ego.dubbo.service;

import it.wang.ego.pojo.TbUser;

public interface TbUserDubboService {
    /**
     * 根据用户名和密码查询当前用户
     * @param tbUser
     * @return
     */
    public TbUser login(TbUser tbUser);

    /**
     * 用户注册
     * @param tbUser
     * @return
     */
    public int  regist(TbUser tbUser);

    /**
     * 检查用户名或者密码是否正确
     * @param tbUser
     * @return
     */
    public int  checkUserNameOrPassword(String userNameOrPassword ,int type);
}
