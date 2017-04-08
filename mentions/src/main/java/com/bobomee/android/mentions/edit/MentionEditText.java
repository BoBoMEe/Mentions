/*
 * Copyright 2016 Andy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bobomee.android.mentions.edit;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import com.bobomee.android.mentions.edit.listener.Convert;
import com.bobomee.android.mentions.edit.listener.InsertData;
import com.bobomee.android.mentions.edit.listener.MentionInputConnection;
import com.bobomee.android.mentions.edit.listener.MentionTextWatcher;
import com.bobomee.android.mentions.edit.util.RangeManager;
import com.bobomee.android.mentions.model.Range;
import java.util.Collections;

/**
 * MentionEditText adds some useful features for mention string(@xxxx), such as highlight,
 * intelligent deletion, intelligent selection and '@' input detection, etc.
 *
 * @author Andy
 */
public class MentionEditText extends EditText {
  private Runnable mAction;

  private boolean mIsSelected;

  public MentionEditText(Context context) {
    super(context);
    init();
  }

  public MentionEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public MentionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @Override public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
    return new MentionInputConnection(super.onCreateInputConnection(outAttrs), true, this);
  }

  @Override public void setText(final CharSequence text, BufferType type) {
    super.setText(text, type);
    //hack, put the cursor at the end of text after calling setText() method
    if (mAction == null) {
      mAction = new Runnable() {
        @Override public void run() {
          setSelection(getText().length());
        }
      };
    }
    post(mAction);
  }

  @Override protected void onSelectionChanged(int selStart, int selEnd) {
    super.onSelectionChanged(selStart, selEnd);
    //avoid infinite recursion after calling setSelection()
    if (null != mRangeManager && !mRangeManager.isEqual(selStart, selEnd)) {
      //if user cancel a selection of mention string, reset the state of 'mIsSelected'
      Range closestRange = mRangeManager.getRangeOfClosestMentionString(selStart, selEnd);
      if (closestRange != null && closestRange.getTo() == selEnd) {
        mIsSelected = false;
      }

      Range nearbyRange = mRangeManager.getRangeOfNearbyMentionString(selStart, selEnd);
      //if there is no mention string nearby the cursor, just skip
      if (null != nearbyRange) {
        //forbid cursor located in the mention string.
        if (selStart == selEnd) {
          setSelection(nearbyRange.getAnchorPosition(selStart));
        } else {
          if (selEnd < nearbyRange.getTo()) {
            setSelection(selStart, nearbyRange.getTo());
          }
          if (selStart > nearbyRange.getFrom()) {
            setSelection(nearbyRange.getFrom(), selEnd);
          }
        }
      }
    }
  }

  public void insert(InsertData insertData) {
    if (null != insertData) {
      CharSequence charSequence = insertData.provideCharSequence();
      Editable editable = getText();
      int start = getSelectionStart();
      int end = start + charSequence.length();
      editable.insert(start, charSequence);
      Range range = insertData.provideRange(start, end);
      mRangeManager.add(range);

      int color = insertData.provideColor();
      editable.setSpan(new ForegroundColorSpan(color), start, end,
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
  }

  public void insert(CharSequence charSequence) {
    insert(new Default(charSequence));
  }

  class Default implements InsertData {

    private final CharSequence charSequence;

    public Default(CharSequence charSequence) {
      this.charSequence = charSequence;
    }

    @Override public CharSequence provideCharSequence() {
      return charSequence;
    }

    @Override public Range provideRange(int start, int end) {
      return new Range(start, end, new DEFAULT());
    }

    @Override public int provideColor() {
      return Color.RED;
    }

    class DEFAULT implements Convert {
      @Override public CharSequence covert() {
        return charSequence;
      }
    }
  }

  public CharSequence convertMetionString() {
    String text = getText().toString();
    if (mRangeManager.isEmpty()) {
      return text;
    }

    StringBuilder builder = new StringBuilder("");
    int lastRangeTo = 0;
    Collections.sort(mRangeManager.get());
    CharSequence newChar;
    for (Range range : mRangeManager.get()) {
      Convert convert = range.getConvert();
      newChar = convert.covert();
      builder.append(text.substring(lastRangeTo, range.getFrom()));
      builder.append(newChar);
      lastRangeTo = range.getTo();
    }

    builder.append(text.substring(lastRangeTo));

    return builder.toString();
  }

  public void clear() {
    mRangeManager.clear();
    setText("");
  }

  protected RangeManager mRangeManager;

  private void init() {
    mRangeManager = new RangeManager();
    //disable suggestion
    addTextChangedListener(new MentionTextWatcher(this));
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  public RangeManager getRangeManager() {
    return mRangeManager;
  }

  @Override public boolean isSelected() {
    return mIsSelected;
  }

  @Override public void setSelected(boolean selected) {
    mIsSelected = selected;
  }
}
