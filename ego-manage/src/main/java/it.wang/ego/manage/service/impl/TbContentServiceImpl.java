package it.wang.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import it.ego.commons.pojo.EasyUIDataGrid;
import it.wang.ego.dubbo.service.TbContentDubboService;
import it.wang.ego.manage.service.TbContentService;
import it.wang.ego.pojo.TbContent;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ClassName TbContentServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/314:27
 * @Version 1.0
 **/
@Service
public class TbContentServiceImpl implements TbContentService {
    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;
    @Override
    public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
        return tbContentDubboServiceImpl.selContentByPage(categoryId,page,rows);
    }

    @Override
    public int save(TbContent tbContent) throws Exception {
        Date date = new Date();
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        return tbContentDubboServiceImpl.insContent(tbContent);
    }
}
