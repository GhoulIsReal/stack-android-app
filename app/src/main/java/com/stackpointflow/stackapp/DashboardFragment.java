package com.stackpointflow.stackapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ModelQuestion> questionsList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        questionsList = new ArrayList<>();
//        questionsList.add(new ModelQuestion("1", "Title1", "Who knows...", "Matumba", "Jun 6"));

        new RequestAsync3().execute();

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

    public class RequestAsync3 extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                return RequestHandler.sendGet("http://10.0.103.133:3001/questions");
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null){
                //success
                JSONArray questionsList1 = null;
                try {
                    questionsList1 = new JSONArray(s);
                    for (int i = 0; i < questionsList1.length(); i++) {
                        JSONObject question = questionsList1.getJSONObject(i);
                        questionsList.add(new ModelQuestion(question.getString("question_primary_id"),
                                                            question.getString("title"),
                                                            question.getString("question_text"),
                                                            question.getString("username"),
                                                            question.getString("published_date")));
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //handle bad request
            }
        }


    }
}
