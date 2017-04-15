package com.bobomee.android.mentionedittextdemo.text.parser;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import com.bobomee.android.mentionedittextdemo.utils.LinkUtil;
import com.bobomee.android.mentions.text.listener.ParserConverter;

/**
 * Project ID：
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/9 汪波 first commit
 */
public class Parser implements ParserConverter {

  public Parser() {
  }

  @Override public Spanned convert(CharSequence source) {
    if (TextUtils.isEmpty(source)) return new SpannableString("");
    String sourceString = source.toString();
    sourceString = LinkUtil.replaceUrl(sourceString);

    return Html.fromHtml(sourceString, null, new HtmlTagHandler());
  }
}
