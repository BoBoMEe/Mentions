package com.bobomee.android.mentionedittextdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bobomee.android.mentions.ConfigFactory;
import com.bobomee.android.mentions.edit.MentionEditText;
import com.bobomee.android.mentions.edit.listener.OnMentionInputListener;
import com.bobomee.android.mentions.model.BaseModel;
import com.bobomee.android.mentions.model.Range;
import com.bobomee.android.mentions.text.MentionTextView;
import com.bobomee.android.mentions.text.listener.SpanClickListener;

import static com.bobomee.android.mentionedittextdemo.R.id.topic;

public class MainActivity extends AppCompatActivity {

  private MainActivity mMainActivity;
  private MentionEditText mMentionedittext;
  private Button mBtnCovert;
  private TextView mCovertedString;
  private Button mAtUser;
  private Button mTopic;
  private Button mBtnClear;
  private OnMentionInputListener mOnMentionInputListener;
  private MentionTextView mMentionTextView;
  private Button mBtnCovertToText;

  public static final int REQUEST_USER_APPEND = 1 << 2;
  public static final int REQUEST_TAG_APPEND = 1 << 3;
  private SpanClickListener mSpanClickListener;

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
    mTopic = (Button) findViewById(topic);
    mMentionTextView = (MentionTextView) findViewById(R.id.mentiontext);
    mBtnCovertToText = (Button) findViewById(R.id.btn_covert_to_text);

    ConfigFactory.INSTANCE.config(
        new ConfigFactory.Config.Builder()
            //.supportAt(false)
            //.supportTag(false)
            .mMentionEditColor(Color.BLUE)
            .mMentionTextColor(Color.BLUE)
            .mTagTextColor(Color.CYAN)
            .mTagEditColor(Color.CYAN)
            .build()
    );

    if (null == mOnMentionInputListener) {
      mOnMentionInputListener = new OnMentionInputListener() {
        @Override public void onMentionCharacterInput(char charSequence) {
          if (charSequence == ConfigFactory.INSTANCE.config().getMentionChar()) {
            startActivityForResult(UserList.getIntent(mMainActivity), REQUEST_USER_APPEND);
          } else if (charSequence == ConfigFactory.INSTANCE.config().getTagChar()) {
            startActivityForResult(TagList.getIntent(mMainActivity), REQUEST_TAG_APPEND);
          }
        }
      };
      mMentionedittext.addOnMentionInputListener(mOnMentionInputListener);
    }

    if (null == mSpanClickListener) {
      mSpanClickListener = new SpanClickListener() {
        @Override public void click(View widget, Range mRange) {
          Toast.makeText(mMainActivity, mRange.toString(), Toast.LENGTH_SHORT).show();
          if (mRange.getType() == BaseModel.TYPE_URL) {
            String lable = mRange.getLable();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(lable);
            intent.setData(content_url);
            mMainActivity.startActivity(intent);
          }
        }
      };
      mMentionTextView.addSpanClickListener(mSpanClickListener);
    }

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
    mBtnCovertToText.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String convertMetionString = mMentionedittext.convertMetionString();
        mMentionTextView.setText(convertMetionString);
      }
    });
    mBtnClear.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mMentionedittext.clear();
        mCovertedString.setText("");
        mMentionTextView.clear();
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
