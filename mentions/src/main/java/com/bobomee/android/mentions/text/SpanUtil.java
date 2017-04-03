package com.bobomee.android.mentions.text;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;
import com.bobomee.android.mentions.model.BaseModel;
import com.bobomee.android.mentions.model.Range;
import com.bobomee.android.mentions.model.UserRange;
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
 * @since 2017/4/3 汪波 first commit
 */
public enum SpanUtil {

  INSTANCE;

  private static final Pattern URL_PATTERN = Pattern.compile(
      "((http|https|ftp|ftps):\\/\\/)?([a-zA-Z0-9-]+\\.){1,5}(com|cn|net|org|hk|tw)((\\/(\\w|-)+(\\.([a-zA-Z]+))?)+)?(\\/)?(\\??([\\.%:a-zA-Z0-9_-]+=[#\\.%:a-zA-Z0-9_-]+(&amp;)?)+)?");
  private static final Pattern AT_PATTERN =
      Pattern.compile("(\\(@[\\u4e00-\\u9fa5\\w\\-\\$]+[-,.?:;'\"!]+[a-z]+[=\\-]+[\\w\\-\\$]+\\))");
  private static final Pattern TAG_PATTERN = Pattern.compile("#([^\\#|.]+)#");

  private Set<Range> mRangeSet;

  private int mMentionTextColor;
  private int mTagTextColor;

  SpanUtil() {
    mRangeSet = new HashSet<>();
    mMentionTextColor = Color.RED;
    mTagTextColor = Color.BLUE;
  }

  public int getMentionTextColor() {
    return mMentionTextColor;
  }

  public void setMentionTextColor(int mentionTextColor) {
    mMentionTextColor = mentionTextColor;
  }

  public int getTagTextColor() {
    return mTagTextColor;
  }

  public void setTagTextColor(int tagTextColor) {
    mTagTextColor = tagTextColor;
  }

  public void addRange(Range range) {
    String lable = range.getLable();
    String id = range.getId();
    if (!TextUtils.isEmpty(lable) && !TextUtils.isEmpty(id)) {
      mRangeSet.add(range);
    }
  }

  /**
   * 将微博正文中的 @ 和 # ，url标识出
   *
   * @param text 待处理的文本
   * @return 处理后的文本
   */
  public SpannableString getWeiBoText(Context context, String text) {

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

      Range range = new UserRange(id, name, start, start + name.length());
      addRange(range);
    }

    SpannableString spannable = new SpannableString(text);

    for (Range range : mRangeSet) {
      if (range.getType() == BaseModel.TYPE_USER) {
        spannable.setSpan(new MyAtSpan(context, range), range.getFrom(), range.getTo(),
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      }
    }

    ////2. tag
    //Matcher tag = TAG_PATTERN.matcher(spannable);
    //while (tag.find()) {
    //  String key = at.group(0);
    //
    //  String name = "", id = "";
    //
    //  int indexOf = key.indexOf(",id=");
    //  if (indexOf > 0) {
    //    name = key.substring(1, indexOf);
    //    id = key.substring(indexOf + 4, key.length() - 1);
    //  }
    //
    //  text = text.replace(key, name);
    //
    //  int start = text.indexOf(name);
    //
    //  Range range = new TagRange(id, name, start, start + name.length());
    //  addRange(range);
    //}
    //
    //for (Range range : mRangeSet) {
    //  if (range.getType() == BaseModel.TYPE_TAG) {
    //    spannable.setSpan(new MyTagSpan(context, range), range.getFrom(), range.getTo(),
    //        Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    //  }
    //}

    Matcher tag = TAG_PATTERN.matcher(spannable);
    while (tag.find()) {
      String tagNameMatch = tag.group();
      int start = tag.start();
      spannable.setSpan(new MyTagSpan(context, tagNameMatch), start, start + tagNameMatch.length(),
          Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    Matcher url = URL_PATTERN.matcher(spannable);
    while (url.find()) {
      String urlString = url.group();
      int start = url.start();
      spannable.setSpan(new MyURLSpan(context, urlString), start, start + urlString.length(),
          Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    return spannable;
  }

  /**
   * 用于weibo text中的连接跳转
   */
  private class MyURLSpan extends ClickableSpan {
    private String mUrl;
    private Context context;

    MyURLSpan(Context ctx, String url) {
      context = ctx;
      mUrl = url;
    }

    @Override public void updateDrawState(TextPaint ds) {
      ds.setColor(Color.parseColor("#f44336"));
    }

    @Override public void onClick(View widget) {
      Intent intent = new Intent();
      intent.setAction("android.intent.action.VIEW");
      Uri content_url = Uri.parse(mUrl);
      intent.setData(content_url);
      context.startActivity(intent);
    }
  }

  /**
   * 用于转发 weibo 中 @名字的点击跳转
   */
  private class MyAtSpan extends ClickableSpan {
    private Range mRange;
    private Context context;

    MyAtSpan(Context ctx, Range range) {
      context = ctx;
      mRange = range;
    }

    @Override public void updateDrawState(TextPaint ds) {
      ds.setColor(getMentionTextColor());
      ds.setUnderlineText(false); //去掉下划线
    }

    @Override public void onClick(View widget) {

      Toast.makeText(context, "name ->" + mRange.getLable() + ",id->" + mRange.getId(),
          Toast.LENGTH_SHORT).show();
    }
  }

  /**
   * 用于转发 weibo 中 Tag 的点击跳转
   */
  private class MyTagSpan extends ClickableSpan {
    private Context context;
    //private Range mRange;
    //
    //MyTagSpan(Context ctx, Range range) {
    //  context = ctx;
    //  mRange = range;
    //}
    private String mTag;
    MyTagSpan(Context ctx, String tag) {
      context = ctx;
      mTag = tag;
    }

    @Override public void updateDrawState(TextPaint ds) {
      ds.setColor(getTagTextColor());
      ds.setUnderlineText(false); //去掉下划线
    }

    @Override public void onClick(View widget) {
      //Toast.makeText(context, "tag ->" + mRange.getLable() + ",id->" + mRange.getId(),
      //    Toast.LENGTH_SHORT).show();
      Toast.makeText(context, "tag ->"+mTag, Toast.LENGTH_SHORT).show();
    }
  }

  public void clear() {
    mRangeSet.clear();
  }
}
