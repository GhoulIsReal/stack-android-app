package com.stackpointflow.stackapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ModelQuestion> questionsList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        questionsList = new ArrayList<>();
        questionsList.add(new ModelQuestion("Title1", "Who knows...", "Matumba", "Jun 6"));
        questionsList.add(new ModelQuestion("Title1", "Who knows...", "Matumba", "Jun 6"));
        questionsList.add(new ModelQuestion("Title1", "Who knows...", "Matumba", "Jun 6"));
        questionsList.add(new ModelQuestion("Title1", "Who knows...", "Matumba", "Jun 6"));
        questionsList.add(new ModelQuestion("Title1", "Who knows...", "Matumba", "Jun 6"));
        questionsList.add(new ModelQuestion("Title1", "Who knows...", "Matumba", "Jun 6"));

        return inflater.inflate(R.layout.fragment_dashboard, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView = getView().findViewById(R.id.rw);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager rvLayoutManager = layoutManager;

        recyclerView.setLayoutManager(rvLayoutManager);

        QuestionAdapter qa = new QuestionAdapter(getContext(), questionsList);
        recyclerView.setAdapter(qa);
    }
}
