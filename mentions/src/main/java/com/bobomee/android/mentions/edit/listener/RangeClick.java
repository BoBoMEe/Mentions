package com.bobomee.android.mentions.edit.listener;

import android.view.View;
import com.bobomee.android.mentions.model.Range;
import com.bobomee.android.mentions.listener.ListenerImpl;
import java.util.List;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */
public class RangeClick extends ListenerImpl<SpanClickListener> {

  public void click(View widget,Range mRange) {
    if (hasListener()) {
      List<SpanClickListener> from = from();
      for (SpanClickListener spanClickListener : from) {
        if (null != spanClickListener) {
          spanClickListener.click(widget,mRange);
        }
      }
    }
  }
}
