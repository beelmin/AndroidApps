package com.example.mychatapp;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    private final String URL = "https://android-chatapp.herokuapp.com/";
    private final String URL2 = "https://android-chatapp.herokuapp.com/chat";
    private RequestQueue requestQueue;

    private String currentUser;
    private String otherUser;
    private int indexCurrentUser;
    private int indexOtherUser;
    private String collectionName;

    private Button send;
    private Button back;
    private Button logout;
    private EditText et1;
    private LinearLayout linearLayout;



    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.info);
        et1 = (EditText) view.findViewById(R.id.mess);

        currentUser = getArguments().getString("current_user");
        otherUser = getArguments().getString("other_user");
        indexCurrentUser = 0;
        indexOtherUser = 0;

        getUsersIndexes();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                handler.postDelayed(this, 300);
            }
        }, 300);

        send = (Button) view.findViewById(R.id.button1);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cal = Calendar.getInstance();
                Date date=cal.getTime();
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                dateFormat.setTimeZone(TimeZone.getDefault());
                String formattedDate=dateFormat.format(date);


                String data = "{"+
                            "\"sender\"" + ":\"" + currentUser + "\","+
                            "\"message\"" + ":\"" + et1.getText().toString() + "\","+
                            "\"saveMess\"" + ":\"" + "true" + "\","+
                            "\"collectionName\"" + ":\"" + collectionName + "\","+
                            "\"time\"" + ":\"" + formattedDate + "\""+
                            "}";
                sendData(data);
                et1.setText("");

            }
        });

        back = (Button) view.findViewById(R.id.button2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UsersList.class);
                intent.putExtra("current_user",currentUser);
                startActivity(intent);

            }
        });

        logout = (Button) view.findViewById(R.id.button3);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();
            }
        });

        return view;
    }

    private void logOutUser(){
        final String savedata = "{"+
                "\"username\"" + ":\"" + currentUser + "\","+
                "\"logout\"" + ":\"" + "true" + "\"}";

        requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    String result = objres.getString("success");
                    if(result.equals("true")){
                        Toast.makeText(getActivity(),"Uspjesno ste se odjavili",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {

                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);

    }


    private void getUsersIndexes(){

        requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{

                    for(int i=0;i<response.length();i++){

                        JSONObject obj = response.getJSONObject(i);

                        String username = obj.getString("username");
                        if(username.equals(currentUser)){
                            indexCurrentUser = Integer.parseInt(obj.getString("index"));
                        }

                        if(username.equals(otherUser)){
                            indexOtherUser = Integer.parseInt(obj.getString("index"));;
                        }

                    }
                    cantorPairingFunction(indexCurrentUser,indexOtherUser);

                }catch (JSONException e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        requestQueue.add(request);

    }

    private void cantorPairingFunction(int index1, int index2){

        if(index1 >index2){
            int temp = index2;
            index2 = index1;
            index1 = temp;
        }

        int x = index1 + index2;
        int y = (x * (x+1))/2;
        int result = y + index2;
        collectionName = "kolekcija" + result;


    }

    private void sendData(String data){
        final String savedata= data;

        requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    //Toast.makeText(getActivity(),objres.toString(),Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {

                    return null;
                }
            }

        };
        requestQueue.add(stringRequest);
    }


    private void getData(){

        final String savedata = "{"+
                "\"collectionName\"" + ":\"" + collectionName + "\","+
                "\"getMess\"" + ":\"" + "true"+ "\""+ "}";

        String URL3 = "https://android-chatapp.herokuapp.com/chat?kolekcija=" + collectionName;

        requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest stringRequest = new JsonArrayRequest(URL3, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {

                    linearLayout.removeAllViews();

                    for(int i=0;i<response.length();i++){

                        JSONObject obj = response.getJSONObject(i);

                        String user = obj.getString("sender");
                        String message = obj.getString("message");
                        String time = obj.getString("time");

                        String text = time + " " + user + " : " + message;

                        if(user.equals(currentUser)) {
                            newTextView(text,"right");
                        }else{
                            newTextView(text,"left");
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);

        }

    private void newTextView(String text, String where) {


        LinearLayout ll = new LinearLayout(getActivity());
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        ll.setOrientation(LinearLayout.HORIZONTAL);
        if(where.equals("right")) {
            ll.setGravity(Gravity.RIGHT);
        }else{
            ll.setGravity(Gravity.LEFT);
        }



        TextView tv = new TextView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,7);
        tv.setLayoutParams(params);
        tv.setPadding(5,5,5,5);


        int[] colors_right = {getResources().getColor(R.color.green),getResources().getColor(R.color.green)};
        int[] colors_left = {getResources().getColor(R.color.grey),getResources().getColor(R.color.grey)};

        GradientDrawable gd;

        if(where.equals("right")){
            gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors_right);
            gd.setStroke(2,getResources().getColor(R.color.green));
        }else{
            gd = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors_left);
            gd.setStroke(2,getResources().getColor(R.color.grey));
        }

        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(10.0f);

        tv.setBackground(gd);
        tv.setText(text);
        tv.setTextColor(Color.WHITE);

        ll.addView(tv);
        this.linearLayout.addView(ll);

    }


}
