package it.ego.commons.pojo;

/**
 * @ClassName EasyUiTree
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/111:16
 * @Version 1.0
 **/
public class EasyUiTree {
    private long id;
    private String text;
    private String state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EasyUiTree{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
