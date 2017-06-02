package com.example.q.pocketmusic.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.q.pocketmusic.config.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 鹏君 on 2016/8/28.
 */
public class Song implements Parcelable,Serializable {
    private String name;//曲谱名字
    private String url;//曲谱url
    private String artist;//所属，不局限于艺术家
    private int typeId;//乐器类型id
    private String date;//时间
    private List<String> ivUrl;//曲谱集合
    private String content;//曲谱描述
    private int searchFrom;//曲谱来自
    private boolean needGrade;//是否需要积分

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

    public boolean isNeedGrade() {
        return needGrade;
    }

    public void setNeedGrade(boolean needGrade) {
        this.needGrade = needGrade;
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
        this.needGrade = false;//默认不需要积分
    }

    protected Song(Parcel in) {
        name = in.readString();
        url = in.readString();
        artist = in.readString();
        typeId = in.readInt();
        date = in.readString();
        ivUrl=in.createStringArrayList();
        content = in.readString();
        searchFrom = in.readInt();
        needGrade = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(artist);
        dest.writeInt(typeId);
        dest.writeString(date);
        dest.writeStringList(ivUrl);
        dest.writeString(content);
        dest.writeInt(searchFrom);
        dest.writeByte((byte) (needGrade ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
