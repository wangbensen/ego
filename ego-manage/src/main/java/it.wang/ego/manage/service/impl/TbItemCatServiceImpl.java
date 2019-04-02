package it.wang.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;

import it.ego.commons.pojo.EasyUiTree;
import it.ego.commons.utils.FtpUtil;
import it.ego.commons.utils.IDUtils;
import it.wang.ego.dubbo.service.TbItemCatDubboService;
import it.wang.ego.manage.service.TbItemCatService;
import it.wang.ego.pojo.TbItemCat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TbItemCatServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/111:20
 * @Version 1.0
 **/
@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;
    @Override
    public List<EasyUiTree> show(long pid) {
        List<TbItemCat> list =
                tbItemCatDubboServiceImpl.show(pid);
        List<EasyUiTree> listTree = new ArrayList();
        for (TbItemCat cat : list) {
            EasyUiTree tree = new EasyUiTree();
            tree.setId(cat.getId());
            tree.setText(cat.getName());
            tree.setState(cat.getIsParent()?"closed":"open");
            listTree.add(tree);
        }
        return listTree;
    }
}
