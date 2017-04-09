## Mentions
- MentionEditText :
support some useful features for mention string(@xxxx,#xxx#，links), such as highlight, intelligent deletion, intelligent selection and '@','#'and `links` input detection, etc.

- MentionTextView :
support some useful features for mention string(@xxxx,#xxx#，links), such as highlight, clickable, custom parser,additional field,etc.

## ScreenShot
![Samples](art/demo.gif)


## Usage

### MentionEditText

- [User](https://github.com/BoBoMEe/Mentions/blob/master/app/src/main/java/com/bobomee/android/mentionedittextdemo/User.java)

```java
public class User implements InsertData{
  //...

  @Override public CharSequence charSequence() {
      return "@"+userName; //provide the CharSequence insert to edittext
    }

    @Override public FormatRange.FormatData formatData() {
      return new UserConvert(this);//provide the formater for the insert data
    }

    @Override public int color() {
      return Color.MAGENTA;//provide the range color
    }

    private class UserConvert implements FormatRange.FormatData {

      public static final String USER_FORMART = "(@%s,id=%s)";
      private final User user;

      public UserConvert(User user) {
        this.user = user;
      }

      @Override public CharSequence formatCharSequence() {//format
        return String.format(USER_FORMART, user.getUserName(), user.getUserId());
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
          mMentionedittext.insert(user);//insert data to edittext
          break;
        //...
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }
}
```

### MentionTextView

```java

public class UserParser implements ParserConverter{

 @Override public Spanned convert(CharSequence source) {
 // covert source to spanned
 }

}

public class MainActivity extends AppCompatActivity {
  @BindView(R.id.mentiontextview) MentionTextView mMentiontextview;
  private UserParser mUserParser = new UserParser();

  @Override protected void onCreate(Bundle savedInstanceState) {
        mMentiontextview.setParserConverter(mUserParser);
        CharSequence convertMetionString = mMentionedittext.getFormatCharSequence();
        mMentiontextview.setText(convertMetionString);

  }
  //...
}

```

more usage:[MainActivity.java](https://github.com/BoBoMEe/MentionEditText/blob/master/app/src/main/java/com/bobomee/android/mentionedittextdemo/MainActivity.java)

## Thanks
- [luckyandyzhang/MentionEditText](https://github.com/luckyandyzhang/MentionEditText)