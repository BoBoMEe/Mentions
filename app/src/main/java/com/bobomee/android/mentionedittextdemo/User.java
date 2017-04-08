package com.bobomee.android.mentionedittextdemo;

import android.graphics.Color;
import com.bobomee.android.mentions.edit.listener.InsertData;
import com.bobomee.android.mentions.model.FormatRange;
import com.bobomee.android.mentions.model.Range;
import java.io.Serializable;

/**
 *
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */
public class User  implements Serializable,InsertData {

  private final CharSequence userId;
  private final CharSequence userName;
  private CharSequence userSex;

  public User(CharSequence userId, CharSequence userName) {
    this.userId = userId;
    this.userName = userName;
  }

  public CharSequence getUserId() {
    return userId;
  }

  public CharSequence getUserName() {
    return userName;
  }
  public CharSequence getUserSex() {
    return userSex;
  }

  public void setUserSex(CharSequence userSex) {
    this.userSex = userSex;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
    if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
    return userSex != null ? userSex.equals(user.userSex) : user.userSex == null;
  }

  @Override public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (userSex != null ? userSex.hashCode() : 0);
    return result;
  }

  @Override public CharSequence charSequence() {
    return "@"+userName;
  }

  @Override public Range range(int start, int end) {
    return new FormatRange(start,end,new UserConvert(this));
  }

  @Override public int color() {
    return Color.MAGENTA;
  }

  private class UserConvert implements FormatRange.FormatData {

    public static final String USER_FORMART = "(%s,id=%s)";
    private final User user;

    public UserConvert(User user) {
      this.user = user;
    }

    @Override public CharSequence formatCharSequence() {
      return String.format(USER_FORMART, user.getUserName(), user.getUserId());
    }
  }
}
