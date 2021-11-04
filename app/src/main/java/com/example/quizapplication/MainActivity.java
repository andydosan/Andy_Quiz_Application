package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button button;
    TextView text;
    private Spinner spinnerQuizzes;
    public static String quizname;
    public static ArrayList<ArrayList<String>> tests = new ArrayList<ArrayList<String>>();
    String[] quizzes = new String[10];
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button5);
        text = (TextView) findViewById(R.id.textView);
        spinnerQuizzes = findViewById(R.id.spinner);

        spinnerQuizzes.setOnItemSelectedListener(this);
        spinnerQuizzes.setEnabled(false);
        button.setEnabled(false);

        description_webscrape dw = new description_webscrape();
        dw.execute();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });
    }

    public void startQuiz() {
        Intent intent = new Intent(this, Test.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if (parent.getId() == R.id.spinner) {
            quizname = parent.getItemAtPosition(position).toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class description_webscrape extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            org.jsoup.nodes.Document doc = null;
            try {
                doc = Jsoup.connect("https://sites.google.com/asianhope.org/mobileresources").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            org.jsoup.select.Elements links = doc.select("a[href]");
            Set<String> quizLinks = new HashSet<String>();
            for(org.jsoup.nodes.Element link:links)
            {
                if(link.attr("href").contains("mobileresources/q"))
                {
                    quizLinks.add("https://sites.google.com"+link.attr("href"));
                }
            }
            Log.d("MyApp", quizLinks.size()+" quizzes found");
            for(String url:quizLinks)
            {

                Log.d("MyApp", "connecting to " + url);
                try {
                    doc = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ArrayList<String> inner = new ArrayList<String>();
                inner.add(url.replace("https://sites.google.com/asianhope.org/mobileresources/q",""));
                try {
                    inner.add(extractQuiz(doc.html()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tests.add(inner);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            text.setText("Choose a quiz");
            for (int i = 0; i < tests.size(); i++) {
                quizzes[i] = tests.get(i).get(0);
            }
            adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, quizzes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerQuizzes.setAdapter(adapter);
            spinnerQuizzes.setEnabled(true);
            button.setEnabled(true);
        }
    }

    private static String extractQuiz(String html) throws IOException {
        boolean strictMode = true;
        String paragraphTagOpen = "<p[^>]+>";
        String paragraphTagClose = "</p[^>]*>";
        String quizTagOpen = "<quiz";
        String quizTagClose ="</quiz>";



        String quiz = html;
        quiz = Parser.unescapeEntities(quiz, strictMode);
        int beginQuizXml = quiz.lastIndexOf(quizTagOpen);
        int endQuizXml = quiz.lastIndexOf(quizTagClose) + quizTagClose.length();

        Validate.isTrue(beginQuizXml>=0&&endQuizXml>=0," quiz not found ");

        quiz = quiz.substring(beginQuizXml, endQuizXml).replaceAll(paragraphTagOpen, "")
                .replaceAll(paragraphTagClose, "").trim();
        return quiz;
    }

}