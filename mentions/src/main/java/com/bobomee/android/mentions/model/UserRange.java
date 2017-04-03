package com.bobomee.android.mentions.model;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */
public class UserRange extends Range {
  public UserRange(String id, String name, int from, int to) {
    super(id, name, from, to);

    setType(TYPE_USER);
  }
}
