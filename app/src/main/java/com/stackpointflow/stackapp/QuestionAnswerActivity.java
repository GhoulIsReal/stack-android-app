package com.stackpointflow.stackapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class QuestionAnswerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelAnswer> answersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        answersList = getIntent().getParcelableArrayListExtra("DATA_LIST");
        String title = getIntent().getStringExtra("Title");
        String textBody = getIntent().getStringExtra("Body");

        if(answersList.isEmpty()) {
            TextView noAnswersText = findViewById(R.id.no_answers_text);
            noAnswersText.setVisibility(View.VISIBLE);
        }

        TextView titleHolder = findViewById(R.id.answers_title);
        TextView bodyHolder = findViewById(R.id.answers_questionBody);

        titleHolder.setText(title);
        bodyHolder.setText(textBody);

        recyclerView = findViewById(R.id.rw_for_answers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLayoutManager = layoutManager;

        recyclerView.setLayoutManager(rvLayoutManager);

        AnswersAdapter aa = new AnswersAdapter(this, answersList);
        recyclerView.setAdapter(aa);

        Button backButton = findViewById(R.id.back_to_dashboard);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                finish();
            }
        });
    }


}
