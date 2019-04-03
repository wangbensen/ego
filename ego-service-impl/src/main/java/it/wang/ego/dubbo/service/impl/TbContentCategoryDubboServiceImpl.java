package it.wang.ego.dubbo.service.impl;

import it.wang.ego.dubbo.service.TbContentCategoryDubboService;
import it.wang.ego.mapper.TbContentCategoryMapper;
import it.wang.ego.pojo.TbContentCategory;
import it.wang.ego.pojo.TbContentCategoryExample;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName TbContentCategoryDubboServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/39:03
 * @Version 1.0
 **/
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {
    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<TbContentCategory> selByPid(long pid) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        return tbContentCategories;
    }

    @Override
    public int insTbContentcategory(TbContentCategory tbContentCategory,TbContentCategory tbContentCategory2) throws Exception {
        //首先新增
        int i = tbContentCategoryMapper.insertSelective(tbContentCategory);
        int i1 = tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory2);
        if (i+i1!=2){
            throw new Exception();
        }
        return 1;
    }

    @Override
    public int updTbContentcategory(TbContentCategory tbContentCategory) throws Exception{

        int i1 = tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        if (i1!=1){
            throw new Exception();
        }
        return i1;
    }
    @Override
    public TbContentCategory selById(long id) {
        return tbContentCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delTbContentCateGory(Long id) throws Exception {

        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        int i = tbContentCategoryMapper.deleteByPrimaryKey(id);
        if (i != 1) {
            throw new Exception();
        }
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andParentIdEqualTo(tbContentCategory.getParentId());
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        if (tbContentCategories == null || tbContentCategories.size() == 0) {
            //修改父状态的isParent 的状态为false
            TbContentCategory tbContentCategory2 =tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
            tbContentCategory2.setUpdated(new Date());
            tbContentCategory2.setIsParent(false);
            i += tbContentCategoryMapper.updateByPrimaryKey(tbContentCategory2);
            if (i != 2) {
                throw new Exception();
            }
        }
        return 1;
    }
}
