package it.wang.ego.dubbo.service;


import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.pojo.*;

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
	 * 新增包含商品表和商品描述表和商品规格
	 * @param tbItem
	 * @param desc
	 * @return
	 */
	int insTbItemDesc(TbItem tbItem, TbItemDesc desc, TbItemParamItem tbItemParamItem)
			throws Exception;

	/**
	 * 查询所有的商品信息status=1的
	 * @param status
	 * @return
	 */
	List<TbItem> selAllByStatus(byte status );

	/**
	 * 根据商品id 查询商品
	 * @param id
	 * @return
	 */
	TbItem selById(long id);

}
