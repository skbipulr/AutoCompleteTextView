package com.example.user.autocompletetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AutoText;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoTextView;
    String Url="https://api.myjson.com/bins/10ukwe";
    ArrayList<String> MyArraryList;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      autoTextView=findViewById(R.id.AutoTextView);
      MyArraryList=new ArrayList<>();
      adapter=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,MyArraryList);
        loadData();

    }

    private void loadData() {

        StringRequest stringRequest=new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("MyData");

                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject receive=jsonArray.getJSONObject(i);
                                MyArraryList.add(receive.getString("country"));
                            }

                           autoTextView.setThreshold(1);
                            autoTextView.setAdapter(adapter);
                        } catch (JSONException JEx) {
                            JEx.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

            }
        }
        );
        RequestQueue queue=Volley.newRequestQueue(this);
        queue.add(stringRequest);



    }
}
