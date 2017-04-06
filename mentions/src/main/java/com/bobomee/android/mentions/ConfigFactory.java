package com.bobomee.android.mentions;

import android.graphics.Color;

import static com.bobomee.android.mentions.ConfigFactory.Config.sConfig;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @since 2017/4/5 汪波 first commit
 */

public enum ConfigFactory {

  INSTANCE;

  private Config mConfig = sConfig();

  public Config config() {
    return mConfig;
  }

  public void config(Config config) {
    this.mConfig = config;
  }

  public void DEFAULT() {
    config(sConfig());
  }

  public static class Config {

    static Config sConfig() {
      return new Config.Builder().build();
    }

    private boolean supportAt = true;
    private boolean supportTag = true;
    private boolean supportLink = true;
    private char mentionChar = '@';
    private char mTagChar = '#';
    private String mMentionTextFormat = "(@%s,id=%s)";
    private String mTagTextFormat = "#%s#";
    private int mMentionTextColor = Color.RED;
    private int mTagTextColor = Color.BLUE;
    private int mUrlTextColor = Color.GRAY;
    private int mMentionEditColor = Color.RED;
    private int mTagEditColor = Color.BLUE;

    private Config(Builder builder) {
      setSupportAt(builder.supportAt);
      setSupportTag(builder.supportTag);
      setSupportLink(builder.supportLink);
      setMentionChar(builder.mentionChar);
      setTagChar(builder.mTagChar);
      setMentionTextFormat(builder.mMentionTextFormat);
      setTagTextFormat(builder.mTagTextFormat);
      setMentionTextColor(builder.mMentionTextColor);
      setTagTextColor(builder.mTagTextColor);
      setUrlTextColor(builder.mUrlTextColor);
      setMentionEditColor(builder.mMentionEditColor);
      setTagEditColor(builder.mTagEditColor);
    }

    public boolean isSupportAt() {
      return supportAt;
    }

    public void setSupportAt(boolean supportAt) {
      this.supportAt = supportAt;
    }

    public boolean isSupportTag() {
      return supportTag;
    }

    public void setSupportTag(boolean supportTag) {
      this.supportTag = supportTag;
    }

    public boolean isSupportLink() {
      return supportLink;
    }

    public void setSupportLink(boolean supportLink) {
      this.supportLink = supportLink;
    }

    public char getMentionChar() {
      return mentionChar;
    }

    public void setMentionChar(char mentionChar) {
      this.mentionChar = mentionChar;
    }

    public char getTagChar() {
      return mTagChar;
    }

    public void setTagChar(char tagChar) {
      mTagChar = tagChar;
    }

    public String getMentionTextFormat() {
      return mMentionTextFormat;
    }

    public void setMentionTextFormat(String mentionTextFormat) {
      mMentionTextFormat = mentionTextFormat;
    }

    public String getTagTextFormat() {
      return mTagTextFormat;
    }

    public void setTagTextFormat(String tagTextFormat) {
      mTagTextFormat = tagTextFormat;
    }

    public int getUrlTextColor() {
      return mUrlTextColor;
    }

    public int getMentionTextColor() {
      return mMentionTextColor;
    }

    public int getTagTextColor() {
      return mTagTextColor;
    }

    public void setUrlTextColor(int urlTextColor) {
      mUrlTextColor = urlTextColor;
    }

    public void setMentionTextColor(int mentionTextColor) {
      mMentionTextColor = mentionTextColor;
    }

    public void setTagTextColor(int tagTextColor) {
      mTagTextColor = tagTextColor;
    }

    public int getMentionEditColor() {
      return mMentionEditColor;
    }

    public void setMentionEditColor(int mentionEditColor) {
      mMentionEditColor = mentionEditColor;
    }

    public int getTagEditColor() {
      return mTagEditColor;
    }

    public void setTagEditColor(int tagEditColor) {
      mTagEditColor = tagEditColor;
    }

    public static final class Builder {
      private boolean supportAt = true;
      private boolean supportTag = true;
      private boolean supportLink = true;
      private char mentionChar = '@';
      private char mTagChar = '#';
      private String mMentionTextFormat = "(@%s,id=%s)";
      private String mTagTextFormat = "#%s#";
      private int mMentionTextColor = Color.RED;
      private int mTagTextColor = Color.BLUE;
      private int mUrlTextColor = Color.GRAY;
      private int mMentionEditColor = Color.RED;
      private int mTagEditColor = Color.BLUE;

      public Builder() {
      }

      public Builder supportAt(boolean val) {
        supportAt = val;
        return this;
      }

      public Builder supportTag(boolean val) {
        supportTag = val;
        return this;
      }

      public Builder supportLink(boolean val) {
        supportLink = val;
        return this;
      }

      public Builder mentionChar(char val) {
        mentionChar = val;
        return this;
      }

      public Builder mTagChar(char val) {
        mTagChar = val;
        return this;
      }

      public Builder mMentionTextFormat(String val) {
        mMentionTextFormat = val;
        return this;
      }

      public Builder mTagTextFormat(String val) {
        mTagTextFormat = val;
        return this;
      }

      public Builder mMentionTextColor(int val) {
        mMentionTextColor = val;
        return this;
      }

      public Builder mTagTextColor(int val) {
        mTagTextColor = val;
        return this;
      }

      public Builder mUrlTextColor(int val) {
        mUrlTextColor = val;
        return this;
      }

      public Builder mMentionEditColor(int val) {
        mMentionEditColor = val;
        return this;
      }

      public Builder mTagEditColor(int val) {
        mTagEditColor = val;
        return this;
      }

      public Config build() {
        return new Config(this);
      }
    }
  }
}
