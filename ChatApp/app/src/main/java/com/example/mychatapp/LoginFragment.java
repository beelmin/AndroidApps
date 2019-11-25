package com.example.mychatapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private final String URL = "https://android-chatapp.herokuapp.com/";
    private RequestQueue requestQueue;
    private String currentUser;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final EditText input = (EditText) view.findViewById(R.id.input);
        final EditText pass = (EditText) view.findViewById(R.id.pass);
        Button submit = (Button) view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentUser = input.getText().toString();

                String data = "{"+
                        "\"username\"" + ":\"" + input.getText().toString() + "\","+
                        "\"password\"" + ":\"" + pass.getText().toString() + "\","+
                        "\"authentication\"" + ":\"" + "true" + "\"}";

                //Toast.makeText(getActivity(),data,Toast.LENGTH_LONG).show();
                checkIfUserExist(data);

            }
        });

        return view;
    }

    private void checkIfUserExist(String data){

        final String savedata= data;

        requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres = new JSONObject(response);
                    String result = objres.getString("success");
                    if(result.equals("true")){
                        Toast.makeText(getActivity(),"Uspjesno ste se logovali",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),UsersList.class);
                        intent.putExtra("current_user",currentUser);
                        startActivity(intent);

                    }else{
                        Toast.makeText(getActivity(),"Pogresan username ili password",Toast.LENGTH_SHORT).show();
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

}
