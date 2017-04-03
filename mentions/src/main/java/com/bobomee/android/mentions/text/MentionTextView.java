package com.bobomee.android.mentions.text;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.util.AttributeSet;

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
    super(context);
  }

  public MentionTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MentionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MentionTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override public void setText(CharSequence text, BufferType type) {
    SpannableString weiBoText = SpanUtil.INSTANCE.getWeiBoText(getContext(), text.toString());
    setMovementMethod(TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
    super.setText(weiBoText, type);
  }

  public void clear() {
    setText("");
    SpanUtil.INSTANCE.clear();
  }

  public void setMentionTextColor(int mentionTextColor) {
    SpanUtil.INSTANCE.setMentionTextColor(mentionTextColor);
  }

  public void setTagTextColor(int tagTextColor) {
    SpanUtil.INSTANCE.setTagTextColor(tagTextColor);
  }
}
