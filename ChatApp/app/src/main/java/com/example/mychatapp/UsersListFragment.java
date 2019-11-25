package com.example.mychatapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersListFragment extends Fragment {

    private final String URL = "https://android-chatapp.herokuapp.com/";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;
    RequestQueue requestQueue;

    private String currentUser;
    private Button logout;


    public UsersListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        listItems = new ArrayList<>();

        currentUser = getArguments().getString("current_user").toString();

        loadRecyclerViewData();

        logout = (Button) view.findViewById(R.id.button);
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


    private void loadRecyclerViewData() {

        requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{

                    for(int i=0;i<response.length();i++){

                        JSONObject obj = response.getJSONObject(i);

                        String user = obj.getString("username");
                        String active = "online: " + obj.getString("online");

                        ListItem item = new ListItem(user,currentUser,active);
                        listItems.add(item);

                    }

                    adapter = new MyAdapter(listItems,getActivity());
                    recyclerView.setAdapter(adapter);

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

}
