package com.bobomee.android.mentions.edit.listener;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/2 汪波 first commit
 */
/**
 * Listener for '@' character
 */
public interface OnMentionInputListener {
  /**
   * call when '@' character is inserted into EditText
   * @param charSequence  mention char
   */
  void onMentionCharacterInput(char charSequence);
}
