package com.bobomee.android.mentions.text;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.bobomee.android.mentions.listener.manager.ListenerManager;
import com.bobomee.android.mentions.model.BaseModel;
import com.bobomee.android.mentions.model.TagRange;
import com.bobomee.android.mentions.model.UrlRange;
import com.bobomee.android.mentions.model.UserRange;
import com.bobomee.android.mentions.text.listener.SpanClickListener;
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
 * @since 2017/4/3 汪波 first commit
 */
public class MentionTextView extends TextViewFixTouchConsume {
  public MentionTextView(Context context) {
    this(context,null);
  }

  public MentionTextView(Context context, AttributeSet attrs) {
    this(context, attrs,0);
  }

  public MentionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    onInitialize();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  public MentionTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    onInitialize();
  }
  @Override public void setText(CharSequence text, BufferType type) {
    SpannableString weiBoText = getWeiBoText(getContext(), text.toString());
    setMovementMethod(TextViewFixTouchConsume.LocalLinkMovementMethod.getInstance());
    super.setText(weiBoText, type);
  }

  public void clear() {
    setText("");
    mRangeSet.clear();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  private static final Pattern URL_PATTERN = Pattern.compile(
      "((http|https|ftp|ftps):\\/\\/)?([a-zA-Z0-9-]+\\.){1,5}(com|cn|net|org|hk|tw)((\\/(\\w|-)+(\\.([a-zA-Z]+))?)+)?(\\/)?(\\??([\\.%:a-zA-Z0-9_-]+=[#\\.%:a-zA-Z0-9_-]+(&amp;)?)+)?");
  private static final Pattern AT_PATTERN =
      Pattern.compile("(\\(@[\\u4e00-\\u9fa5\\w\\-\\$]+[-,.?:;'\"!]+[a-z]+[=\\-]+[\\w\\-\\$]+\\))");
  private static final Pattern TAG_PATTERN = Pattern.compile("#([^\\#|.]+)#");

  private Set<com.bobomee.android.mentions.model.Range> mRangeSet;
  private int mMentionTextColor;
  private int mTagTextColor;
  private int mUrlTextColor;

  private void onInitialize() {
    mRangeSet = new HashSet<>();
    mMentionTextColor = Color.RED;
    mTagTextColor = Color.BLUE;
    mUrlTextColor = Color.GRAY;
  }

  public int getUrlTextColor() {
    return mUrlTextColor;
  }

  public void setUrlTextColor(int urlTextColor) {
    mUrlTextColor = urlTextColor;
  }

  public int getMentionTextColor() {
    return mMentionTextColor;
  }

  public int getTagTextColor() {
    return mTagTextColor;
  }

  public void setMentionTextColor(int mentionTextColor) {
    mMentionTextColor = mentionTextColor;
  }

  public void setTagTextColor(int tagTextColor) {
    mTagTextColor = tagTextColor;
  }

  private void addRange(com.bobomee.android.mentions.model.Range range) {
    String lable = range.getLable();
    String id = range.getId();
    if (!TextUtils.isEmpty(lable) && !TextUtils.isEmpty(id)) {
      mRangeSet.add(range);
    }
  }

  private SpannableString getWeiBoText(Context context, String text) {

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

      com.bobomee.android.mentions.model.Range range = new UserRange(id, name, start, start + name.length());
      addRange(range);
    }

    SpannableString spannable = new SpannableString(text);

    for (com.bobomee.android.mentions.model.Range range : mRangeSet) {
      if (range.getType() == BaseModel.TYPE_USER) {
        spannable.setSpan(new AtSpan(this, range), range.getFrom(), range.getTo(),
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
      }
    }

    Matcher tag = TAG_PATTERN.matcher(spannable);
    while (tag.find()) {
      String tagNameMatch = tag.group();
      int start = tag.start();
      com.bobomee.android.mentions.model.Range range = new TagRange(tagNameMatch, start, start + tagNameMatch.length());
      spannable.setSpan(new TagSpan(this, range), start, start + tagNameMatch.length(),
          Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    Matcher url = URL_PATTERN.matcher(spannable);
    while (url.find()) {
      String urlString = url.group();
      int start = url.start();
      com.bobomee.android.mentions.model.Range range = new UrlRange(urlString, start, start + urlString.length());
      spannable.setSpan(new UrlSpan(this, range), start, start + urlString.length(),
          Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    return spannable;
  }

  private ListenerManager mListenerManager = ListenerManager.INSTANCE;
  public void  addSpanClickListener(SpanClickListener spanClickListener){
    mListenerManager.addSpanClickListener(spanClickListener);
  }

}
