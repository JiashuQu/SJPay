package com.sujin.sjpay.entity;

/**
 * Created by czb on 2016/7/26.
 */
public class TabData {
    private String tag;
    private String iconUrl;
    private String iconUrlSelect;
    private String name;
    private int resourceId;
    private int resourceIdSelect;

    public TabData() {
    }

    /**
     * 首页底部图标
     * @param tag
     * @param name
     * @param iconUrl
     * @param iconUrlSelect
     * @param resourceId
     * @param resourceIdSelect
     */
    public TabData(String tag, String name, String iconUrl, String iconUrlSelect, int resourceId, int resourceIdSelect) {
        this.tag = tag;
        this.iconUrl = iconUrl;
        this.iconUrlSelect = iconUrlSelect;
        this.name = name;
        this.resourceId = resourceId;
        this.resourceIdSelect = resourceIdSelect;
    }

    public TabData(String tag, String name, int resourceId, int resourceIdSelect) {
        this.tag = tag;
        this.name = name;
        this.resourceId = resourceId;
        this.resourceIdSelect = resourceIdSelect;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceIdSelect() {
        return resourceIdSelect;
    }

    public void setResourceIdSelect(int resourceIdSelect) {
        this.resourceIdSelect = resourceIdSelect;
    }

    public String getIconUrlSelect() {
        return iconUrlSelect;
    }

    public void setIconUrlSelect(String iconUrlSelect) {
        this.iconUrlSelect = iconUrlSelect;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "TabData{" +
                "tag='" + tag + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", iconUrlSelect='" + iconUrlSelect + '\'' +
                ", name='" + name + '\'' +
                ", resourceIdSelect=" + resourceIdSelect +
                '}';
    }
}
