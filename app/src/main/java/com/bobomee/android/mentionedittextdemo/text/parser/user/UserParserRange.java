package com.bobomee.android.mentionedittextdemo.text.parser.user;

import com.bobomee.android.mentions.model.Range;

/**
 * Project ID：
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/9 汪波 first commit
 */
public class UserParserRange extends Range {
  private final String source;

  private String name;
  private String id;

  private int namePatternStart;
  private int namePatternEnd;

  public UserParserRange(int from, int to, String source) {
    super(from, to);
    this.source = source;

    parseAttribute();
  }

  private void parseAttribute() {
    int indexOf = source.indexOf(",id=");
    if (indexOf > 0) {

      name = source.substring(1, indexOf);
      id = source.substring(indexOf + 4, source.length() - 1);
    }
  }

  public void setStart(int start) {
    namePatternStart = start;
    namePatternEnd = namePatternStart + name.length();
  }

  public String getSource() {
    return source;
  }

  public String getName() {
    return name;
  }

  public String getId() {
    return id;
  }

  public int getNamePatternStart() {
    return namePatternStart;
  }

  public int getNamePatternEnd() {
    return namePatternEnd;
  }

  @Override public String toString() {
    return " name='" + name + '\'' +
        ", id='" + id + '\'';
  }
}
