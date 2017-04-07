package com.bobomee.android.mentions.edit.listener;

import com.bobomee.android.mentions.edit.MentionEditText;
import com.bobomee.android.mentions.model.Range;
import java.util.ArrayList;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/2 汪波 first commit
 */
public class RangeListenerManager {

  private final ArrayList<Range> mRangeArrayList;

  public RangeListenerManager(MentionEditText mentionEditText) {
    this.mRangeArrayList = mentionEditText.getRangeArrayList();
  }

  public Range getRangeOfClosestMentionString(int selStart, int selEnd) {
    if (mRangeArrayList == null) {
      return null;
    }
    for (Range range : mRangeArrayList) {
      if (range.contains(selStart, selEnd)) {
        return range;
      }
    }
    return null;
  }

  public Range getRangeOfNearbyMentionString(int selStart, int selEnd) {
    if (mRangeArrayList == null) {
      return null;
    }
    for (Range range : mRangeArrayList) {
      if (range.isWrappedBy(selStart, selEnd)) {
        return range;
      }
    }
    return null;
  }
}
