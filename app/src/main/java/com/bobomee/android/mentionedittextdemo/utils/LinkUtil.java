package com.bobomee.android.mentionedittextdemo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/15 汪波 first commit
 */
public class LinkUtil {

  private static final Pattern URL_PATTERN = Pattern.compile(
      "((http|https|ftp|ftps):\\/\\/)?([a-zA-Z0-9-]+\\.){1,5}(com|cn|net|org|hk|tw)((\\/(\\w|-)+(\\.([a-zA-Z]+))?)+)?(\\/)?(\\??([\\.%:a-zA-Z0-9_-]+=[#\\.%:a-zA-Z0-9_-]+(&amp;)?)+)?");

  public static String replaceUrl(String source) {
    Matcher matcher = URL_PATTERN.matcher(source);
    if (matcher.find()) {
      String url = matcher.group();

      source = source.replace(url, "<a href=" + "\'" + url + "\'" + ">🔗网页链接</a>");
    }
    return source;
  }
}
