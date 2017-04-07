package com.bobomee.android.mentionedittextdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bobomee.android.mentions.ConfigFactory;
import com.bobomee.android.mentions.edit.MentionEditText;

public class MainActivity extends AppCompatActivity {

  private MainActivity mMainActivity;
  private MentionEditText mMentionedittext;
  private Button mBtnCovert;
  private TextView mCovertedString;
  private Button mAtUser;
  private Button mTopic;
  private Button mBtnClear;

  public static final int REQUEST_USER_APPEND = 1 << 2;
  public static final int REQUEST_TAG_APPEND = 1 << 3;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mMainActivity = this;
    initView();
  }

  private void initView() {
    mMentionedittext = (MentionEditText) findViewById(R.id.mentionedittext);
    mBtnCovert = (Button) findViewById(R.id.btn_covert);
    mCovertedString = (TextView) findViewById(R.id.coverted_string);
    mBtnClear = (Button) findViewById(R.id.btn_clear);
    mAtUser = (Button) findViewById(R.id.at_user);
    mTopic = (Button) findViewById(R.id.topic);

    ConfigFactory.INSTANCE.config(ConfigFactory.Config.newBuilder()
        //.supportAt(false)
        //.supportTag(false)
        .mAtEditTextColor(Color.BLUE)
        .mTagEditTextColor(Color.CYAN)
        .build());

    mAtUser.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mMentionedittext.append("@");
      }
    });
    mTopic.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mMentionedittext.append("#");
      }
    });
    mBtnCovert.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String convertMetionString = mMentionedittext.convertMetionString();
        mCovertedString.setText(convertMetionString);
      }
    });
    mBtnClear.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mMentionedittext.clear();
        mCovertedString.setText("");
      }
    });

    mMentionedittext.addTextChangedListener(new TextWatcher() {
      @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count == 1 && !TextUtils.isEmpty(s)) {
          char mentionChar = s.toString().charAt(start);
          ConfigFactory.Config config = ConfigFactory.INSTANCE.config();
          if (mentionChar == config.getAtChar()
              ) {
            startActivityForResult(UserList.getIntent(mMainActivity), REQUEST_USER_APPEND);
          }else if (mentionChar == config.getTagChar()){
            startActivityForResult(TagList.getIntent(mMainActivity), REQUEST_TAG_APPEND);
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
          User user = data.getParcelableExtra(UserList.RESULT_USER);
          mMentionedittext.appendUser(user.getUserId(), user.getUserName());
          break;
        case REQUEST_TAG_APPEND:
          Tag tag = data.getParcelableExtra(TagList.RESULT_TAG);
          mMentionedittext.appendTag(tag.getTagId(), tag.getTagLable());
          break;
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }
}
