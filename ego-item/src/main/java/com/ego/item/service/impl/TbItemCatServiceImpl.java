package com.ego.item.service.impl;

import java.util.ArrayList;
import java.util.List;

import it.wang.ego.dubbo.service.TbItemCatDubboService;
import it.wang.ego.pojo.TbItemCat;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.pojo.PortalMenu;
import com.ego.item.pojo.PortalMenuNode;
import com.ego.item.service.TbItemCatService;


@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboServiceImpl;


    public PortalMenu showCatMenu() {
        // 首先查询所有的 顶级父亲数据
        List<TbItemCat> list = tbItemCatDubboServiceImpl.show(0);
        PortalMenu pm = new PortalMenu();
        pm.setData(selAllMenu(list));
        return pm;
    }

    private List<Object> selAllMenu(List<TbItemCat> list) {
        List<Object> listNode = new ArrayList();
        for (TbItemCat tbItemCat : list) {
            if (tbItemCat.getIsParent()) {
                PortalMenuNode pmd = new PortalMenuNode();
                pmd.setU("/products/" + tbItemCat.getId() + ".html");
                pmd.setN("<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
                pmd.setI(selAllMenu(tbItemCatDubboServiceImpl.show(tbItemCat.getId())));
                listNode.add(pmd);
            } else {
                listNode.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
            }
        }
        return listNode;
    }
}
