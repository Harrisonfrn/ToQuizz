package com.wedevtogether.topquiz.topquiz.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.wedevtogether.topquiz.topquiz.R;
import com.wedevtogether.topquiz.topquiz.model.ItemRowScore;
import com.wedevtogether.topquiz.topquiz.model.ScoreData;
import com.wedevtogether.topquiz.topquiz.view.ScoreAdapter;

import java.util.Collections;
import java.util.Comparator;

import static com.wedevtogether.topquiz.topquiz.model.ScoreData.mItemRowScores;

public class LeadActivity extends AppCompatActivity {

    public static Comparator<ItemRowScore> ComparatorNom = new Comparator<ItemRowScore>() {
        @Override
        public int compare(ItemRowScore itemRowScore, ItemRowScore t1) {
            return itemRowScore.getPseudo().compareTo(t1.getPseudo());
        }
    };

    public static Comparator<ItemRowScore> ComparatorScore = new Comparator<ItemRowScore>() {
        @Override
        public int compare(ItemRowScore itemRowScore, ItemRowScore t1) {
            return t1.getScore().compareTo(itemRowScore.getScore());
        }
    };

    private Button mAlphabet;
    private Button mTopScore;
    private Button mDelete;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        mListView = (ListView) findViewById(R.id.list_view);
        mAlphabet = (Button) findViewById(R.id.activity_lead_alphabet_btn);
        mTopScore = (Button) findViewById(R.id.activity_lead_topscore_btn);
        mDelete = (Button) findViewById(R.id.activity_lead_delete_btn);


        ScoreData.loadData(this);
        final ScoreAdapter adapter = new ScoreAdapter(this, mItemRowScores);
        mListView.setAdapter(adapter);


        mAlphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sort by name
                Collections.sort(mItemRowScores, ComparatorNom);
                adapter.notifyDataSetChanged();
            }
        });

        mTopScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sort by score
                Collections.sort(mItemRowScores, ComparatorScore);
                adapter.notifyDataSetChanged();
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScoreData.clearData(LeadActivity.this);
                adapter.notifyDataSetChanged();
            }
        });

    }

}
