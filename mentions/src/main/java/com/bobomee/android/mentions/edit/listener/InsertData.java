package com.bobomee.android.mentions.edit.listener;

import com.bobomee.android.mentions.model.FormatRange;

/**
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/8 汪波 first commit
 */
public interface InsertData {

  CharSequence charSequence();

  FormatRange.FormatData formatData();

  int color();
}
