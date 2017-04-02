package com.bobomee.android.mentionedittext;

import android.support.annotation.NonNull;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 * //helper class to record the position of mention string in EditText
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/2 汪波 first commit
 */
public class Range implements Comparable<Range> {
  private String id;
  private String name;
  private int from;
  private int to;

  public Range(String id, String name, int from, int to) {
    this.id = id;
    this.name = name;
    this.from = from;
    this.to = to;
  }

  public boolean isWrapped(int start, int end) {
    return from >= start && to <= end;
  }

  public boolean isWrappedBy(int start, int end) {
    return (start > from && start < to) || (end > from && end < to);
  }

  public boolean contains(int start, int end) {
    return from <= start && to >= end;
  }

  public boolean isEqual(int start, int end) {
    return (from == start && to == end) || (from == end && to == start);
  }

  public int getAnchorPosition(int value) {
    if ((value - from) - (to - value) >= 0) {
      return to;
    } else {
      return from;
    }
  }

  public void setOffset(int offset) {
    from += offset;
    to += offset;
  }

  @Override public int compareTo(@NonNull Range o) {
    return from - o.from;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getFrom() {
    return from;
  }

  public void setFrom(int from) {
    this.from = from;
  }

  public int getTo() {
    return to;
  }

  public void setTo(int to) {
    this.to = to;
  }
}

