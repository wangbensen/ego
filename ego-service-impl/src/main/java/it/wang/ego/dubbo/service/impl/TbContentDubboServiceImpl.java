package it.wang.ego.dubbo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.dubbo.service.TbContentDubboService;
import it.wang.ego.mapper.TbContentMapper;
import it.wang.ego.pojo.TbContent;
import it.wang.ego.pojo.TbContentExample;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName TbContentDubboServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/314:21
 * @Version 1.0
 **/
public class TbContentDubboServiceImpl implements TbContentDubboService {
    @Resource
    private TbContentMapper tbContentMapper;
    @Override
    public EasyUIDataGrid selContentByPage(long categoryId, int page, int rows) {
        PageHelper.startPage(page, rows);
        TbContentExample example = new TbContentExample();
        if(categoryId!=0){
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbContent> pi = new PageInfo(list);
        EasyUIDataGrid datagrid = new EasyUIDataGrid();
        datagrid.setRows(pi.getList());
        datagrid.setTotal(pi.getTotal());
        return datagrid;
    }

    @Override
    public int insContent(TbContent tbContent)throws Exception {
        int i = tbContentMapper.insertSelective(tbContent);
        if (i!=1){
            throw  new Exception();
        }
        return 1;
    }

    @Override
    public List<TbContent> selByCount(int count, boolean bool) {
        TbContentExample tbContentExample = new TbContentExample();
        if (bool){
            tbContentExample.setOrderByClause("updated desc");
        }
        if (count!=0){
            PageHelper.startPage(1,count);
            List<TbContent> tbContents = tbContentMapper.selectByExampleWithBLOBs(tbContentExample);
            PageInfo<TbContent> pageInfo=new PageInfo(tbContents);
            return pageInfo.getList();
        }else{
            return  tbContentMapper.selectByExampleWithBLOBs(tbContentExample);
        }
    }
}
