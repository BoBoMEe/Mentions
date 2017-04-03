package com.bobomee.android.mentionedittextdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bobomee.android.mentions.edit.MentionEditText;
import com.bobomee.android.mentions.edit.listener.OnMentionInputListener;
import com.bobomee.android.mentions.text.MentionTextView;

import static com.bobomee.android.mentionedittextdemo.R.id.topic;

public class MainActivity extends AppCompatActivity {

  private MainActivity mMainActivity;
  private MentionEditText mMentionedittext;
  private Button mBtnCovert;
  private TextView mCovertedString;
  private Button mAtUser;
  private Button mTopic;

  public static final int REQUEST_USER_APPEND = 1 << 2;
  public static final int REQUEST_TAG_APPEND = 1 << 3;
  private Button btnClear;
  private OnMentionInputListener mOnMentionInputListener;
  private MentionTextView mentiontext;
  private Button btnCovertToText;

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
    btnClear = (Button) findViewById(R.id.btn_clear);
    mAtUser = (Button) findViewById(R.id.at_user);
    mTopic = (Button) findViewById(topic);

    if (null == mOnMentionInputListener) {
      mOnMentionInputListener = new OnMentionInputListener() {
        @Override public void onMentionCharacterInput(char charSequence) {
          if (charSequence == mMentionedittext.getMentionChar()) {
            startActivityForResult(UserList.getIntent(mMainActivity), REQUEST_USER_APPEND);
          } else if (charSequence == mMentionedittext.getTagChar()) {
            startActivityForResult(TagList.getIntent(mMainActivity), REQUEST_TAG_APPEND);
          }
        }
      };
      mMentionedittext.addOnMentionInputListener(mOnMentionInputListener);
    }

    mBtnCovert.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String convertMetionString = mMentionedittext.convertMetionString();
        mCovertedString.setText(convertMetionString);
      }
    });

    btnClear.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mMentionedittext.clear();
        mCovertedString.setText("");
      }
    });

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
    mentiontext = (MentionTextView) findViewById(R.id.mentiontext);
    btnCovertToText = (Button) findViewById(R.id.btn_covert_to_text);

    btnCovertToText.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //String convertMetionString = mMentionedittext.convertMetionString();

        mentiontext.setText(test);
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

  private String test =
      "(@杨幂,id=y6b7337c3-132e-4dd8-a643-79a9d634de56)#让红包飞#(@1s11112,id=16b7337c3-132e-4dd8-a643-79a9d634de56)#😁好高兴#(@_-cag好;id=h6b7337c3-132e-4dd8-a643-79a9d634de56)";

}
