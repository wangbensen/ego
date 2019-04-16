package it.wang.ego.dubbo.service.impl;

import it.wang.ego.dubbo.service.TbOrderDubboService;
import it.wang.ego.mapper.TbOrderItemMapper;
import it.wang.ego.mapper.TbOrderMapper;
import it.wang.ego.mapper.TbOrderShippingMapper;
import it.wang.ego.pojo.TbOrder;
import it.wang.ego.pojo.TbOrderItem;
import it.wang.ego.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName TbOrderDubboServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/169:02
 * @Version 1.0
 **/

public class TbOrderDubboServiceImpl implements TbOrderDubboService {
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Override
    public int createOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItems, TbOrderShipping tbOrderShipping) throws Exception {
        int index = tbOrderMapper.insert(tbOrder);
            for(TbOrderItem tbOrderItem:tbOrderItems){
                index +=tbOrderItemMapper.insert(tbOrderItem);
            }
        index +=tbOrderShippingMapper.insert(tbOrderShipping);
        if (index==(2+tbOrderItems.size())){
            return 1;
        }else {
            throw  new Exception();
        }
    }
}
