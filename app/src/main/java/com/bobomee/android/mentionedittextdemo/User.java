package com.bobomee.android.mentionedittextdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Project ID：400YF17051<br/>
 * Resume:
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/3 汪波 first commit
 */
public class User  implements Parcelable{

  private String userId;
  private String userName;

  private String userSex;

  ///....

  public User(String userId, String userName) {
    this.userId = userId;
    this.userName = userName;
  }

  protected User(Parcel in) {
    userId = in.readString();
    userName = in.readString();
    userSex = in.readString();
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override public User createFromParcel(Parcel in) {
      return new User(in);
    }

    @Override public User[] newArray(int size) {
      return new User[size];
    }
  };

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserSex() {
    return userSex;
  }

  public void setUserSex(String userSex) {
    this.userSex = userSex;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    if (userId != null ? !userId.equals(user.userId) : user.userId != null) return false;
    if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
    if (userSex != null ? !userSex.equals(user.userSex) : user.userSex != null) return false;

    return true;
  }

  @Override public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (userSex != null ? userSex.hashCode() : 0);
    return result;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(userId);
    dest.writeString(userName);
    dest.writeString(userSex);
  }
}
