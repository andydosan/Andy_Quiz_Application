package com.example.quizapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

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
    ArrayList<ArrayList<String>> tests = new ArrayList<ArrayList<String>>();
    ArrayList<String> inner = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button5);
        text = (TextView) findViewById(R.id.textView);
        spinnerQuizzes = findViewById(R.id.spinner);
        String[] quizzes= getResources().getStringArray(R.array.quizzes);

        spinnerQuizzes.setOnItemSelectedListener(this);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, quizzes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuizzes.setAdapter(adapter);

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
            System.out.println(quizLinks.size()+" quizzes found");
            ArrayList<String> quizzes = new ArrayList<String>();
            for(String url:quizLinks)
            {
                System.out.println("connecting to "+url);
                doc = Jsoup.connect(url).get();
                quizzes.add(extractQuiz(doc.html()));

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


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