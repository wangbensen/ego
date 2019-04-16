package com.ego.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.search.service.TbItemService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Time;
import java.util.Map;

/**
 * @ClassName TbItemController
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/815:19
 * @Version 1.0
 **/
@Controller
public class TbItemController {

    @Autowired
    private TbItemService tbItemServiceImpl;

    /**
     * 给商品信息添加到solr 索引库中
     *
     * @return
     */
    @RequestMapping(value = "solr/init", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String initSolr() {
        long l = System.currentTimeMillis();
        try {
            tbItemServiceImpl.init();
            long l1 = System.currentTimeMillis();
            return "成功" + (l1 - l) / 1000 + "秒";
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }

    /**
     * 查询商品信息
     *
     * @param model
     * @param page
     * @param rows
     * @param q
     * @return
     */
    @RequestMapping("search.html")
    public String search(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "12") int rows,  @RequestParam(defaultValue = "*")String q) {
        //应为是get 请求 所以 q 会乱码 本地需要从新编码
        try {
            q = new String(q.getBytes("ISO-8859-1"), "utf-8");
            Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
            //封装数据
            model.addAttribute("query", q);
            model.addAttribute("itemList", map.get("itemList"));
            model.addAttribute("totalPages", map.get("totalPages"));
            model.addAttribute("page", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "search";
    }

    /**
     * 添加商品到索引库
     *
     * @param map
     * @return
     */
    @RequestMapping("solr/add")
    public int add(@RequestBody Map<String, Object> map) {
        try {
            return tbItemServiceImpl.add((Map<String, Object>) map.get("item"), map.get("desc").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
