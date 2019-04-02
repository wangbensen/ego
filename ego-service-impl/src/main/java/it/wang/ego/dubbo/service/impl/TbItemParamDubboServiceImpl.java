package it.wang.ego.dubbo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.dubbo.service.TbItemParamDubboService;
import it.wang.ego.mapper.TbItemParamItemMapper;
import it.wang.ego.mapper.TbItemParamMapper;
import it.wang.ego.pojo.TbItemParam;
import it.wang.ego.pojo.TbItemParamExample;
import it.wang.ego.pojo.TbItemParamItem;
import it.wang.ego.pojo.TbItemParamItemExample;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TbItemParamDubboServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/29:06
 * @Version 1.0
 **/
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {
    @Resource
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public EasyUIDataGrid showPage(int page, int size) {
        Page<Object> objects = PageHelper.startPage(page, size);
        List<TbItemParam> tbItemParamItems = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
        PageInfo pageInfo = new PageInfo(tbItemParamItems);
        EasyUIDataGrid easyUIDataGrid = new EasyUIDataGrid();
        easyUIDataGrid.setRows(pageInfo.getList());
        easyUIDataGrid.setTotal(pageInfo.getTotal());
        return easyUIDataGrid;
    }

    @Override
    public int delByIds(String ids) throws Exception {
        int index=0;
        String[] ids2 = ids.split(",");
        for (int i = 0; i<ids2.length;i++){
            index+= tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(ids2[i]));
        }
        if (index==ids2.length){
            return 1;
        }else {
            throw new Exception("批量删除异常，数据可能已经不存在了");

        }
    }
}
