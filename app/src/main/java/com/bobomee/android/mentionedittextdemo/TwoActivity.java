package com.bobomee.android.mentionedittextdemo;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bobomee.android.mentionedittextdemo.text.parser.xml.Data;
import com.bobomee.android.mentionedittextdemo.text.parser.xml.DataHandler;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Project ID：400YF17051
 * Resume:     <br/>
 *
 * @author 汪波
 * @version 1.0
 * @see
 * @since 2017/4/10.汪波.
 */
public class TwoActivity extends AppCompatActivity {

  @BindView(R.id.text) TextView mText;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_two);
    ButterKnife.bind(this);

    Data data = _parseXml();

    mText.setText(data.toString());

  }


  private Data _parseXml() {
    Data data = null;

    // sax stuff
    try {
      SAXParserFactory spf = SAXParserFactory.newInstance();
      SAXParser sp = spf.newSAXParser();

      XMLReader xr = sp.getXMLReader();

      DataHandler dataHandler = new DataHandler();
      xr.setContentHandler(dataHandler);

      AssetManager am = getAssets();
      InputStream open = am.open("xmldata.xml");

      xr.parse(new InputSource(open));

      data = dataHandler.getData();

    } catch(ParserConfigurationException pce) {
      Log.e("SAX XML", "sax parse error", pce);
    } catch(SAXException se) {
      Log.e("SAX XML", "sax error", se);
    } catch(IOException ioe) {
      Log.e("SAX XML", "sax parse io error", ioe);
    }

    return data;
  }
}
