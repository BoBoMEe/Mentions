package com.bobomee.android.mentions.text.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import com.bobomee.android.mentions.ConfigFactory;
import com.bobomee.android.mentions.model.BaseModel;
import com.bobomee.android.mentions.model.Range;
import com.bobomee.android.mentions.model.TagRange;
import com.bobomee.android.mentions.model.UrlRange;
import com.bobomee.android.mentions.model.UserRange;
import com.bobomee.android.mentions.text.span.AtSpan;
import com.bobomee.android.mentions.text.span.TagSpan;
import com.bobomee.android.mentions.text.span.UrlSpan;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/5 汪波 first commit
 */
public class SpanConvertUtil {

  private static final Pattern URL_PATTERN = Pattern.compile(
      "((http|https|ftp|ftps):\\/\\/)?([a-zA-Z0-9-]+\\.){1,5}(com|cn|net|org|hk|tw)((\\/(\\w|-)+(\\.([a-zA-Z]+))?)+)?(\\/)?(\\??([\\.%:a-zA-Z0-9_-]+=[#\\.%:a-zA-Z0-9_-]+(&amp;)?)+)?");
  private static final Pattern AT_PATTERN =
      Pattern.compile("(\\(@[\\u4e00-\\u9fa5\\w\\-\\$]+[-,.?:;'\"!]+[a-z]+[=\\-]+[\\w\\-\\$]+\\))");
  private static final Pattern TAG_PATTERN = Pattern.compile("#([^\\#|.]+)#");

  private Set<Range> mRangeSet;

  public SpanConvertUtil() {
    mRangeSet = new HashSet<>();
  }

  private void addRange(com.bobomee.android.mentions.model.Range range) {
    String lable = range.getLable();
    String id = range.getId();
    if (!TextUtils.isEmpty(lable) && !TextUtils.isEmpty(id)) {
      mRangeSet.add(range);
    }
  }

  public SpannableString getWeiBoText(String text) {

    if (TextUtils.isEmpty(text)) {
      return new SpannableString("");
    }

    // 1, 处理@昵称
    Matcher at = AT_PATTERN.matcher(text);
    while (at.find()) {
      String key = at.group(0);

      String name = "", id = "";

      int indexOf = key.indexOf(",id=");
      if (indexOf > 0) {
        name = key.substring(1, indexOf);
        id = key.substring(indexOf + 4, key.length() - 1);
      }

      text = text.replace(key, name);

      int start = text.indexOf(name);

      com.bobomee.android.mentions.model.Range range =
          new UserRange(id, name, start, start + name.length());
      addRange(range);
    }

    SpannableString spannable = new SpannableString(text);
    if (getConfig().isSupportAt()) {

      for (com.bobomee.android.mentions.model.Range range : mRangeSet) {
        if (range.getType() == BaseModel.TYPE_USER) {
          spannable.setSpan(new AtSpan(range), range.getFrom(), range.getTo(),
              Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
      }
    }

    if (getConfig().isSupportTag()) {

      Matcher tag = TAG_PATTERN.matcher(spannable);
      while (tag.find()) {
        String tagNameMatch = tag.group();
        int start = tag.start();
        com.bobomee.android.mentions.model.Range range =
            new TagRange(tagNameMatch, start, start + tagNameMatch.length());
        spannable.setSpan(new TagSpan(range), start, start + tagNameMatch.length(),
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      }
    }

    if (getConfig().isSupportLink()) {
      Matcher url = URL_PATTERN.matcher(spannable);
      while (url.find()) {
        String urlString = url.group();
        int start = url.start();
        com.bobomee.android.mentions.model.Range range =
            new UrlRange(urlString, start, start + urlString.length());
        spannable.setSpan(new UrlSpan(range), start, start + urlString.length(),
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      }
    }

    return spannable;
  }

  public void clear() {
    mRangeSet.clear();
  }

  private ConfigFactory.Config getConfig() {
    return ConfigFactory.INSTANCE.config();
  }
}
