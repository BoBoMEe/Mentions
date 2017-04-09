package com.bobomee.android.mentions.text.listener;

import android.text.Spanned;

/**
 * Project ID：
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/9 汪波 first commit
 */
public interface ParserConverter {

  Spanned convert(CharSequence source);
}
