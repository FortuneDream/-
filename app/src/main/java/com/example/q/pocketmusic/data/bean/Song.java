package com.example.q.pocketmusic.data.bean;

import com.example.q.pocketmusic.config.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 鹏君 on 2016/8/28.
 */
public class Song implements Serializable {
    private String name;//曲谱名字
    private String url;//曲谱url
    private String artist;//所属，不局限于艺术家
    private int typeId;//乐器类型id
    private String date;//时间
    private List<String> ivUrl;//曲谱集合
    private String content;//曲谱描述
    private int searchFrom;//曲谱来自

    public Song(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getIvUrl() {
        return ivUrl;
    }

    public void setIvUrl(List<String> ivUrl) {
        this.ivUrl = ivUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSearchFrom() {
        return searchFrom;
    }

    public void setSearchFrom(int searchFrom) {
        this.searchFrom = searchFrom;
    }





    public Song() {
    }

    public Song(String name, String url) {
        this.name = name;
        this.url = url;
        this.artist = "未知";
        this.typeId = 0;
        this.date = "未知";
        this.ivUrl = null;
        this.content = "暂无";
        this.searchFrom = Constant.FROM_SEARCH_NET;//默认来自搜索
    }



}
