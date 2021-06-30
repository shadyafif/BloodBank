
package com.example.bloodbank.data.model.PostsData;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "ArticleData")
public class Datum implements Parcelable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("thumbnail_full_path")
    @Expose
    private String thumbnailFullPath;
    @SerializedName("is_favourite")
    @Expose
    private boolean isFavourite;


    public Datum() {


    }

    @Ignore
    public Datum(String title, String content, String thumbnail, String publishDate, String categoryId, String thumbnailFullPath, boolean isFavourite) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.publishDate = publishDate;
        this.categoryId = categoryId;
        this.thumbnailFullPath = thumbnailFullPath;
        this.isFavourite = isFavourite;
    }
    protected Datum(Parcel in) {
        id = in.readInt();
        title = in.readString();
        content = in.readString();
        thumbnail = in.readString();
        publishDate = in.readString();
        categoryId = in.readString();
        thumbnailFullPath = in.readString();
        isFavourite = in.readByte() != 0;
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getThumbnailFullPath() {
        return thumbnailFullPath;
    }

    public void setThumbnailFullPath(String thumbnailFullPath) {
        this.thumbnailFullPath = thumbnailFullPath;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(thumbnail);
        dest.writeString(publishDate);
        dest.writeString(categoryId);
        dest.writeString(thumbnailFullPath);
        dest.writeByte((byte) (isFavourite ? 1 : 0));
    }
}
