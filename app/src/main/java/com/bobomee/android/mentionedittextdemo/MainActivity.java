package com.bobomee.android.mentionedittextdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.mentionedittextdemo.edit.Tag;
import com.bobomee.android.mentionedittextdemo.edit.TagList;
import com.bobomee.android.mentionedittextdemo.edit.User;
import com.bobomee.android.mentionedittextdemo.edit.UserList;
import com.bobomee.android.mentionedittextdemo.text.parser.Parser;
import com.bobomee.android.mentions.edit.MentionEditText;
import com.bobomee.android.mentions.text.MentionTextView;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.mentionedittext) MentionEditText mMentionedittext;
  @BindView(R.id.btn_covert) Button mBtnCovert;
  @BindView(R.id.coverted_string) TextView mCovertedString;
  @BindView(R.id.btn_clear) Button mBtnClear;
  @BindView(R.id.at_user) Button mAtUser;
  @BindView(R.id.topic) Button mTopic;
  @BindView(R.id.insert) Button mInsert;
  @BindView(R.id.btn_show) Button mBtnShow;
  @BindView(R.id.mentiontextview) MentionTextView mMentiontextview;
  private MainActivity mMainActivity;

  public static final int REQUEST_USER_APPEND = 1 << 2;
  public static final int REQUEST_TAG_APPEND = 1 << 3;

  private Parser mTagParser = new Parser();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    mMainActivity = this;

    initViews();
    initListener();
  }

  private void initViews() {
    mCovertedString.setMovementMethod(new ScrollingMovementMethod());
    mMentiontextview.setMovementMethod(new LinkMovementMethod());
    mMentiontextview.setParserConverter(mTagParser);

    mMentiontextview.setText(str2);
  }

  private void initListener() {

    mMentionedittext.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count == 1 && !TextUtils.isEmpty(s)) {
          char mentionChar = s.toString().charAt(start);
          int selectionStart = mMentionedittext.getSelectionStart();
          if (mentionChar == '@') {
            startActivityForResult(UserList.getIntent(mMainActivity), REQUEST_USER_APPEND);
            mMentionedittext.getText().delete(selectionStart - 1, selectionStart);
          } else if (mentionChar == '#') {
            startActivityForResult(TagList.getIntent(mMainActivity), REQUEST_TAG_APPEND);
            mMentionedittext.getText().delete(selectionStart - 1, selectionStart);
          }
        }
      }

      @Override public void afterTextChanged(Editable s) {
      }
    });
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (resultCode == Activity.RESULT_OK && null != data) {
      switch (requestCode) {
        case REQUEST_USER_APPEND:
          User user = (User) data.getSerializableExtra(UserList.RESULT_USER);
          mMentionedittext.insert(user);
          break;
        case REQUEST_TAG_APPEND:
          Tag tag = (Tag) data.getSerializableExtra(TagList.RESULT_TAG);
          mMentionedittext.insert(tag);
          break;
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }

  @OnClick({
      R.id.btn_covert, R.id.btn_clear, R.id.at_user, R.id.topic, R.id.insert, R.id.btn_show
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_covert:
        CharSequence convertMetionString = mMentionedittext.getFormatCharSequence();
        mCovertedString.setText(convertMetionString);
        break;
      case R.id.btn_clear:
        mMentionedittext.clear();
        mCovertedString.setText("");
        mMentiontextview.setText("");
        break;
      case R.id.at_user:
        startActivityForResult(UserList.getIntent(mMainActivity), REQUEST_USER_APPEND);
        break;
      case R.id.topic:
        startActivityForResult(TagList.getIntent(mMainActivity), REQUEST_TAG_APPEND);
        break;
      case R.id.insert:
        mMentionedittext.insert("http://www.baidu.com/");
        break;
      case R.id.btn_show:
        CharSequence convertMetionString1 = mMentionedittext.getFormatCharSequence();
        mMentiontextview.setText(convertMetionString1);
        break;
    }
  }


  String str2 =
      "myfont效果：<tag id='100'>导演：轻口味</tag>副导演:重口味";


}
