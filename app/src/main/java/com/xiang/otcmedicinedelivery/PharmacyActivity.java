package com.xiang.otcmedicinedelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import com.xiang.otcmedicinedelivery.adapters.PharmacyListAdapter;
import com.xiang.otcmedicinedelivery.model.PharmacyModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

public class PharmacyActivity extends AppCompatActivity implements PharmacyListAdapter.RestaurantListClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pharmacy List");

        List<PharmacyModel> pharmacyModelList =  getPharmacyData();

        initRecyclerView(pharmacyModelList);
    }

    private void initRecyclerView(List<PharmacyModel> pharmacyModelList) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PharmacyListAdapter adapter = new PharmacyListAdapter(pharmacyModelList, this);
        recyclerView.setAdapter(adapter);
    }

    private List<PharmacyModel> getPharmacyData() {
        //TODO: use firebase to retrive data
        InputStream is = getResources().openRawResource(R.raw.restaurent);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try{
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while(( n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0,n);
            }
        }catch (Exception e) {

        }

        String jsonStr = writer.toString();
        Gson gson = new Gson();
        PharmacyModel[] pharmacyModels =  gson.fromJson(jsonStr, PharmacyModel[].class);
        List<PharmacyModel> restList = Arrays.asList(pharmacyModels);

        return  restList;

    }

    @Override
    public void onItemClick(PharmacyModel pharmacyModel) {
        Intent intent = new Intent(PharmacyActivity.this, MenuListActivity.class);
        intent.putExtra("PharmacyModel", pharmacyModel);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Home:
                Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Profile:
                Toast.makeText(this, "Profile selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Order:
                Toast.makeText(this, "Order selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

