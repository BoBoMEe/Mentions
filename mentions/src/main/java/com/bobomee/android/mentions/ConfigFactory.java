package com.bobomee.android.mentions;

import android.graphics.Color;

import static com.bobomee.android.mentions.ConfigFactory.Config.sConfig;

/**
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

    private Config(Builder builder) {
      supportAt = builder.supportAt;
      supportTag = builder.supportTag;
      mAtChar = builder.mAtChar;
      mTagChar = builder.mTagChar;
      mAtTextFormat = builder.mAtTextFormat;
      mTagTextFormat = builder.mTagTextFormat;
      mAtEditTextColor = builder.mAtEditTextColor;
      mTagEditTextColor = builder.mTagEditTextColor;
    }

    static Config sConfig() {
      return new Config.Builder().build();
    }

    private boolean supportAt = true;
    private boolean supportTag = true;
    private char mAtChar = '@';
    private char mTagChar = '#';
    private String mAtTextFormat = "(@%s,id=%s)";
    private String mTagTextFormat = "#%s#";
    private int mAtEditTextColor = Color.RED;
    private int mTagEditTextColor = Color.BLUE;

    public static Builder newBuilder() {
      return new Builder();
    }

    public static Builder newBuilder(Config copy) {
      Builder builder = new Builder();
      builder.supportAt = copy.supportAt;
      builder.supportTag = copy.supportTag;
      builder.mAtChar = copy.mAtChar;
      builder.mTagChar = copy.mTagChar;
      builder.mAtTextFormat = copy.mAtTextFormat;
      builder.mTagTextFormat = copy.mTagTextFormat;
      builder.mAtEditTextColor = copy.mAtEditTextColor;
      builder.mTagEditTextColor = copy.mTagEditTextColor;
      return builder;
    }

    public boolean isSupportAt() {
      return supportAt;
    }

    public boolean isSupportTag() {
      return supportTag;
    }

    public char getAtChar() {
      return mAtChar;
    }

    public char getTagChar() {
      return mTagChar;
    }

    public String getAtTextFormat() {
      return mAtTextFormat;
    }

    public String getTagTextFormat() {
      return mTagTextFormat;
    }

    public int getAtEditTextColor() {
      return mAtEditTextColor;
    }

    public int getTagEditTextColor() {
      return mTagEditTextColor;
    }

    /**
     * {@code Config} builder static inner class.
     */
    public static final class Builder {
      private boolean supportAt = true;
      private boolean supportTag = true;
      private char mAtChar = '@';
      private char mTagChar = '#';
      private String mAtTextFormat = "(@%s,id=%s)";
      private String mTagTextFormat = "#%s#";
      private int mAtEditTextColor = Color.RED;
      private int mTagEditTextColor = Color.BLUE;

      private Builder() {
      }

      /**
       * Sets the {@code supportAt} and returns a reference to this Builder so that the methods can
       * be chained together.
       *
       * @param supportAt the {@code supportAt} to set
       * @return a reference to this Builder
       */
      public Builder supportAt(boolean supportAt) {
        this.supportAt = supportAt;
        return this;
      }

      /**
       * Sets the {@code supportTag} and returns a reference to this Builder so that the methods
       * can
       * be chained together.
       *
       * @param supportTag the {@code supportTag} to set
       * @return a reference to this Builder
       */
      public Builder supportTag(boolean supportTag) {
        this.supportTag = supportTag;
        return this;
      }

      /**
       * Sets the {@code mAtChar} and returns a reference to this Builder so that the methods can
       * be
       * chained together.
       *
       * @param mAtChar the {@code mAtChar} to set
       * @return a reference to this Builder
       */
      public Builder mAtChar(char mAtChar) {
        this.mAtChar = mAtChar;
        return this;
      }

      /**
       * Sets the {@code mTagChar} and returns a reference to this Builder so that the methods can
       * be chained together.
       *
       * @param mTagChar the {@code mTagChar} to set
       * @return a reference to this Builder
       */
      public Builder mTagChar(char mTagChar) {
        this.mTagChar = mTagChar;
        return this;
      }

      /**
       * Sets the {@code mAtTextFormat} and returns a reference to this Builder so that the methods
       * can be chained together.
       *
       * @param mAtTextFormat the {@code mAtTextFormat} to set
       * @return a reference to this Builder
       */
      public Builder mAtTextFormat(String mAtTextFormat) {
        this.mAtTextFormat = mAtTextFormat;
        return this;
      }

      /**
       * Sets the {@code mTagTextFormat} and returns a reference to this Builder so that the
       * methods
       * can be chained together.
       *
       * @param mTagTextFormat the {@code mTagTextFormat} to set
       * @return a reference to this Builder
       */
      public Builder mTagTextFormat(String mTagTextFormat) {
        this.mTagTextFormat = mTagTextFormat;
        return this;
      }

      /**
       * Sets the {@code mAtEditTextColor} and returns a reference to this Builder so that the
       * methods can be chained together.
       *
       * @param mAtEditTextColor the {@code mAtEditTextColor} to set
       * @return a reference to this Builder
       */
      public Builder mAtEditTextColor(int mAtEditTextColor) {
        this.mAtEditTextColor = mAtEditTextColor;
        return this;
      }

      /**
       * Sets the {@code mTagEditTextColor} and returns a reference to this Builder so that the
       * methods can be chained together.
       *
       * @param mTagEditTextColor the {@code mTagEditTextColor} to set
       * @return a reference to this Builder
       */
      public Builder mTagEditTextColor(int mTagEditTextColor) {
        this.mTagEditTextColor = mTagEditTextColor;
        return this;
      }

      /**
       * Returns a {@code Config} built from the parameters previously set.
       *
       * @return a {@code Config} built with parameters of this {@code Config.Builder}
       */
      public Config build() {
        return new Config(this);
      }
    }
  }
}
