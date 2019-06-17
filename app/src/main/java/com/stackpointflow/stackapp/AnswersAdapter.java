package com.stackpointflow.stackapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ModelAnswer> sList;
    private Integer tipButtonPositionHepler;
    AnswersAdapter(Context context, ArrayList<ModelAnswer> list) {
        mContext = context;
        sList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.rv_answer_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ModelAnswer answerItem = sList.get(i);

        viewHolder.username.setText(answerItem.getUsername());
        viewHolder.answer_body_text.setText(answerItem.getAnswerText());
        viewHolder.tips_count.setText("x" + answerItem.getTipsQuantity());
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView username, answer_body_text, tips_count;
        public ViewHolder(final View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.usernameInAnswer);
            answer_body_text = itemView.findViewById(R.id.questionBodyInAnswerCard);
            tips_count = itemView.findViewById(R.id.tipsCounter);

            Button tipButton = itemView.findViewById(R.id.buttonTip);

            tipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tipSource = BottomNavigationActivity.staticUserID;
                    String tipDestination = sList.get(getAdapterPosition()).getUserID();
                    String answerID = sList.get(getAdapterPosition()).getAnswerID();
                    tipButtonPositionHepler = getAdapterPosition();

                    new RequestAsync6().execute(tipSource, tipDestination, answerID);
                }
            });

        }
    }

    public class RequestAsync6 extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("tip_source", strings[0]);
                postDataParams.put("tip_destination", strings[1]);
                postDataParams.put("answer_id", strings[2]);


                return RequestHandler.sendPost("http://192.168.0.195:3001/tip",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {

            if(s!=null){
                //success
                try {
                    JSONArray ja = new JSONArray(s);
                    sList.get(tipButtonPositionHepler).setTipsQuantity((ja.getString(0)));
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Success Tip", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //handle bad request
                Toast.makeText(mContext, "You can't tip yourself, bastard!", Toast.LENGTH_LONG).show();
            }
        }


    }
}
