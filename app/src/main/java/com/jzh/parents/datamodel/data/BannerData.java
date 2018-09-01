package com.jzh.parents.datamodel.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 轮播图
 *
 * @author Ding
 */
public class BannerData implements Serializable {

    private static final long serialVersionUID = -8998599127251754569L;

    public String imageUrl;
    public String linkUrl;

    public BannerData(String imageUrl, String linkUrl) {

        this.imageUrl = imageUrl;
        this.linkUrl = linkUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public static List<BannerData> parse(String jsonStr) {
        List<BannerData> adList = new ArrayList<BannerData>();
        return adList;
    }
}
