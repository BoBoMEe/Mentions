package com.bobomee.android.mentionedittextdemo.text.parser;

import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import com.bobomee.android.common.util.ToastUtil;
import com.bobomee.android.mentions.text.listener.ParserConverter;
import java.lang.reflect.Field;
import org.xml.sax.XMLReader;

/**
 * Project ID：
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/9 汪波 first commit
 */
public class TagParser implements ParserConverter {

  public TagParser() {
  }

  @Override public Spanned convert(CharSequence source) {
    if (TextUtils.isEmpty(source)) return new SpannableString("");
    return Html.fromHtml(source.toString(), null, new HtmlTagHandler());
  }

  class HtmlTagHandler implements Html.TagHandler {

    class MyFont{
      public String mId;

      public MyFont( String id) {
        mId = id;
      }

      @Override public String toString() {
        return "MyFont{" +
            ", mId='" + mId + '\'' +
            '}';
      }
    }
    @Override
    public void handleTag(boolean opening, String tag, Editable output,
        XMLReader xmlReader) {
      if (tag.toLowerCase().equals("tag")) {
        if (opening) {
          startMyFont(tag, output, xmlReader);
        } else {
          endMyFont(tag, output, xmlReader);
        }
      }
    }
    private  Object getLast(Spanned text, Class kind) {
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
    private void endMyFont(String tag, Editable text,
        XMLReader xmlReader) {
      //myfont标签不能裸着，即必须有html等标签包裹，或者前面有其他内容，否则字体大小不能起作用
      //即getlast变成从后面取，最后的内容的范围是0到文本全长度
      int  len = text.length();
      Object obj = getLast(text, MyFont.class);
      final int where = text.getSpanStart(obj);
      text.removeSpan(obj);
      Log.e("AAA", "where:"+where+",len:"+len);
      if (where != len) {
        final MyFont f = (MyFont) obj;

        if (!TextUtils.isEmpty(f.mId)){
          text.setSpan(new ClickableSpan() {
            @Override public void onClick(View widget) {
              ToastUtil.show(widget.getContext(),f.toString());
            }
          }, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

      }
    }
    private void startMyFont(String tag, Editable output,
        XMLReader xmlReader) {
      try {
        Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
        elementField.setAccessible(true);
        Object element = elementField.get(xmlReader);
        Field attsField = element.getClass().getDeclaredField("theAtts");
        attsField.setAccessible(true);
        Object atts = attsField.get(element);
        Field dataField = atts.getClass().getDeclaredField("data");
        dataField.setAccessible(true);
        String[] data = (String[]) dataField.get(atts);
        Field lengthField = atts.getClass().getDeclaredField("length");
        lengthField.setAccessible(true);
        int len = (Integer) lengthField.get(atts);
        String id = null;
        for (int i = 0; i < len; i++) {
          if ("id".equals(data[i * 5 + 1])) {
            id = data[i * 5 + 4];
          }
        }
        output.setSpan(new MyFont(id), output.length(), output.length(), Spannable.SPAN_MARK_MARK);
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }
}
