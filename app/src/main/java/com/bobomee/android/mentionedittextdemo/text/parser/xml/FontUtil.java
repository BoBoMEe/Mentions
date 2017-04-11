package com.bobomee.android.mentionedittextdemo.text.parser.xml;

import java.lang.reflect.Field;

import org.xml.sax.XMLReader;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.Html.TagHandler;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;

public class FontUtil {
	public static Bitmap convertViewToBitmap(View view, int width, int height) {
        if (view != null) {
            view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache();
            return bitmap;
        }
        return null;
    }
 public static Bitmap getImage(Context context,int width,int height,int bgColor,int frColor,int textSize,String content){
	 TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(bgColor);//打开会导致最后片尾显示不出来，卡在上一帧上
        textView.setTextColor(frColor);
        textView.setTextSize(textSize);
        textView.setPadding(5,5,5,5);
        textView.setText(Html.fromHtml(content, null, new TagHandler() {
        	class MyFont{
        		public String mColor;
                public String mFace;
                public String mSize;
                public MyFont(String color, String size,String face) {
                    mColor = color;
                    mSize=size;
                    mFace = face;
                }
        	}
			@Override
			public void handleTag(boolean opening, String tag, Editable output,
					XMLReader xmlReader) {
				if (tag.toLowerCase().equals("myfont")) {  
		            if (opening) {  
		                startMyFont(tag, output, xmlReader);  
		            } else {  
		                endMyFont(tag, output, xmlReader);  
		            }  
		        }  
			}
			 private  Object getLast(Spanned text, Class kind) {
			        /*
			         * This knows that the last returned object from getSpans()
			         * will be the most recently added.
			         */
			        Object[] objs = text.getSpans(0, text.length(), kind);

			        if (objs.length == 0) {
			            return null;
			        } else {
			            return objs[objs.length - 1];
			        }
			    }
			private void endMyFont(String tag, Editable text,
					XMLReader xmlReader) {
				//myfont标签不能裸着，即必须有html等标签包裹，或者前面有其他内容，否则字体大小不能起作用
				//即getlast变成从后面取，最后的内容的范围是0到文本全长度
				int  len = text.length();
		        Object obj = getLast(text, MyFont.class);
		        int where = text.getSpanStart(obj);
		        text.removeSpan(obj);
		        Log.e("AAA", "where:"+where+",len:"+len);
		        if (where != len) {
		        	MyFont f = (MyFont) obj;

		            if (!TextUtils.isEmpty(f.mColor)) {
		                if (f.mColor.startsWith("@")) {
		                    Resources res = Resources.getSystem();
		                    String name = f.mColor.substring(1);
		                    int colorRes = res.getIdentifier(name, "color", "android");
		                    if (colorRes != 0) {
		                        ColorStateList colors = res.getColorStateList(colorRes);
		                        text.setSpan(new TextAppearanceSpan(null, 0, 0, colors, null),
		                                where, len,
		                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		                    }
		                } else {
		                    int c = Color.parseColor(f.mColor);
		                    if (c != -1) {
		                        text.setSpan(new ForegroundColorSpan(c | 0xFF000000),
		                                where, len,
		                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		                    }
		                }
		            }
		            if(f.mSize!=null){
		            	text.setSpan(new AbsoluteSizeSpan(Integer.parseInt(f.mSize)), where, len,
	                             Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		            }
		            if (f.mFace != null) {
		                text.setSpan(new TypefaceSpan(f.mFace), where, len,
		                             Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		            }
		        }
			}
			private void startMyFont(String tag, Editable output,
					XMLReader xmlReader) {
		        try {  
		            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");  
		            elementField.setAccessible(true);  
		            Object element = elementField.get(xmlReader);  
		            Field attsField = element.getClass().getDeclaredField("theAtts");  
		            attsField.setAccessible(true);  
		            Object atts = attsField.get(element);  
		            Field dataField = atts.getClass().getDeclaredField("data");  
		            dataField.setAccessible(true);  
		            String[] data = (String[]) dataField.get(atts);  
		            Field lengthField = atts.getClass().getDeclaredField("length");  
		            lengthField.setAccessible(true);  
		            int len = (Integer) lengthField.get(atts);  
		            String color = null;  
		            String size = null;  
		            String face=null;
		            for (int i = 0; i < len; i++) {  
		                if ("color".equals(data[i * 5 + 1])) {  
		                	color = data[i * 5 + 4];  
		                }  else if ("size".equals(data[i * 5 + 1])) {  
		                	size = data[i * 5 + 4];  
		                } else if ("face".equals(data[i * 5 + 1])) {  
		                	face = data[i * 5 + 4];  
		                }
		            }  
		            output.setSpan(new MyFont(color, size,face), output.length(), output.length(), Spannable.SPAN_MARK_MARK);
		        } catch (NoSuchFieldException e) {  
		            e.printStackTrace();  
		        } catch (IllegalAccessException e) {  
		            e.printStackTrace();  
		        }  
			}
			
		}));
        return convertViewToBitmap(textView, width, height);
 }
}
