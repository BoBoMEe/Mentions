package com.bobomee.android.mentionedittextdemo.model;

import android.support.annotation.IntDef;
import com.bobomee.android.mentions.model.Range;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * Resume: 基本用户信息
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/3 汪波 first commit
 */
public class Lable extends Range {

  public static final int TYPE_USER = 1;
  public static final int TYPE_TAG = 1 << 1;
  public static final int TYPE_URL = 1<<3;

  @IntDef({ TYPE_USER, TYPE_TAG,TYPE_URL }) @Retention(RetentionPolicy.SOURCE) public @interface TYPE {
  }

  private final String mId;
  private final CharSequence mLable;

  @TYPE private final int mType;

  public Lable(String id, CharSequence lable,int from,int to,int type) {
    super(from, to);
    mId = id;
    mLable = lable;
    mType =type;
  }

  public String getId() {
    return mId;
  }

  public CharSequence getLable() {
    return mLable;
  }

  @TYPE public int getType() {
    return mType;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Lable baseModel = (Lable) o;

    if (mType != baseModel.mType) return false;
    if (mId != null ? !mId.equals(baseModel.mId) : baseModel.mId != null) return false;
    return mLable != null ? mLable.equals(baseModel.mLable) : baseModel.mLable == null;
  }

  @Override public int hashCode() {
    int result = mId != null ? mId.hashCode() : 0;
    result = 31 * result + (mLable != null ? mLable.hashCode() : 0);
    result = 31 * result + mType;
    return result;
  }

  @Override public String toString() {
    return "Lable{" +
        "mId='" + mId + '\'' +
        ", mLable='" + mLable + '\'' +
        ", mType=" + mType +
        '}';
  }
}
