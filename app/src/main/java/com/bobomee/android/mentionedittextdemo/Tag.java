package com.bobomee.android.mentionedittextdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */
public class Tag  implements Parcelable{

  private String tagLable;
  private String tagId;

  private String tagUrl;
  //....

  public Tag(String tagLable, String tagId) {
    this.tagLable = tagLable;
    this.tagId = tagId;
  }

  protected Tag(Parcel in) {
    tagLable = in.readString();
    tagId = in.readString();
    tagUrl = in.readString();
  }

  public static final Creator<Tag> CREATOR = new Creator<Tag>() {
    @Override public Tag createFromParcel(Parcel in) {
      return new Tag(in);
    }

    @Override public Tag[] newArray(int size) {
      return new Tag[size];
    }
  };

  public String getTagLable() {
    return tagLable;
  }

  public void setTagLable(String tagLable) {
    this.tagLable = tagLable;
  }

  public String getTagId() {
    return tagId;
  }

  public void setTagId(String tagId) {
    this.tagId = tagId;
  }

  public String getTagUrl() {
    return tagUrl;
  }

  public void setTagUrl(String tagUrl) {
    this.tagUrl = tagUrl;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Tag tag = (Tag) o;

    if (tagLable != null ? !tagLable.equals(tag.tagLable) : tag.tagLable != null) return false;
    if (tagId != null ? !tagId.equals(tag.tagId) : tag.tagId != null) return false;
    return tagUrl != null ? tagUrl.equals(tag.tagUrl) : tag.tagUrl == null;
  }

  @Override public int hashCode() {
    int result = tagLable != null ? tagLable.hashCode() : 0;
    result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
    result = 31 * result + (tagUrl != null ? tagUrl.hashCode() : 0);
    return result;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(tagLable);
    dest.writeString(tagId);
    dest.writeString(tagUrl);
  }
}
