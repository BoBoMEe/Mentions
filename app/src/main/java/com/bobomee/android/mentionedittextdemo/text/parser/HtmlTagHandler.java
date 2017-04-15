package com.bobomee.android.mentionedittextdemo.text.parser;

import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.bobomee.android.mentionedittextdemo.utils.HtmlParserUtil;
import java.util.Map;
import org.xml.sax.XMLReader;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/15 汪波 first commit
 */
public class HtmlTagHandler implements Html.TagHandler {
  private static final String TAG = "tag";
  private static final String USER = "user";
  private static final String ID = "id";
  public static final String NAME = "name";

  @Override
  public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
    if (tag.toLowerCase().equals(TAG)||tag.toLowerCase().equals(USER)) {
      if (opening) {
        Map<String, String> map = HtmlParserUtil.parseStart(tag, output, xmlReader);
        String id = map.get(ID);
        String name = map.get(NAME);
        output.setSpan(new TagBean(name,id), output.length(), output.length(), Spannable.SPAN_MARK_MARK);
      } else {
        endTag(tag, output, xmlReader);
      }
    }
  }

  private Object getLast(Spanned text, Class kind) {
              /*
               * This knows that the last returned object from getSpans()
			         * will be the most recently added.
			         */
    Object[] objs = text.getSpans(0, text.length(), kind);

    if (objs.length == 0) {
      return null;
    } else {
      return objs[objs.length - 1];
    }
  }

  private void endTag(String tag, Editable text, XMLReader xmlReader) {
    //myfont标签不能裸着，即必须有html等标签包裹，或者前面有其他内容，否则字体大小不能起作用
    //即getlast变成从后面取，最后的内容的范围是0到文本全长度
    int len = text.length();
    Object obj = getLast(text, TagBean.class);
    int where = text.getSpanStart(obj);
    text.removeSpan(obj);
    Log.e("AAA", "where:" + where + ",len:" + len);
    if (where != len) {
      final TagBean t = (TagBean) obj;

      if (null != t) {
        text.setSpan(new ClickableSpan() {
          @Override public void onClick(View widget) {
            Toast.makeText(widget.getContext(), t.toString(), Toast.LENGTH_SHORT).show();
          }
        }, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }
  }
}