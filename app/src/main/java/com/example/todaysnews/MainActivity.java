package com.example.todaysnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter myadapter;
    ArrayList<news> newsobject;
    public  Context context;
    Spinner spinnercities;
    Spinner spinnercatag;
    String choosencity;
    String choosenCategory;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinnercities = findViewById(R.id.spinnerCitys);
        ArrayAdapter citiesadapter = ArrayAdapter.createFromResource(this,R.array.cities_array,R.layout.support_simple_spinner_dropdown_item);
        citiesadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercities.setAdapter(citiesadapter);

        spinnercities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                switch (selectedItem){
                    case "India":Toast.makeText(MainActivity.this,"India Selected",Toast.LENGTH_SHORT).show();choosencity = "in";break;
                    case "France":Toast.makeText(MainActivity.this,"France Selected",Toast.LENGTH_SHORT).show();choosencity = "fr";break;
                    case "United Kingdom":Toast.makeText(MainActivity.this,"United Kingdom Selected",Toast.LENGTH_SHORT).show();choosencity = "gb";break;
                    case "Russia":Toast.makeText(MainActivity.this,"Russia Selected",Toast.LENGTH_SHORT).show();choosencity = "ru";break;
                    case "Australia":Toast.makeText(MainActivity.this,"Australia Selected",Toast.LENGTH_SHORT).show();choosencity = "au";break;
                    case "United States":Toast.makeText(MainActivity.this,"United States Selected",Toast.LENGTH_SHORT).show();choosencity = "us";break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnercatag = findViewById(R.id.spinnerCatag);
        ArrayAdapter catagadapter = ArrayAdapter.createFromResource(this,R.array.categories_array,R.layout.support_simple_spinner_dropdown_item);
        catagadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnercatag.setAdapter(catagadapter);

        spinnercatag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedcategory = adapterView.getItemAtPosition(i).toString();
                switch (selectedcategory){
                    case "Technology":Toast.makeText(MainActivity.this,"Technology Selected",Toast.LENGTH_SHORT).show();choosenCategory = "technology";break;
                    case "Entertainment":Toast.makeText(MainActivity.this,"Entertainment Selected",Toast.LENGTH_SHORT).show();choosenCategory = "entertainment";break;
                    case "Business":Toast.makeText(MainActivity.this,"Business Selected",Toast.LENGTH_SHORT).show();choosenCategory = "business";break;
                    case "Health":Toast.makeText(MainActivity.this,"Health Selected",Toast.LENGTH_SHORT).show();choosenCategory = "health";break;
                    case "Science":Toast.makeText(MainActivity.this,"Science Selected",Toast.LENGTH_SHORT).show();choosenCategory = "science";break;
                    case "Sports":Toast.makeText(MainActivity.this,"Sports Selected",Toast.LENGTH_SHORT).show();choosenCategory = "sports";break;
                    case "General":Toast.makeText(MainActivity.this,"General Selected",Toast.LENGTH_SHORT).show();choosenCategory = "general";break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button = findViewById(R.id.Showbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsobject.clear();;
                fetchdata(choosencity,choosenCategory);
            }
        });



        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplication(),DividerItemDecoration.VERTICAL));

        newsobject = new ArrayList<news>();
        context = getApplicationContext();



    }


    void fetchdata(String choosencity,String choosenCategory) {


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://saurav.tech/NewsAPI/top-headlines/category/"+choosenCategory+"/"+choosencity+".json";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");
                    int totalresult = jsonObject.getInt("totalResults");
                    Toast.makeText(MainActivity.this, "Status :" + status + "\n" + "Total Resutls :" + totalresult, Toast.LENGTH_SHORT).show();

                    JSONArray articlearray = jsonObject.getJSONArray("articles");
                    for (int i = 0; i < articlearray.length(); i++) {
                        JSONObject jsonObject1 = articlearray.getJSONObject(i);
                        String Title = jsonObject1.getString("title");
                        String dis = jsonObject1.getString("description");
                        String imgurl = jsonObject1.getString("urlToImage");
                        String newsurl = jsonObject1.getString("url");
                        news obj = new news(Title,dis,imgurl,newsurl);
                        newsobject.add(obj);
                    }
                    recyclerView.setAdapter(new Adapter(newsobject,context));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);



    }



}
