package it.wang.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.dubbo.service.TbItemCatDubboService;
import it.wang.ego.dubbo.service.TbItemParamDubboService;
import it.wang.ego.manage.pojo.TbItemParamChild;
import it.wang.ego.manage.service.TbItemCatService;
import it.wang.ego.manage.service.TbItemParamService;
import it.wang.ego.pojo.TbItemParam;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TbItemParamServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/29:10
 * @Version 1.0
 **/
@Service
public class TbItemParamServiceImpl implements TbItemParamService {
    @Reference
    private TbItemParamDubboService tbItemParamDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    /**
     * 分页查询 规格参数
     * @param page
     * @param rows
     * @return
     * @throws Exception
     */
    @Override
    public EasyUIDataGrid showPage(int page, int rows)  {
        EasyUIDataGrid easyUIDataGrid = tbItemParamDubboService.showPage(page, rows);
        List<TbItemParam> list= (List<TbItemParam>) easyUIDataGrid.getRows();

        List<TbItemParamChild> list1=new ArrayList<TbItemParamChild>();
        for (TbItemParam tbItemParam :list){
            TbItemParamChild tbItemParamChild = new TbItemParamChild();
            tbItemParamChild.setId(tbItemParam.getId());
            tbItemParamChild.setCreated(tbItemParam.getCreated());
            tbItemParamChild.setUpdated(tbItemParam.getUpdated());
            tbItemParamChild.setParamData(tbItemParam.getParamData());
            tbItemParamChild.setItemCatId(tbItemParam.getItemCatId());
            String name = tbItemCatDubboService.selById(tbItemParam.getItemCatId()).getName();
            tbItemParamChild.setItemCatName(name);
            list1.add(tbItemParamChild);
        }
        easyUIDataGrid.setRows(list1);
        return easyUIDataGrid;
    }

    @Override
    public int delete(String ids) throws Exception {
        return tbItemParamDubboService.delByIds(ids);
    }

    @Override
    public TbItemParam showParam(long catId) {
        return  tbItemParamDubboService.selByCatid(catId);
    }

    @Override
    public int save(TbItemParam param) {
        Date date = new Date();
        param.setUpdated(date);
        param.setCreated(date);
        return  tbItemParamDubboService.insParam(param);
    }
}
