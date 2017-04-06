package com.bobomee.android.mentions.model;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/4 汪波 first commit
 */
public class UrlRange extends Range {
  public UrlRange(String name, int from, int to) {
    this("url-id", name, from, to);
  }

  public UrlRange(String id, String name, int from, int to) {
    super(id, name, from, to);
    setType(TYPE_URL);
  }
}
