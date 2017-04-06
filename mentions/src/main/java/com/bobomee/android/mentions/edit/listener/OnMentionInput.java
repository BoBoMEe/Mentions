package com.bobomee.android.mentions.edit.listener;

import com.bobomee.android.mentions.listener.ListenerImpl;
import java.util.List;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
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
