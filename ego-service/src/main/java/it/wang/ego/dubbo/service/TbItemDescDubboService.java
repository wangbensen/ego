package it.wang.ego.dubbo.service;

import it.wang.ego.pojo.TbItemDesc;

public interface TbItemDescDubboService {
	/**
	 * 新增
	 * @param itemDesc
	 * @return
	 */
	int insDesc(TbItemDesc itemDesc);

	/**
	 * 根据id 查询商品描述信息
	 * @param id
	 * @return
	 */
	TbItemDesc selTbItemDescById(Long id);
}
