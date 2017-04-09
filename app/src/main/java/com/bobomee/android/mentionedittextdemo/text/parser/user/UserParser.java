package com.bobomee.android.mentionedittextdemo.text.parser.user;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import com.bobomee.android.common.util.ToastUtil;
import com.bobomee.android.mentions.text.listener.ParserConverter;
import java.util.Iterator;

/**
 * Project ID：
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/9 汪波 first commit
 */
public class UserParser implements ParserConverter {

  private static final String TAG = "UserParser";

  public UserParser() {
    mParserRangeManager = new UserParserRangeManager();
  }

  @Override public Spanned convert(CharSequence source) {

    mParserRangeManager.setSource(source.toString());

    String copySource = mParserRangeManager.getPatternSource();

    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(copySource);

    Iterator<UserParserRange> parserRangeIterator = mParserRangeManager.iterator();
    while (parserRangeIterator.hasNext()) {
      UserParserRange next = parserRangeIterator.next();
      int start = next.getNamePatternStart();
      int end = next.getNamePatternEnd();
      if (start >= 0 && end >= 0 && start<end) {
        spannableStringBuilder.setSpan(new Span(next), start, end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }

    return spannableStringBuilder;
  }

  private class Span extends ClickableSpan {

    private final UserParserRange parserRange;

    public Span(UserParserRange parserRange) {
      this.parserRange = parserRange;
    }

    @Override public void onClick(View widget) {
      ToastUtil.show(widget.getContext(),parserRange.toString());
    }
  }

  private UserParserRangeManager mParserRangeManager;
}
