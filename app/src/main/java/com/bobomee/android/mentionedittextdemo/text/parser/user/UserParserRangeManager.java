package com.bobomee.android.mentionedittextdemo.text.parser.user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project ID：
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/9 汪波 first commit
 */
public class UserParserRangeManager {

  private String mOriginalSource;
  private String mPatternSource;

  private Set<UserParserRange> mRanges;

  private static final Pattern pattern =
      Pattern.compile("(\\(@[\\u4e00-\\u9fa5\\w\\-\\$]+[-,.?:;'\"!]+[a-z]+[=\\-]+[\\w\\-\\$]+\\))");

  private void put(UserParserRange range) {
    ensureNonNull();
    mRanges.add(range);
  }

  public Iterator<UserParserRange> iterator() {
    ensureNonNull();
    return mRanges.iterator();
  }

  private void clear() {
    ensureNonNull();
    mRanges.clear();
  }

  public void ensureNonNull() {
    if (null == mRanges) mRanges = new HashSet<>();
  }

  public void setSource(String source) {

    mOriginalSource = source;
    int offset = 0;
    clear();
    Matcher at = pattern.matcher(source);
    while (at.find()) {
      String key = at.group(0);
      int start = at.start();
      UserParserRange range = new UserParserRange(start, at.end(), key);

      put(range);

      source = source.replace(key, range.getName());

      range.setStart(start - offset);

      offset += key.length() - range.getName().length();
    }

    mPatternSource = source;
  }

  public String getOriginalSource() {
    return mOriginalSource;
  }

  public String getPatternSource() {
    return mPatternSource;
  }
}
