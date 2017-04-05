package com.bobomee.android.mentions.text;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.util.AttributeSet;
import com.bobomee.android.mentions.listener.manager.ListenerManager;
import com.bobomee.android.mentions.text.listener.SpanClickListener;
import com.bobomee.android.mentions.text.util.SpanConvertUtil;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */
public class MentionTextView extends TextViewFixTouchConsume {
  public MentionTextView(Context context) {
    this(context, null);
  }

  public MentionTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MentionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    onInitialize();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MentionTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    onInitialize();
  }

  @Override public void setText(CharSequence text, BufferType type) {
    SpannableString weiBoText = mSpanConvertUtil.getWeiBoText(text.toString());
    setMovementMethod(TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
    super.setText(weiBoText, type);
  }

  public void clear() {
    setText("");
    mSpanConvertUtil.clear();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  private SpanConvertUtil mSpanConvertUtil;
  private int mMentionTextColor;
  private int mTagTextColor;
  private int mUrlTextColor;

  private void onInitialize() {
    mSpanConvertUtil = new SpanConvertUtil(this);
    mMentionTextColor = Color.RED;
    mTagTextColor = Color.BLUE;
    mUrlTextColor = Color.GRAY;
  }

  public int getUrlTextColor() {
    return mUrlTextColor;
  }

  public int getMentionTextColor() {
    return mMentionTextColor;
  }

  public int getTagTextColor() {
    return mTagTextColor;
  }

  public void setUrlTextColor(int urlTextColor) {
    mUrlTextColor = urlTextColor;
  }

  public void setMentionTextColor(int mentionTextColor) {
    mMentionTextColor = mentionTextColor;
  }

  public void setTagTextColor(int tagTextColor) {
    mTagTextColor = tagTextColor;
  }

  private ListenerManager mListenerManager = ListenerManager.INSTANCE;

  public void addSpanClickListener(SpanClickListener spanClickListener) {
    mListenerManager.addSpanClickListener(spanClickListener);
  }
}
