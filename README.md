## MentionEditText

```java
mMentionedittext.setMentionTextColor(Color.RED);
mMentionedittext.setMentionChar('@');
mMentionedittext.setMentionTextFormat("[@%s,id=%s]");
mMentionedittext.addOnMentionInputListener(new OnMentionInputListener() {
  @Override public void onMentionCharacterInput(char charSequence) {
    mMentionedittext.mentionUser("uid-uid-uid-uid-uid", "杨幂");
  }
});

mBtnCovert.setOnClickListener(new View.OnClickListener() {
  @Override public void onClick(View v) {
    String convertMetionString = mMentionedittext.convertMetionString();
    mCovertedString.setText(convertMetionString);
  }
});
```

## ScreenShot

![Samples](art/demo.gif)

## Thanks

[luckyandyzhang/MentionEditText](https://github.com/luckyandyzhang/MentionEditText)