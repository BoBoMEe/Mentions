package com.bobomee.android.mentionedittext.span;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;
import com.bobomee.android.mentionedittext.MentionEditText;
import com.bobomee.android.mentionedittext.Range;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/2 汪波 first commit
 */
public class AtSpan extends ClickableSpan {

  private final Context mContext;
  private Range mRange;
  private final int mMentionTextColor;

  public AtSpan(Context context, Range range, MentionEditText mentionEditText) {
    mContext = context;
    mRange = range;
    mMentionTextColor = mentionEditText.getMentionTextColor();
  }

  @Override public void updateDrawState(TextPaint ds) {
    ds.setColor(mMentionTextColor);
    ds.setUnderlineText(false); //去掉下划线
  }

  @Override public void onClick(View widget) {
    String name = mRange.getName();
    String id = mRange.getId();

    Toast.makeText(mContext, "name = " + name + ", id = " + id, Toast.LENGTH_SHORT).show();
  }
}
