package com.bobomee.android.mentionedittextdemo.model;

import android.support.annotation.IntDef;
import com.bobomee.android.mentionedittextdemo.Tag;
import com.bobomee.android.mentionedittextdemo.User;
import com.bobomee.android.mentions.model.Range;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Resume: 基本用户信息
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/3 汪波 first commit
 */
public class Lable extends Range {

  private static final int TYPE_USER = 1;
  private static final int TYPE_TAG = 1 << 1;
  private static final int TYPE_URL = 1 << 3;

  @IntDef({ TYPE_USER, TYPE_TAG, TYPE_URL }) @Retention(RetentionPolicy.SOURCE)
  private @interface TYPE {
  }

  private final CharSequence mId;
  private final CharSequence mLable;

  @TYPE private final int mType;

  public Lable(int from, int to,User user) {
    super(from, to);
    mId = user.getUserId();
    mLable = user.getCharSequence();
    mType = TYPE_USER;
  }

  public Lable(int from, int to,Tag tag) {
    super(from, to);
    mId = tag.getTagId();
    mLable = tag.getCharSequence();
    mType = TYPE_TAG;
  }

  public String getFormat(){
    String newChar = "";
    switch (getType()) {
      case Lable.TYPE_USER:
        newChar = String.format("(%s,id=%s)", getLable(), getId());
        break;
      case Lable.TYPE_TAG:
        newChar = String.format("(%s,id=%s)", getLable(), getId());
        break;
    }
    return newChar;
  }

  @TYPE private int getType() {
    return mType;
  }

  private CharSequence getId() {
    return mId;
  }

  private CharSequence getLable() {
    return mLable;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Lable lable = (Lable) o;

    if (mType != lable.mType) return false;
    if (mId != null ? !mId.equals(lable.mId) : lable.mId != null) return false;
    return mLable != null ? mLable.equals(lable.mLable) : lable.mLable == null;
  }

  @Override public int hashCode() {
    int result = mId != null ? mId.hashCode() : 0;
    result = 31 * result + (mLable != null ? mLable.hashCode() : 0);
    result = 31 * result + mType;
    return result;
  }
}
