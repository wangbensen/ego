package it.wang.ego.dubbo.service;


import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.pojo.TbItem;
import it.wang.ego.pojo.TbItemCat;
import it.wang.ego.pojo.TbItemDesc;

import java.util.List;

public interface TbItemDubboService {
	/**
	 * 商品分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(int page, int rows);
	/**
	 * 根据id修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	int updItemStatus(TbItem tbItem);
	/**
	 * 新增包含商品表和商品描述表
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	int insTbItemDesc(TbItem tbItem, TbItemDesc desc)
			throws Exception;


}