package com.bobomee.android.mentionedittextdemo;

import com.bobomee.android.mentions.model.Model;
import java.io.Serializable;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */
public class Tag  extends Model implements Serializable{

  private CharSequence tagLable;
  private CharSequence tagId;

  private CharSequence tagUrl;
  //....

  public Tag(CharSequence tagLable, CharSequence tagId) {
    super("#"+tagLable+"#");
    this.tagLable = tagLable;
    this.tagId = tagId;
  }

  public CharSequence getTagLable() {
    return tagLable;
  }

  public CharSequence getTagId() {
    return tagId;
  }

  public CharSequence getTagUrl() {
    return tagUrl;
  }

  public void setTagUrl(CharSequence tagUrl) {
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
}
