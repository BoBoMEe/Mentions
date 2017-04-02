package com.bobomee.android.mentionedittextdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bobomee.android.mentionedittext.MentionEditText;
import com.bobomee.android.mentionedittext.listener.OnMentionInputListener;

public class MainActivity extends AppCompatActivity {

  private LinearLayout mActivityMain;
  private MentionEditText mMentionedittext;
  private Button mBtnCovert;
  private TextView mCovertedString;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
  }

  private void initView() {
    mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
    mMentionedittext = (MentionEditText) findViewById(R.id.mentionedittext);
    mBtnCovert = (Button) findViewById(R.id.btn_covert);
    mCovertedString = (TextView) findViewById(R.id.coverted_string);

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
  }
}
