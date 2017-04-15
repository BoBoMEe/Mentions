package com.bobomee.android.mentionedittextdemo.text.parser;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/15 汪波 first commit
 */
public class TagBean {

  private String name;
  private String id;

  public TagBean(String name, String id) {
    this.name = name;
    this.id = id;
  }

  @Override public String toString() {
    return "TagBean{" +
        "name='" + name + '\'' +
        ", id='" + id + '\'' +
        '}';
  }
}
