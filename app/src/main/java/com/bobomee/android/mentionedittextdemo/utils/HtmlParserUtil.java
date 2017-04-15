package com.bobomee.android.mentionedittextdemo.utils;

import android.text.Editable;
import java.lang.reflect.Field;
import java.util.HashMap;
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
public class HtmlParserUtil {

  public static Map<String, String> parseStart(String tag, Editable output, XMLReader xmlReader) {
    Map<String, String> attibute = new HashMap<>();
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
        attibute.put(data[i * 5 + 1], data[i * 5 + 4]);
      }
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }

    return attibute;
  }
}
