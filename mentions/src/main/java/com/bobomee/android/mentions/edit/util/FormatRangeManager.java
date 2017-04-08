package com.bobomee.android.mentions.edit.util;

import com.bobomee.android.mentions.Range;
import com.bobomee.android.mentions.RangeManager;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/8 汪波 first commit
 */
public class FormatRangeManager extends RangeManager {

  public CharSequence formatCharSequence(CharSequence charSequence) {
    String text = charSequence.toString();
    if (isEmpty()) {
      return text;
    }

    int lastRangeTo = 0;
    CharSequence newChar = "";
    ArrayList<? extends Range> ranges = get();
    Collections.sort(ranges);

    StringBuilder builder = new StringBuilder("");
    for (Range range : ranges) {
      if (range instanceof FormatRange) {
        FormatRange formatRange = (FormatRange) range;
        FormatRange.FormatData formatData = formatRange.getConvert();
        newChar = formatData.formatCharSequence();
      }
      builder.append(text.substring(lastRangeTo, range.getFrom()));
      builder.append(newChar);
      lastRangeTo = range.getTo();
    }
    builder.append(text.substring(lastRangeTo));

    return builder.toString();
  }
}
