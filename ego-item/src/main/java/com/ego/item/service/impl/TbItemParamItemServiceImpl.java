package com.ego.item.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.item.pojo.ParamItem;
import com.ego.item.service.TbItemParamItemSerice;
import it.ego.commons.utils.JsonUtils;
import it.wang.ego.dubbo.service.TbItemParamItemDubboService;
import it.wang.ego.pojo.TbItemParamItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TbItemParamItemServiceImpl
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1016:20
 * @Version 1.0
 **/
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemSerice {
    @Reference
    private TbItemParamItemDubboService itemParamItemDubboServiceImpl;

    public String param(Long id) {
        TbItemParamItem param = itemParamItemDubboServiceImpl.param(id);
        String paramData = param.getParamData();
        List<ParamItem> paramItems = JsonUtils.jsonToList(paramData, ParamItem.class);
        StringBuffer sb = new StringBuffer();
        //由于页面没有样式 所以咋后台给样式
        for(ParamItem paramItem:paramItems){
            sb.append("<table width='500' style='color:gray;'>");
            for(int i=0;i<paramItem.getParams().size();i++){
                if (i==0){
                    sb.append("<tr>");
                    sb.append("<td align='right'width='30%'>"+paramItem.getGroup()+"</td>");
                    sb.append("<td align='right'width='30%'>"+paramItem.getParams().get(i).getK()+"</td>");
                    sb.append("<td >"+paramItem.getParams().get(i).getV()+"</td>");
                    sb.append("</tr>");
                }else{
                    sb.append("<tr>");
                    sb.append("<td > </td>");
                    sb.append("<td align='right'width='30%'>"+paramItem.getParams().get(i).getK()+"</td>");
                    sb.append("<td >"+paramItem.getParams().get(i).getV()+"</td>");
                    sb.append("</tr>");
                }
            }
             sb.append("</table>");
            sb.append("<hr style='color:gray;'/>");
        }
        return sb.toString();
    }
}
