package com.ego.item.pojo;

import java.util.List;

/**
 * @ClassName ParamItem
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1017:00
 * @Version 1.0
 **/
public class ParamItem {

    private String group;
    private List<ParamNode> params;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ParamNode> getParams() {
        return params;
    }

    public void setParams(List<ParamNode> params) {
        this.params = params;
    }
}
