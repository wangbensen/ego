package it.ego.commons.pojo;


import it.wang.ego.pojo.TbItem;

/**
 * @ClassName TbItemChild
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/815:17
 * @Version 1.0
 **/
public class TbItemChild extends TbItem {
    private String[] images;
    private Boolean enough;

    public Boolean getEnough() {
        return enough;
    }

    public void setEnough(Boolean enough) {
        this.enough = enough;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
