package com.bobomee.android.mentionedittextdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.bobomee.android.mentionedittextdemo.text.parser.xml.FontUtil;

/**
 * Project ID：400YF17051
 * Resume:     <br/>
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/11.汪波.
 */
public class ThreeActivity extends AppCompatActivity {
  private ImageView iv1;
  private ImageView iv2;
  String str1="font效果:<tag id='#00ff00' name='导演：轻口味'>#导演：轻口味#</tag>副导演:重口味";
  //myfont标签不能裸着，即必须有html等标签包裹，或者前面有其他内容，否则字体大小不能起作用
  String str2="myfont效果：<br><myfont color='#00ff00' size='60'>导演：轻口味</myfont><br><myfont color='#ff0000' size='80'>副导演:重口味</myfont>";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_three);
    iv1 = (ImageView) findViewById(R.id.iv1);
    iv2 = (ImageView) findViewById(R.id.iv2);
    iv1.setImageBitmap(FontUtil.getImage(this, 600,400, Color.parseColor("#000000"), Color.parseColor("#ffffff"), 20, str1));
    iv2.setImageBitmap(FontUtil.getImage(this, 600, 400, Color.parseColor("#000000"), Color.parseColor("#ffffff"), 20, str2));
  }
}
