package com.bobomee.android.mentionedittextdemo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import com.bobomee.android.mentionedittextdemo.model.Lable;
import com.bobomee.android.mentions.edit.MentionEditText;
import com.bobomee.android.mentions.model.Range;
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
public class MentionEditTextEnhance extends MentionEditText {
  public MentionEditTextEnhance(Context context) {
    super(context);
  }

  public MentionEditTextEnhance(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MentionEditTextEnhance(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public Range provideRange(int start, int end, CharSequence lable) {
    if (lable.toString().startsWith("@")) {
      return new Lable("user_id", lable, start, end, Lable.TYPE_USER);
    } else if (lable.toString().startsWith("#")) {
      return new Lable("tag_id", lable, start, end, Lable.TYPE_TAG);
    }
    return super.provideRange(start, end, lable);
  }

  @Override public int provideRangeColor(Range range) {
    int result = super.provideRangeColor(range);
    if (range instanceof Lable) {
      Lable lable = (Lable) range;
      int type = lable.getType();
      switch (type) {
        case Lable.TYPE_USER:
          result = Color.BLUE;
          break;
        case Lable.TYPE_TAG:
          result = Color.CYAN;
          break;
      }
    }
    return result;
  }

  public CharSequence convertMetionString() {
    String text = getText().toString();
    if (mRangeManager.isEmpty()) {
      return text;
    }

    StringBuilder builder = new StringBuilder("");
    int lastRangeTo = 0;
    Collections.sort(mRangeManager.get());
    String newChar = "";
    for (Range range : mRangeManager.get()) {

      if (range instanceof Lable) {
        Lable lable = (Lable) range;
        switch (lable.getType()) {
          case Lable.TYPE_USER:
            newChar = String.format("(%s,id=%s)", lable.getLable(), lable.getId());
            break;
          case Lable.TYPE_TAG:
            newChar = String.format("(%s)", lable.getLable());
            break;
        }

        builder.append(text.substring(lastRangeTo, range.getFrom()));
        builder.append(newChar);
        lastRangeTo = range.getTo();
      }
    }

    builder.append(text.substring(lastRangeTo));

    return builder.toString();
  }
}
