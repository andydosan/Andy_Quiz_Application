package com.example.quizapplication;

public class Question {

    public static String mQuestions[] = new String[10];

    public static String mChoices[][] = new String[10][4];

    public static String mCorrectAnswers[] = new String[10];

    /*
    public String mQuestions[] = {
            "x = 5 + 3",
            "x^2 = 81",
            "x = 61 + 2 * 4",
            "x = 2^3",
            "x = 1 + 1",
            "5^x = 125",
            "x = 1 + 0",
            "x = 3 - 4 * 2",
            "2x = 4",
            "x = 9 + 1 / 10"
    };

    private String mChoices[][] = {
            {"x = 5", "x = 53", "x = 8", "x = 15"},
            {"x = 3", "x = 27", "x = 6", "x = 9"},
            {"x = 252", "x = 0", "x = -1", "x = 69"},
            {"x = 2", "x = 4", "x = 6", "x = 8"},
            {"x = 1", "x = 11", "x = 2", "x = 22"},
            {"x = 2", "x = 3", "x = 4", "x = 5"},
            {"x = 1", "x = 0", "x = -3", "x = -1"},
            {"x = 2", "x = 5", "x = -5", "x = -2"},
            {"x = 1/2", "x = 2", "x = 4", "x = 4/x"},
            {"x = 1", "x = 10", "x = 9.1", "x = 9"}
    };

    private String mCorrectAnswers[] = {
            "x = 8",
            "x = 9",
            "x = 69",
            "x = 8",
            "x = 2",
            "x = 3",
            "x = 1",
            "x = -5",
            "x = 2",
            "x = 9.1"
    };

     */

    public String getQuestions(int a) {
        String question = mQuestions[a];
        return question;
    }

    public String getChoice1(int a) {
        String choice = mChoices[a][0];
        return choice;
    };

    public String getChoice2(int a) {
        String choice = mChoices[a][1];
        return choice;
    };

    public String getChoice3(int a) {
        String choice = mChoices[a][2];
        return choice;
    };

    public String getChoice4(int a) {
        String choice = mChoices[a][3];
        return choice;
    };

    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers[a];
        return answer;
    };
}
