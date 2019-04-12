package it.wang.ego.dubbo.service.impl;

import it.wang.ego.dubbo.service.TbItemParamItemDubboService;
import it.wang.ego.mapper.TbItemParamItemMapper;
import it.wang.ego.pojo.TbItemParamItem;
import it.wang.ego.pojo.TbItemParamItemExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName TbItemParamItemDubboServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1016:12
 * @Version 1.0
 **/

public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Override
    public TbItemParamItem param(Long id) {
        TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
        tbItemParamItemExample.createCriteria().andItemIdEqualTo(id);
        List<TbItemParamItem> tbItemParamItems = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
        if (tbItemParamItems!=null&&tbItemParamItems.size()>0){
            return  tbItemParamItems.get(0);
        }
        return null;
    }
}
