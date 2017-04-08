package com.bobomee.android.mentionedittextdemo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import com.bobomee.android.mentionedittextdemo.model.Lable;
import com.bobomee.android.mentions.edit.MentionEditText;
import com.bobomee.android.mentions.model.Model;
import com.bobomee.android.mentions.model.Range;
import java.util.Collections;

/**
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

  @Override public <T extends Model> Range provideRange(int start, int end, T model) {
    if (model instanceof User) {
      User user = (User) model;
      return new Lable(start, end,user);
    } else if (model instanceof Tag) {
      Tag tag = (Tag) model;
      return new Lable( start, end,tag);
    }
    return super.provideRange(start, end, model);
  }

  @Override public <T extends Model> int provideRangeColor(T model) {
    if (model instanceof User) {
      return Color.BLUE;
    } else if (model instanceof Tag) {
      return Color.CYAN;
    }
    return super.provideRangeColor(model);
  }

  public CharSequence convertMetionString() {
    String text = getText().toString();
    if (mRangeManager.isEmpty()) {
      return text;
    }

    StringBuilder builder = new StringBuilder("");
    int lastRangeTo = 0;
    Collections.sort(mRangeManager.get());
    String newChar;
    for (Range range : mRangeManager.get()) {
      if (range instanceof Lable) {
        Lable lable = (Lable) range;
        newChar = lable.getFormat();
        builder.append(text.substring(lastRangeTo, range.getFrom()));
        builder.append(newChar);
        lastRangeTo = range.getTo();
      }
    }

    builder.append(text.substring(lastRangeTo));

    return builder.toString();
  }
}
