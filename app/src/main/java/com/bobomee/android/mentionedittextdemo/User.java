package com.bobomee.android.mentionedittextdemo;

import com.bobomee.android.mentions.model.Model;
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
public class User extends Model implements Serializable{

  private CharSequence userId;
  private CharSequence userName;
  private CharSequence userSex;

  public User(CharSequence userId, CharSequence userName) {
    super("@"+userName);
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
}
