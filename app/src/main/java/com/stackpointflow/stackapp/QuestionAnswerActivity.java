package com.stackpointflow.stackapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionAnswerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelAnswer> answersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        answersList = new ArrayList<>();
        answersList.add(new ModelAnswer("1", "3", "1", "Dmitri", "well done", "2"));
        answersList.add(new ModelAnswer("1", "3", "1", "Dmitri", "well done", "5"));
        answersList.add(new ModelAnswer("1", "3", "1", "Dmitri", "well done", "3"));

        recyclerView = findViewById(R.id.rw_for_answers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager rvLayoutManager = layoutManager;

        recyclerView.setLayoutManager(rvLayoutManager);

        AnswersAdapter aa = new AnswersAdapter(this, answersList);
        recyclerView.setAdapter(aa);
    }
}
