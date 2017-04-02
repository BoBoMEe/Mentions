package com.bobomee.android.mentionedittext.listener;

import java.util.List;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/2 汪波 first commit
 */
public class OnMentionInput extends ListenerImpl<OnMentionInputListener> {

  public void onMentionCharacterInput(char charSequence) {
    if (hasListener()) {
      List<OnMentionInputListener> from = from();
      for (OnMentionInputListener onMentionInputListener : from) {
        if (null != onMentionInputListener) {
          onMentionInputListener.onMentionCharacterInput(charSequence);
        }
      }
    }
  }
}
