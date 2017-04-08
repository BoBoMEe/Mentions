## Mentions
- MentionEditText :
adds some useful features for mention string(@xxxx,#xxx#，links), such as highlight, intelligent deletion, intelligent selection and '@','#'and `links` input detection, etc.

## ScreenShot
![Samples](art/demo.gif)


## Usage

- [User](https://github.com/BoBoMEe/Mentions/blob/master/app/src/main/java/com/bobomee/android/mentionedittextdemo/User.java)

```java
public class User implements InsertData{
  //...

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
    //...

    @Override public CharSequence formatCharSequence() {
      return "";
    }
  }
}
```

- [MainActivity.java](https://github.com/BoBoMEe/MentionEditText/blob/master/app/src/main/java/com/bobomee/android/mentionedittextdemo/MainActivity.java)

```java
public class MainActivity extends AppCompatActivity{
@BindView(R.id.mentionedittext) MentionEditText mMentionedittext;
@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (resultCode == Activity.RESULT_OK && null != data) {
      switch (requestCode) {
        case REQUEST_USER_APPEND:
          User user = (User) data.getSerializableExtra(UserList.RESULT_USER);
          mMentionedittext.insert(user);
          break;
        //...
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }
}
```

more usage:[MainActivity.java](https://github.com/BoBoMEe/MentionEditText/blob/master/app/src/main/java/com/bobomee/android/mentionedittextdemo/MainActivity.java)

## Thanks
- [luckyandyzhang/MentionEditText](https://github.com/luckyandyzhang/MentionEditText)