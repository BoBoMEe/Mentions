package com.bobomee.android.mentionedittextdemo.text.parser;

import android.text.SpannableString;
import android.text.Spanned;
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
public class NoParserConverter implements ParserConverter {

  @Override public Spanned convert(CharSequence source) {
    return new SpannableString(source);
  }
}
