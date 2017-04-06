package com.bobomee.android.mentions.text.span;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import com.bobomee.android.mentions.ConfigFactory;
import com.bobomee.android.mentions.listener.manager.ListenerManager;
import com.bobomee.android.mentions.model.Range;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/4 汪波 first commit
 */
public class UrlSpan extends ClickableSpan {
  private final Range mUrlRange;
  private final ListenerManager mListenerManager = ListenerManager.INSTANCE;

  public UrlSpan(Range range) {
    this.mUrlRange = range;
  }

  @Override public void updateDrawState(TextPaint ds) {
    ds.setColor(ConfigFactory.INSTANCE.config().getUrlTextColor());
    ds.setUnderlineText(true); //去掉下划线
  }

  @Override public void onClick(View widget) {
    mListenerManager.notifySpanClickListener(widget, mUrlRange);
  }
}
