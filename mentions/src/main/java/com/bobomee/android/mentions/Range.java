package com.bobomee.android.mentions;

import android.support.annotation.NonNull;

/**
 * Resume:
 * //helper class mTo record the position of mention string in EditText
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/2 汪波 first commit
 */
public class Range implements Comparable<Range> {

  private int mFrom;
  private int mTo;

  public Range(int from, int to) {
    this.mFrom = from;
    this.mTo = to;
  }

  public boolean isWrapped(int start, int end) {
    return mFrom >= start && mTo <= end;
  }

  public boolean isWrappedBy(int start, int end) {
    return (start > mFrom && start < mTo) || (end > mFrom && end < mTo);
  }

  public boolean contains(int start, int end) {
    return mFrom <= start && mTo >= end;
  }

  public boolean isEqual(int start, int end) {
    return (mFrom == start && mTo == end) || (mFrom == end && mTo == start);
  }

  public int getAnchorPosition(int value) {
    if ((value - mFrom) - (mTo - value) >= 0) {
      return mTo;
    } else {
      return mFrom;
    }
  }

  public void setOffset(int offset) {
    mFrom += offset;
    mTo += offset;
  }

  @Override public int compareTo(@NonNull Range o) {
    return mFrom - o.mFrom;
  }

  public int getFrom() {
    return mFrom;
  }

  public void setFrom(int from) {
    this.mFrom = from;
  }

  public int getTo() {
    return mTo;
  }

  public void setTo(int to) {
    this.mTo = to;
  }

}

