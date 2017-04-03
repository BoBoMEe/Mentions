package com.bobomee.android.mentionedittextdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.bobomee.android.mentionedittext.MentionEditText;
import com.bobomee.android.mentionedittext.listener.OnMentionInputListener;

import static com.bobomee.android.mentionedittextdemo.R.id.topic;

public class MainActivity extends AppCompatActivity {

  private MainActivity mMainActivity;
  private MentionEditText mMentionedittext;
  private Button mBtnCovert;
  private TextView mCovertedString;
  private Button mAtUser;
  private Button mTopic;

  public static final int REQUEST_USER = 1;
  public static final int REQUEST_TAG = 1 << 1;

  public static final int REQUEST_USER_APPEND = 1 << 2;
  public static final int REQUEST_TAG_APPEND = 1 << 3;
  private Button btnClear;
  private OnMentionInputListener mOnMentionInputListener;

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

    mMentionedittext.setMentionTextColor(Color.RED);
    mMentionedittext.setMentionChar('@');
    mMentionedittext.setMentionTextFormat("(@%s:%s)");

    mMentionedittext.setTagTextColor(Color.BLUE);
    mMentionedittext.setTagChar('#');
    mMentionedittext.setTagTextFormat("[#%s:%s]");

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
        startActivityForResult(UserList.getIntent(mMainActivity), REQUEST_USER);
      }
    });

    mTopic.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        startActivityForResult(TagList.getIntent(mMainActivity), REQUEST_TAG);
      }
    });
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (resultCode == Activity.RESULT_OK && null != data) {
      switch (requestCode) {
        case REQUEST_USER:
          User user = data.getParcelableExtra(UserList.RESULT_USER);
          mMentionedittext.mentionUser(user.getUserId(), user.getUserName());
          break;
        case REQUEST_TAG:
          Tag tag = data.getParcelableExtra(TagList.RESULT_TAG);
          mMentionedittext.mentionTag(tag.getTagId(), tag.getTagLable());
          break;
        case REQUEST_USER_APPEND:
          User user1 = data.getParcelableExtra(UserList.RESULT_USER);
          mMentionedittext.appendUser(user1.getUserId(), user1.getUserName());
          break;
        case REQUEST_TAG_APPEND:
          Tag tag1 = data.getParcelableExtra(TagList.RESULT_TAG);
          mMentionedittext.appendTag(tag1.getTagId(), tag1.getTagLable());
          break;
      }
    }

    super.onActivityResult(requestCode, resultCode, data);
  }
}
