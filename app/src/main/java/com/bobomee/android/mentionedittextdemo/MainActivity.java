package com.bobomee.android.mentionedittextdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bobomee.android.mentions.edit.MentionEditText;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.mentionedittext) MentionEditText mMentionedittext;
  @BindView(R.id.btn_covert) Button mBtnCovert;
  @BindView(R.id.coverted_string) TextView mCovertedString;
  @BindView(R.id.btn_clear) Button mBtnClear;
  @BindView(R.id.at_user) Button mAtUser;
  @BindView(R.id.topic) Button mTopic;
  @BindView(R.id.insert) Button mInsert;
  private MainActivity mMainActivity;

  public static final int REQUEST_USER_APPEND = 1 << 2;
  public static final int REQUEST_TAG_APPEND = 1 << 3;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    mMainActivity = this;
    initListener();
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

  @OnClick({ R.id.btn_covert, R.id.btn_clear, R.id.at_user, R.id.topic, R.id.insert })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_covert:
        CharSequence convertMetionString = mMentionedittext.convertMetionString();
        mCovertedString.setText(convertMetionString);
        break;
      case R.id.btn_clear:
        mMentionedittext.clear();
        mCovertedString.setText("");
        break;
      case R.id.at_user:
        startActivityForResult(UserList.getIntent(mMainActivity), REQUEST_USER_APPEND);
        break;
      case R.id.topic:
        startActivityForResult(TagList.getIntent(mMainActivity), REQUEST_TAG_APPEND);
        break;
      case R.id.insert:
        mMentionedittext.insert("insert a range CharSequence");
        break;
    }
  }
}
