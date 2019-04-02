package it.wang.ego.dubbo.service.impl;

import it.wang.ego.dubbo.service.TbItemDescDubboService;
import it.wang.ego.mapper.TbItemDescMapper;
import it.wang.ego.pojo.TbItemDesc;

import javax.annotation.Resource;



public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	@Override
	public int insDesc(TbItemDesc itemDesc) {
		return tbItemDescMapper.insert(itemDesc);
	}

}
