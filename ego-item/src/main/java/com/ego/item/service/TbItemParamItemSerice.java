package com.ego.item.service;

import it.wang.ego.pojo.TbItemParamItem;

public interface TbItemParamItemSerice {
    /**
     * 根据商品id 查询商品的规格参数
     * @param id
     * @return
     */
    String param(Long id);
}
