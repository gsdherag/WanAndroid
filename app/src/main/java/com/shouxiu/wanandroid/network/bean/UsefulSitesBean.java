package com.shouxiu.wanandroid.network.bean;

/**
 * @author yeping
 * @date 2018/3/8 11:22
 * TODO
 */

public class UsefulSitesBean {

    /**
     * icon :
     * id : 6
     * link : https://juejin.im/timeline/android
     * name : 掘金
     * order : 6
     * visible : 1
     */

    private String icon;
    private int id;
    private String link;
    private String name;
    private int order;
    private int visible;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
