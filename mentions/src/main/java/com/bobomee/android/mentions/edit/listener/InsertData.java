package com.bobomee.android.mentions.edit.listener;

import com.bobomee.android.mentions.model.Range;

/**
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/8 汪波 first commit
 */
public interface InsertData {

  CharSequence provideCharSequence();

  Range provideRange(int start,int end);

  int provideColor();
}
