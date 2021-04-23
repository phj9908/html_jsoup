package org.techtown.capture.my_news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.jsoup.Jsoup;             // 파싱 할 때 필수
import org.jsoup.nodes.Element;     // 파싱 할 때 필수
import org.jsoup.select.Elements;   // 파싱 할 때 필수
import org.jsoup.nodes.Document;    // 파싱 할 때 필수

import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String htmlPageUrl = "https://www.naver.com/";
    private TextView textviewHtmlDocument;
    private String htmlContentTnStringFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textviewHtmlDocument = (TextView) findViewById(R.id.textView);
        Button htmlTitleButton = (Button) findViewById(R.id.button);

        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod());
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });


    }
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void>{


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params){
            try{
                Document doc = (Document) Jsoup.connect(htmlPageUrl).get(); // 파싱하고 싶은 페이지 링크 정보 get
                Elements links = doc.select("a[href]"); // select() : 원하는 태그의 부분만 뽑아냄

                for(Element link:links){ // "abs:href"와 일치하는 부분을 추출하여 특정태그의 내용을 뽑아냄
                    htmlContentTnStringFormat+= (link.attr("abs:href")+"("+link.text().trim()+")\n");
                }
            } catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            textviewHtmlDocument.setText(htmlContentTnStringFormat);
        }
    }


}