package com.bobomee.android.mentions.edit.util;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * ArrowKeyMovementMethod does support selection of text but not the clicking of links.
 * LinkMovementMethod does support clicking of links but not the selection of text.
 * This class adds the link clicking to the ArrowKeyMovementMethod.
 * We basically take the LinkMovementMethod onTouchEvent code and remove the line
 *      Selection.removeSelection(buffer);
 * which deselects all text when no link was found.
 */
public class EnhancedMovementMethod extends ArrowKeyMovementMethod {

  private static EnhancedMovementMethod sInstance;

  public static MovementMethod getInstance() {
    if (sInstance == null) {
      sInstance = new EnhancedMovementMethod ();
    }
    return sInstance;
  }

  @Override
  public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
    int action = event.getAction();

    if (action == MotionEvent.ACTION_UP ||
        action == MotionEvent.ACTION_DOWN) {
      int x = (int) event.getX();
      int y = (int) event.getY();

      x -= widget.getTotalPaddingLeft();
      y -= widget.getTotalPaddingTop();

      x += widget.getScrollX();
      y += widget.getScrollY();

      Layout layout = widget.getLayout();
      int line = layout.getLineForVertical(y);
      int off = layout.getOffsetForHorizontal(line, x);

      ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

      if (link.length != 0) {
        if (action == MotionEvent.ACTION_UP) {
          link[0].onClick(widget);
        }
        else if (action == MotionEvent.ACTION_DOWN) {
          Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
        }

        return true;
      }
            /*else {
                Selection.removeSelection(buffer);
            }*/
    }

    return super.onTouchEvent(widget, buffer, event);
  }

}
