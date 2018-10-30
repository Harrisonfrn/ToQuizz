package com.wedevtogether.topquiz.topquiz.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wedevtogether.topquiz.topquiz.R;
import com.wedevtogether.topquiz.topquiz.model.ItemRowScore;
import com.wedevtogether.topquiz.topquiz.model.ScoreData;
import com.wedevtogether.topquiz.topquiz.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.wedevtogether.topquiz.topquiz.model.ScoreData.mItemRowScores;
import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

    //Here we handle a date format to show it on the items
    Date now = new Date();
    SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
    String date = sfd.format(now);

    private TextView mGreetingText;
    private EditText mNameInput;
    private Button mPlayButton;
    private Button mLeadButton;
    private User mUser;

    public static final int GAME_ACTIVITY_ID = 12;
    public static final int SCORE_ACTIVITY_ID = 24;
    public static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";
    public static final String PREF_KEY_FIRSTNAME = "PREF_KEY_FIRSTNAME";

    private SharedPreferences mPreferences;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GAME_ACTIVITY_ID == requestCode && RESULT_OK == resultCode){
            // Fetch the score from the intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
            //Stock the score in shared preferences
            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();
            /*When a round of game is played in mainActivity we can see the score button*
            *And the methods greetUser() is used here just after the game result
            */
            mLeadButton.setVisibility(View.VISIBLE);
            greetUser();
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity::onCreate()");

        mUser = new User();

        mPreferences = getPreferences(MODE_PRIVATE);

        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mNameInput = (EditText) findViewById(R.id.activity_main_name_input);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);

        mLeadButton = (Button) findViewById(R.id.activity_main_lead_btn);
        
        mPlayButton.setEnabled(false);
        
        greetUser();


        mNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(s.toString().length() != 0);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The user clicked

                String firstname = mNameInput.getText().toString();
                mUser.setFirstName(firstname);

                //Stock the firstname (user text input) in the shared preferences
                mPreferences.edit().putString(PREF_KEY_FIRSTNAME, mUser.getFirstName()).apply();

                // The user just clicked
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_ID);

            }
        });

        /*
         * Button Leaderboard is not valid at start when none data is stored*/
        if (mItemRowScores.size() == 0)
            mLeadButton.setVisibility(View.GONE);

        //        This here where we handle the access to the Leaderboard view
        mLeadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // click

                Intent leadActivityIntent = new Intent(MainActivity.this, LeadActivity.class);
                startActivityForResult(leadActivityIntent, SCORE_ACTIVITY_ID);
            }
        });
    }

    private void scoreHandler(String name, int score) {
        //Here we avoid to put more than 5 items on the screen's device
        if (mItemRowScores.size() == 5)
            mItemRowScores.remove(0);

        //Here we had the new name and score of the game every time the user go back to the menu automatically
        mItemRowScores.add(new ItemRowScore(name, String.valueOf(score), date));
        //Here the data is saved (Look to Score data class in the model folder)
        ScoreData.saveData(this);
    }

    private void greetUser() {
        String firstname = mPreferences.getString(PREF_KEY_FIRSTNAME, null);

        if (null != firstname) {
            int score = mPreferences.getInt(PREF_KEY_SCORE, 0);

            String fulltext = "Bon retour, " + firstname
                    + "!\nTon dernier score est de  " + score + " pts "
                    + ", fera tu mieux cette fois ?";

            //This is where we can store both name and score after the game is over
            scoreHandler(firstname, score);

            mGreetingText.setText(fulltext);
            mNameInput.setText(firstname);
            mNameInput.setSelection(firstname.length());
            mPlayButton.setEnabled(true);
    }
}

    @Override
    protected void onStart() {
        super.onStart();

        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("MainActivity::onDestroy()");
    }
}