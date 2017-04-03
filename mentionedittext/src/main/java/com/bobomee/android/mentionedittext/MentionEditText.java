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

package com.bobomee.android.mentionedittext;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import com.bobomee.android.mentionedittext.listener.MentionTextWatcher;
import com.bobomee.android.mentionedittext.listener.OnMentionInputListener;
import com.bobomee.android.mentionedittext.manager.ListenerManager;
import com.bobomee.android.mentionedittext.manager.RangeListenerManager;
import com.bobomee.android.mentionedittext.model.Range;
import com.bobomee.android.mentionedittext.model.TagRange;
import com.bobomee.android.mentionedittext.model.UserRange;
import com.bobomee.android.mentionedittext.span.AtSpan;
import com.bobomee.android.mentionedittext.span.TagSpan;
import com.bobomee.android.mentionedittext.util.HackInputConnection;
import java.util.ArrayList;
import java.util.Collections;

/**
 * MentionEditText adds some useful features for mention string(@xxxx), such as highlight,
 * intelligent deletion, intelligent selection and '@' input detection, etc.
 *
 * @author Andy
 */
public class MentionEditText extends AppCompatEditText {
  private Runnable mAction;

  private int mMentionTextColor;
  private int mTagTextColor;
  private boolean mIsSelected;

  private Range mLastSelectedRange;
  private ArrayList<Range> mRangeArrayList;

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
    HackInputConnection hackInputConnection =
        new HackInputConnection(super.onCreateInputConnection(outAttrs), true, this);
    mRangeListenerManager = hackInputConnection.getRangeListenerManager();
    return hackInputConnection;
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
    if (mLastSelectedRange != null && mLastSelectedRange.isEqual(selStart, selEnd)) {
      return;
    }

    //if user cancel a selection of mention string, reset the state of 'mIsSelected'
    if (null != mRangeListenerManager) {
      Range closestRange = mRangeListenerManager.getRangeOfClosestMentionString(selStart, selEnd);
      if (closestRange != null && closestRange.getTo() == selEnd) {
        mIsSelected = false;
      }

      Range nearbyRange = mRangeListenerManager.getRangeOfNearbyMentionString(selStart, selEnd);
      //if there is no mention string nearby the cursor, just skip
      if (nearbyRange == null) {
        return;
      }

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

  /**
   * 插入mention string
   * 在调用该方法前，请先插入一个字符（如'@'），之后插入的name将会和该字符组成一个整体
   *
   * @param uid 用户id
   * @param name 用户名字
   */
  public void appendUser(String uid, String name) {
    Editable editable = getText();
    int start = getSelectionStart();
    int end = start + name.length();
    editable.insert(start, name);
    Range range = new UserRange(uid, name, start - 1, end);
    editable.setSpan(new AtSpan(getContext(), range, this), start - 1, end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    mRangeArrayList.add(range);
  }

  /**
   * 插入mention string
   * 在调用该方法前，不用先插入一个字符（如'@'），会在name之前插入后，组成一个整体一起添加
   *
   * @param uid 用户id
   * @param name 用户名字
   */
  public void mentionUser(String uid, String name) {
    Editable editable = getText();
    int start = getSelectionStart();
    name = mListenerManager.getMentionChar() + name;
    int end = start + name.length();
    editable.insert(start, name);
    Range range = new UserRange(uid, name, start, end);
    editable.setSpan(new AtSpan(getContext(), range, this), start, end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    mRangeArrayList.add(range);
  }

  public void appendTag(String tagId, String tagLabel) {
    Editable editable = getText();
    int start = getSelectionStart();
    int end = start + tagLabel.length() + 1;
    editable.insert(start, tagLabel + mListenerManager.getTagChar());
    Range range = new TagRange(tagId, tagLabel, start - 1, end);
    editable.setSpan(new TagSpan(getContext(), range, this), start - 1, end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    mRangeArrayList.add(range);
  }

  public void mentionTag (String tagId, String tagLabel) {
    Editable editable = getText();
    tagLabel = mListenerManager.getTagChar() + tagLabel;
    int start = getSelectionStart();
    int end = start + tagLabel.length() + 1;
    editable.insert(start, tagLabel + mListenerManager.getTagChar());
    Range range = new TagRange(tagId, tagLabel, start, end);
    editable.setSpan(new TagSpan(getContext(), range, this), start, end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    mRangeArrayList.add(range);
  }

  /**
   * 将所有mention string以指定格式输出
   *
   * @return 以指定格式输出的字符串
   */
  public String convertMetionString() {
    String text = getText().toString();
    if (mRangeArrayList.isEmpty()) {
      return text;
    }

    StringBuilder builder = new StringBuilder("");
    int lastRangeTo = 0;
    Collections.sort(mRangeArrayList);
    String newChar = "";
    for (Range range : mRangeArrayList) {
      switch (range.getType()) {
        case Range.TYPE_USER:
          newChar = String.format(mListenerManager.getMentionTextFormat(), range.getLable(),
              range.getId());
          break;
        case Range.TYPE_TAG:
          newChar =
              String.format(mListenerManager.getTagTextFormat(), range.getLable(), range.getId());
          break;
      }

      builder.append(text.substring(lastRangeTo, range.getFrom()));
      builder.append(newChar);
      lastRangeTo = range.getTo();
    }

    mRangeArrayList.clear();
    return builder.toString();
  }

  public void clear() {
    mRangeArrayList.clear();
    setText("");
  }

  private void init() {
    mRangeArrayList = new ArrayList<>();
    mMentionTextColor = Color.RED;
    mTagTextColor = Color.BLUE;
    //disable suggestion
    setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    addTextChangedListener(new MentionTextWatcher(this));
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////
  private RangeListenerManager mRangeListenerManager;

  private ListenerManager mListenerManager = ListenerManager.INSTANCE;

  public void addOnMentionInputListener(OnMentionInputListener mentionInputListener) {
    mListenerManager.addOnMentionInputListener(mentionInputListener);
  }

  /**
   * set highlight color of mention string
   *
   * @param color value from 'getResources().getColor()' or 'Color.parseColor()' etc.
   */
  public void setMentionTextColor(int color) {
    mMentionTextColor = color;
  }

  public int getMentionTextColor() {
    return mMentionTextColor;
  }

  public int getTagTextColor() {
    return mTagTextColor;
  }

  public void setTagTextColor(int tagTextColor) {
    mTagTextColor = tagTextColor;
  }

  @Override public boolean isSelected() {
    return mIsSelected;
  }

  @Override public void setSelected(boolean selected) {
    mIsSelected = selected;
  }

  public void setLastSelectedRange(Range lastSelectedRange) {
    mLastSelectedRange = lastSelectedRange;
  }

  public ArrayList<Range> getRangeArrayList() {
    return mRangeArrayList;
  }

  public void setMentionChar(char mentionChar) {
    mListenerManager.setMentionChar(mentionChar);
  }

  public char getMentionChar() {
    return mListenerManager.getMentionChar();
  }

  public void setMentionTextFormat(String format) {
    mListenerManager.setMentionTextFormat(format);
  }

  public void setTagChar(char tagChar) {
    mListenerManager.setTagChar(tagChar);
  }

  public char getTagChar() {
    return mListenerManager.getTagChar();
  }

  public void setTagTextFormat(String tagTextFormat) {
    mListenerManager.setTagTextFormat(tagTextFormat);
  }
}
