package it.wang.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.dubbo.service.TbItemDubboService;
import it.wang.ego.mapper.TbItemCatMapper;
import it.wang.ego.mapper.TbItemDescMapper;
import it.wang.ego.mapper.TbItemParamItemMapper;
import it.wang.ego.pojo.*;
import it.wang.ego.mapper.TbItemMapper;

public class TbItemDubboServiceImpl implements TbItemDubboService {
    @Resource
    private TbItemMapper tbItemMapper;
    @Resource
    private TbItemDescMapper tbItemDescMapper;
    @Resource
    private TbItemCatMapper tbItemCatMapper;
    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public EasyUIDataGrid show(int page, int rows) {
        PageHelper.startPage(page, rows);
        //查询全部
        List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
        //分页代码
        //设置分页条件
        PageInfo<TbItem> pi = new PageInfo(list);
        //放入到实体类
        EasyUIDataGrid datagrid = new EasyUIDataGrid();
        datagrid.setRows(pi.getList());
        datagrid.setTotal(pi.getTotal());
        return datagrid;
    }

    @Override
    public int updItemStatus(TbItem tbItem) {
        return tbItemMapper.updateByPrimaryKeySelective(tbItem);
    }

    @Override
    public int insTbItemDesc(TbItem tbItem, TbItemDesc desc, TbItemParamItem tbItemParamItem) throws Exception {
        int index = 0;
        try {
            index = tbItemMapper.insertSelective(tbItem);
            index += tbItemDescMapper.insertSelective(desc);
            index +=  tbItemParamItemMapper.insertSelective(tbItemParamItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (index == 3) {
            return 1;
        } else {
            throw new Exception("新增失败,数据还原");
        }
    }
    @Override
    public List<TbItem> selAllByStatus(byte status) {
        TbItemExample tbItemExample = new TbItemExample();
        tbItemExample.createCriteria().andStatusEqualTo(status);
        return tbItemMapper.selectByExample(tbItemExample);
    }

    @Override
    public TbItem selById(long id) {
//        TbItemExample example = new TbItemExample();
//        example.createCriteria().andStatusEqualTo((byte)1);
//        example.createCriteria().andIdEqualTo(id);
//        return tbItemMapper.selectByExample(example).get(0);
        return tbItemMapper.selectByPrimaryKey(id);
    }

}
