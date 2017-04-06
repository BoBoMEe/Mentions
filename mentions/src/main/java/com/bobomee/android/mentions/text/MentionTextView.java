package com.bobomee.android.mentions.text;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.util.AttributeSet;
import com.bobomee.android.mentions.listener.manager.ListenerManager;
import com.bobomee.android.mentions.text.listener.SpanClick;
import com.bobomee.android.mentions.text.listener.SpanClickListener;
import com.bobomee.android.mentions.text.util.SpanConvertUtil;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
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
    onInitialize();
    SpannableString weiBoText = mSpanConvertUtil.getWeiBoText(text.toString());
    setMovementMethod(TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());

    SpannableString wrapper = wrapper(weiBoText);

    super.setText(wrapper, type);
  }

  protected SpannableString wrapper(SpannableString weiBoText) {

    return weiBoText;
  }

  public void clear() {
    setText("");
    mSpanConvertUtil.clear();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  private SpanConvertUtil mSpanConvertUtil;

  private void onInitialize() {
    if (null == mSpanConvertUtil) mSpanConvertUtil = new SpanConvertUtil();
  }

  private ListenerManager mListenerManager = ListenerManager.INSTANCE;

  public void addSpanClickListener(SpanClickListener spanClickListener) {
    mListenerManager.addSpanClickListener(spanClickListener);
  }

  public void setSpanClickListener(SpanClickListener spanClickListener){
    SpanClick spanClick = mListenerManager.getSpanClick();
    if (!spanClick.hasListener()){
      addSpanClickListener(spanClickListener);
    }
  }
}
