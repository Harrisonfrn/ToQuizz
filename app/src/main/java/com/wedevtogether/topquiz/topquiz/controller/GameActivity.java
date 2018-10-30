package com.wedevtogether.topquiz.topquiz.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wedevtogether.topquiz.topquiz.R;
import com.wedevtogether.topquiz.topquiz.model.Question;
import com.wedevtogether.topquiz.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestionTextView;
    private Button mAnswerButton1;
    private Button mAnswerButton2;
    private Button mAnswerButton3;
    private Button mAnswerButton4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    private boolean mEnableTouchEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate()");

        mQuestionBank = this.generateQuestions();

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNumberOfQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        } else {
            mScore = 0;
            mNumberOfQuestions = 4;
        }

        mEnableTouchEvents = true;

        // Wire widgets
        mQuestionTextView = (TextView) findViewById(R.id.activity_game_question_text);
        mAnswerButton1 = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswerButton2 = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswerButton3 = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswerButton4 = (Button) findViewById(R.id.activity_game_answer4_btn);

        // Use the tag property to 'name' the buttons
        mAnswerButton1.setTag(0);
        mAnswerButton2.setTag(1);
        mAnswerButton3.setTag(2);
        mAnswerButton4.setTag(3);

        mAnswerButton1.setOnClickListener(this);
        mAnswerButton2.setOnClickListener(this);
        mAnswerButton3.setOnClickListener(this);
        mAnswerButton4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNumberOfQuestions);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            // Good answer
            Toast.makeText(this, "Vrai ", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            // Wrong answer
            Toast.makeText(this, "Faux!", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;

                // If this is the last question, ends the game.
                // Else, display the next question.
                if (--mNumberOfQuestions == 0) {
                    // End the game
                    endGame();
                } else {
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000); // LENGTH_SHORT is usually 2 second long
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bien jouer!")
                .setMessage("Ton score est de " + mScore + " pts ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // End the activity
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        mAnswerButton1.setText(question.getChoiceList().get(0));
        mAnswerButton2.setText(question.getChoiceList().get(1));
        mAnswerButton3.setText(question.getChoiceList().get(2));
        mAnswerButton4.setText(question.getChoiceList().get(3));
    }

    private QuestionBank generateQuestions() {
        Question question1 = new Question("Qui est l'ancien président Français ?",
                Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"),
                1);

        Question question2 = new Question("Combien de pays dans l'UE?",
                Arrays.asList("15", "24", "28", "32"),
                2);

        Question question3 = new Question("Qui est le createur d'android?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question4 = new Question("Premier Homme sur la lune ?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question5 = new Question("Quels est la capital de la Roumanie?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6 = new Question("Qui a paint Mona Lisa?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("Ou est enterré F.Chopin ?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("Quels est le nom de domaine Belge?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("Quels est le numéro de maison des Simpson?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        Question question10 = new Question("Qui était le dieu de la guerre dans la mythologie grecque ?",
                Arrays.asList("Hermès", "Arès", "Hadès", "Apollon"),
        1);

        Question question11 = new Question("Quel est la danse officielle du Brésil ?",
                Arrays.asList("La samba", "Le tango", "Le chacha", "La valse"),
                0);

        Question question12 = new Question("Qui est l'actuel président de la France",
                Arrays.asList("Hollande", "Sarkozy", "Chirac", "Macron"),
                3);

        Question question13 = new Question("Quel était le nom du petit dragon dans le film Mulan ?",
                Arrays.asList("Honshu", "Kyushu", "Mushu", "Royco"),
                2);

        Question question14 = new Question("Quelle est le nom de l'actrice principale d'Alien ?",
                Arrays.asList("Sigourney Weaver", "Sigourney Beaver", "Sigourney Meaver", "Sigourney Ueaver"),
                0);

        Question question15 = new Question("Quelle était la profession de Popeye ?",
                Arrays.asList("Militaire", "Cuisinier", "Marin", "Pêcheur"),
                2);

        Question question16 = new Question("A quel écrivain doit-on le personnage de Boule-de-Suif ?",
                Arrays.asList("Charles Baudelaires", "Guy de Maupassant", "Alexandre Dumas", "Albert Camus"),
                1);

        Question question17 = new Question("De quel pays Tirana est-elle la capitale ?",
                Arrays.asList("L' Ouzbékistan", "La Biélorussie", "L' Albanie", "Le Kirghistan"),
                2);

        Question question18 = new Question("De quel groupe Jim Morrison était-il le chanteur ?",
                Arrays.asList("The Cardigans", "The Beattles", "The Who", "The Doors"),
                3);

        Question question19 = new Question("Dans quelle ville française se trouve la Cité de l'espace ?",
                Arrays.asList("Poithier", "Toulouse", "Lyon", "Nantes"),
                1);

        Question question20 = new Question("En Inde, quels individus \"hors caste\" sont considérés comme impurs ?",
                Arrays.asList("Les puants", "Les parias", "Les sales", "Les intouchables"),
                3);

        Question question21 = new Question("Que tient la statue de la Liberté dans sa main droite ?",
                Arrays.asList("Un livre", "Un sac", "Un flambeau", "Un étendard"),
                2);



        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9,
                question10,
                question11,
                question12,
                question13,
                question14,
                question15,
                question16,
                question17,
                question18,
                question19,
                question20,
                question21));
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("GameActivity::onDestroy()");
    }
}
