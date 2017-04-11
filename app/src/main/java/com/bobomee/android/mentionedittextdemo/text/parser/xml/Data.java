package com.bobomee.android.mentionedittextdemo.text.parser.xml;

/**
 * Project ID：400YF17051
 * Resume:     <br/>
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/10.汪波.
 */
public class Data {
  // I know this could be an int, but this is just to show you how it works
  public String sectionId;
  public String section;
  public String area;

  public Data() {

  }

  @Override public String toString() {
    return "Data{" +
        "sectionId='" + sectionId + '\'' +
        ", section='" + section + '\'' +
        ", area='" + area + '\'' +
        '}';
  }
}