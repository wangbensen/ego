package it.wang.ego.dubbo.service;

import it.wang.ego.pojo.TbUser;

public interface TbUserDubboService {
    /**
     * 根据用户名和密码查询当前用户
     * @param tbUser
     * @return
     */
    public TbUser login(TbUser tbUser);
}
