package com.bobomee.android.mentions.listener.manager;

import android.view.View;
import com.bobomee.android.mentions.edit.listener.OnMentionInput;
import com.bobomee.android.mentions.edit.listener.OnMentionInputListener;
import com.bobomee.android.mentions.model.Range;
import com.bobomee.android.mentions.text.listener.SpanClick;
import com.bobomee.android.mentions.text.listener.SpanClickListener;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/2 汪波 first commit
 */
public enum ListenerManager {

  INSTANCE;

  private OnMentionInput mOnMentionInput = new OnMentionInput();

  public void addOnMentionInputListener(OnMentionInputListener mentionInputListener) {
    mOnMentionInput.addListener(mentionInputListener);
  }

  public void notifyMentionCharacterInput(char c) {
    mOnMentionInput.onMentionCharacterInput(c);
  }

  private SpanClick mSpanClick = new SpanClick();

  public void addSpanClickListener(SpanClickListener spanClickListener) {
    mSpanClick.addListener(spanClickListener);
  }

  public void notifySpanClickListener(View view, Range range) {
    mSpanClick.click(view, range);
  }

  public OnMentionInput getOnMentionInput() {
    return mOnMentionInput;
  }

  public SpanClick getSpanClick() {
    return mSpanClick;
  }
}
