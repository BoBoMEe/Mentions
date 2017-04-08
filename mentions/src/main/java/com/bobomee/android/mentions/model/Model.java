package com.bobomee.android.mentions.model;

import java.io.Serializable;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/8 汪波 first commit
 */
public class Model implements Serializable{

  private final CharSequence mCharSequence;

  public Model(CharSequence charSequence) {
    mCharSequence = charSequence;
  }

  public CharSequence getCharSequence() {
    return mCharSequence;
  }
}
