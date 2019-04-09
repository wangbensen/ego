package it.wang.ego.dubbo.service.impl;

import it.wang.ego.dubbo.service.TbItemDescDubboService;
import it.wang.ego.mapper.TbItemDescMapper;
import it.wang.ego.pojo.TbItemDesc;
import it.wang.ego.pojo.TbItemExample;

import javax.annotation.Resource;



public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public int insDesc(TbItemDesc itemDesc) {
		return tbItemDescMapper.insert(itemDesc);
	}

	@Override
	public TbItemDesc selTbItemDescById(Long id) {
		return tbItemDescMapper.selectByPrimaryKey(id);
	}

}
