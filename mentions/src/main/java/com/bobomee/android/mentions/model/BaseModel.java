package com.bobomee.android.mentions.model;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Project ID：400YF17051<br/>
 * Resume: 基本用户信息
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/3 汪波 first commit
 */
public class BaseModel {

  public static final int TYPE_USER = 1;
  public static final int TYPE_TAG = 1 << 1;
  public static final int TYPE_URL = 1<<3;

  @IntDef({ TYPE_USER, TYPE_TAG,TYPE_URL }) @Retention(RetentionPolicy.SOURCE) public @interface TYPE {
  }

  private final String mId;
  private final String mLable;

  @TYPE private int mType;

  public BaseModel(String id, String lable) {
    mId = id;
    mLable = lable;
  }

  public String getId() {
    return mId;
  }

  public String getLable() {
    return mLable;
  }

  @TYPE public int getType() {
    return mType;
  }

  public void setType(@TYPE int type) {
    mType = type;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BaseModel baseModel = (BaseModel) o;

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
    return "BaseModel{" +
        "mId='" + mId + '\'' +
        ", mLable='" + mLable + '\'' +
        ", mType=" + mType +
        '}';
  }
}
