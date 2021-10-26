package com.example.quizapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Test extends AppCompatActivity {

    TextView txt;
    Button button1, button2, button3, button4;

    private Question mQuestions = new Question();
    private String mAnswer;
    private int score = 0;
    private int guesses = 0;
    private int currentquestion = 0;
    private int mQuestionsLength;
    String answers[] = {"answerA", "answerB", "answerC", "answerD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        txt = (TextView) findViewById(R.id.txt);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        try {
            InputStream is = getAssets().open(MainActivity.quizname);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("question");

            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    mQuestions.mQuestions[i] = getValue("stem", element2);
                    for (int j=0; j<4; j++)  {
                        mQuestions.mChoices[i][j] = getValue(answers[j], element2);
                    }
                    mQuestions.mCorrectAnswers[i] = getValue("key", element2);
                }
            }
        } catch (Exception e) {e.printStackTrace();}

        mQuestionsLength = mQuestions.mQuestions.length;
        updateQuestion(currentquestion);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button1.getText().equals(mAnswer)) {
                    guesses++;
                    currentquestion++;
                    if (currentquestion == mQuestionsLength) {
                        quizDone();
                    } else {
                        updateQuestion(currentquestion);
                    }
                } else {
                    button1.setEnabled(false);
                    button1.setBackgroundColor(Color.RED);
                    guesses++;
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button2.getText().equals(mAnswer)) {
                    guesses++;
                    currentquestion++;
                    if (currentquestion == mQuestionsLength) {
                        quizDone();
                    } else {
                        updateQuestion(currentquestion);
                    }
                } else {
                    button2.setEnabled(false);
                    button2.setBackgroundColor(Color.RED);
                    guesses++;
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button3.getText().equals(mAnswer)) {
                    guesses++;
                    currentquestion++;
                    if (currentquestion == mQuestionsLength) {
                        quizDone();
                    } else {
                        updateQuestion(currentquestion);
                    }
                } else {
                    button3.setEnabled(false);
                    button3.setBackgroundColor(Color.RED);
                    guesses++;
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button4.getText().equals(mAnswer)) {
                    guesses++;
                    currentquestion++;
                    if (currentquestion == mQuestionsLength) {
                        quizDone();
                    } else {
                        updateQuestion(currentquestion);
                    }
                } else {
                    button4.setEnabled(false);
                    button4.setBackgroundColor(Color.RED);
                    guesses++;
                }
            }
        });
    }

    private void updateQuestion(int num) {
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);

        button1.setBackgroundColor(Color.CYAN);
        button2.setBackgroundColor(Color.CYAN);
        button3.setBackgroundColor(Color.CYAN);
        button4.setBackgroundColor(Color.CYAN);

        txt.setText(mQuestions.getQuestions(num));
        button1.setText(mQuestions.getChoice1(num));
        button2.setText(mQuestions.getChoice2(num));
        button3.setText(mQuestions.getChoice3(num));
        button4.setText(mQuestions.getChoice4(num));

        mAnswer = mQuestions.getCorrectAnswer(num);
    }

    private void quizDone() {
        score = Math.round((float) mQuestionsLength/guesses * 100);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Test.this);
        alertDialogBuilder.setMessage("Quiz done! Your score is " + score);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("New Game",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}