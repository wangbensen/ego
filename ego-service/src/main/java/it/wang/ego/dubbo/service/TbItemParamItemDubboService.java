package it.wang.ego.dubbo.service;

import it.wang.ego.pojo.TbItemParamItem;

public interface TbItemParamItemDubboService {
    /**
     * 根据商品id 查询商品的规格参数
     * @param id
     * @return
     */
    TbItemParamItem param(Long id);
}
