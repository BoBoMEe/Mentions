package com.bobomee.android.mentionedittextdemo.text.parser;

import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;
import com.bobomee.android.mentions.text.listener.ParserConverter;
import java.lang.reflect.Field;
import java.util.HashMap;
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
    private static final String TAG_BLUE_FONT = "tag";

    private int startIndex = 0;
    private int stopIndex = 0;
    final HashMap<String, String> attributes = new HashMap<String, String>();

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
      processAttributes(xmlReader);

      if (tag.equalsIgnoreCase(TAG_BLUE_FONT)) {
        if (opening) {
          startFont(tag, output, xmlReader);
        } else {
          endFont(tag, output, xmlReader);
        }
      }
    }

    public void startFont(String tag, Editable output, XMLReader xmlReader) {
      startIndex = output.length();
    }

    public void endFont(String tag, Editable output, XMLReader xmlReader) {
      stopIndex = output.length();

      final String id = attributes.get("id");
      final String name = attributes.get("name");

      if (!TextUtils.isEmpty(id)) {
        output.setSpan(new ClickableSpan() {
          @Override public void onClick(View widget) {
            Toast.makeText(widget.getContext(), "id = " + id + ",name = " + name,
                Toast.LENGTH_SHORT).show();
          }
        }, startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      }
    }

    private void processAttributes(final XMLReader xmlReader) {
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

        for (int i = 0; i < len; i++) {
          attributes.put(data[i * 5 + 1], data[i * 5 + 4]);
        }
      } catch (Exception e) {

      }
    }
  }
}
