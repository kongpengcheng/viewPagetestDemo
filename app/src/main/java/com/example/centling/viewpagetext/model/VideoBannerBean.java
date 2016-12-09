package com.example.centling.viewpagetext.model;

import java.io.Serializable;

/**
 * Created by kong.harry on 2016/10/19.
 */
public class VideoBannerBean implements Serializable {
    private String video;
    private String picture;
    private int type;
    private String goods_id;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
