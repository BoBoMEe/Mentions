package com.bobomee.android.mentionedittext.listener;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/2 汪波 first commit
 */
/**
 * Listener for '@' character
 */
public interface OnMentionInputListener {
  /**
   * call when '@' character is inserted into EditText
   */
  void onMentionCharacterInput(char charSequence);
}
