package it.wang.ego.dubbo.service.impl;

import it.wang.ego.dubbo.service.TbItemCatDubboService;
import it.wang.ego.mapper.TbItemCatMapper;
import it.wang.ego.mapper.TbItemDescMapper;
import it.wang.ego.mapper.TbItemMapper;
import it.wang.ego.pojo.TbItem;
import it.wang.ego.pojo.TbItemCat;
import it.wang.ego.pojo.TbItemCatExample;
import it.wang.ego.pojo.TbItemDesc;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TbItemCatDubboServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/111:05
 * @Version 1.0
 **/
public class TbItemCatDubboServiceImpl implements TbItemCatDubboService {
    @Resource
    private TbItemCatMapper tbItemCatMapper;

    @Resource
    private TbItemMapper tbItemMapper;
    @Override
    public List<TbItemCat> show(long pid) {
        TbItemCatExample example =new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(pid);
        return tbItemCatMapper.selectByExample(example);
    }
    @Override
    public TbItemCat selById(long id) {
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(id);
        return tbItemCat;
    }
}
