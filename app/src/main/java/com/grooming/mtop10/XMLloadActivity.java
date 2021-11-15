package com.grooming.mtop10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Xml;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLloadActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_m_lload);
        tv = findViewById(R.id.tv_xmllog);
        RadioGroup rg = findViewById(R.id.rg_xml);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_simple_xml) {
                    tv.setText(parseSimple(R.xml.entity1_demo));
                } else if (checkedId == R.id.rb_xxe1) {
                    tv.setText(parseSimple(R.xml.entity1_xxe1));
                } else if (checkedId == R.id.rb_xxe2) {
                    tv.setText(parseSimple(R.xml.entity1_bomb));
                } else if (checkedId == R.id.rb_assets) {
                    tv.setText(parseFromAssets());
                }
            }
        });
    }

    String parseSimple(int id) {
//        xmlResourceParser
        StringBuilder stringBuilder = new StringBuilder();
        try {
            XmlResourceParser xmlResourceParser = getResources().getXml(id);

            while (xmlResourceParser.next() != XmlPullParser.END_DOCUMENT) {
                if (xmlResourceParser.getEventType() == XmlPullParser.TEXT)
                    stringBuilder.append(xmlResourceParser.getText() + "\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            stringBuilder.append("\nerror:\n\t" + e.toString());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            stringBuilder.append("\nerror:\n\t" + e.toString());
        }
        return stringBuilder.toString();
    }

    String parseFromAssets() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open("entity_xxe_assets.xml");
            int len = is.available();
            byte[] bytes = new byte[len];
            is.read(bytes);

            String xml =  new String(bytes);
            XmlPullParser xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlPullParser.setFeature(Xml.FEATURE_RELAXED,true);
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL,true);
//            xmlPullParser.setFeature(XmlPullParser.FEATURE_VALIDATION,true);
            xmlPullParser.setInput(new StringReader(xml));
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                if (xmlPullParser.getEventType() == XmlPullParser.TEXT)
                    stringBuilder.append(xmlPullParser.getText() + "\n");
            }
            return stringBuilder.toString();

//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
//            Document document = documentBuilder.parse(is);
//            String s = document.getElementsByTagName("foo").item(0).getTextContent();
//
//            return s;

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return "null";
    }
}