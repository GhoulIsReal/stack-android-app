package com.stackpointflow.stackapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    private String userID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final Button addQuestionButton = getView().findViewById(R.id.addQuestionButton);
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openAddQuestionActivity();
            }
        });

        TextView usernameWelcomeTextView = getView().findViewById(R.id.usernameHomeTextView);
        TextView pointsTextView = getView().findViewById(R.id.pointsHomeTextView);

        usernameWelcomeTextView.setText("Welcome, " + getArguments().getString("username"));
        pointsTextView.setText("Points: " + getArguments().getString("points"));
        userID = getArguments().getString("id");
    }

    public void openAddQuestionActivity() {
        Intent intent = new Intent(getContext(), AddQuestionActivity.class);
        intent.putExtra("USER_ID", userID);
        startActivity(intent);
    }
}
