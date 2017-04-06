package com.bobomee.android.mentions.model;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/3 汪波 first commit
 */
public class TagRange extends Range {

  public TagRange(String name, int from, int to) {
    this("tag-id", name, from, to);
  }

  public TagRange(String id, String name, int from, int to) {
    super(id, name, from, to);
    setType(TYPE_TAG);
  }
}
