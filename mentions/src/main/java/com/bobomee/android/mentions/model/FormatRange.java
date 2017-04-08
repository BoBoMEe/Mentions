package com.bobomee.android.mentions.model;

/**
 * Project ID：400YF17051
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/8 汪波 first commit
 */
public class FormatRange extends Range {

  private FormatData convert;

  public FormatRange(int from, int to, FormatData convert) {
    super(from, to);
    this.convert = convert;
  }

  public FormatData getConvert() {
    return convert;
  }

  public void setConvert(FormatData convert) {
    this.convert = convert;
  }

  public interface FormatData {

    CharSequence formatCharSequence();
  }
}
