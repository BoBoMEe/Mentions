package com.bobomee.android.mentions.text;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import com.bobomee.android.mentions.text.listener.ParserConverter;

/**
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/9 汪波 first commit
 */
public class MentionTextView extends TextView {
  public MentionTextView(Context context) {
    this(context, null);
  }

  public MentionTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public MentionTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public MentionTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override public void setText(CharSequence text, BufferType type) {
    if (!TextUtils.isEmpty(text) && null != mParserConverter) {
      text = mParserConverter.convert(text);
    }

    text = wrapper(text);
    super.setText(text, type);
    setMovementMethod(new LinkMovementMethod());
  }

  public CharSequence wrapper(CharSequence text) {
    return text;
  }

  private ParserConverter mParserConverter;

  public void setParserConverter(ParserConverter parserConverter) {
    mParserConverter = parserConverter;
  }
}
