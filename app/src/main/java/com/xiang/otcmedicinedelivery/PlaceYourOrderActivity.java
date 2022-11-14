package com.xiang.otcmedicinedelivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiang.otcmedicinedelivery.adapters.PlaceYourOrderAdapter;
import com.xiang.otcmedicinedelivery.model.Menu;
import com.xiang.otcmedicinedelivery.model.PharmacyModel;

public class PlaceYourOrderActivity extends AppCompatActivity {

    private EditText inputName, inputAddress, inputCardNumber;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvTotalAmount, buttonPlaceYourOrder;
    private PlaceYourOrderAdapter placeYourOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        PharmacyModel PharmacyModel = getIntent().getParcelableExtra("PharmacyModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(PharmacyModel.getName());
        actionBar.setSubtitle(PharmacyModel.getAddress());
        actionBar.setDisplayHomeAsUpEnabled(true);

        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);


        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(PharmacyModel);
            }
        });


        initRecyclerView(PharmacyModel);
        calculateTotalAmount(PharmacyModel);
    }

    private void calculateTotalAmount(PharmacyModel PharmacyModel) {
        float TotalAmount = 0f;
        for(Menu m : PharmacyModel.getMenus()) {
            TotalAmount += m.getPrice() * m.getTotalInCart();
        }
        tvTotalAmount.setText("$"+String.format("%.2f", TotalAmount));
    }

    private void onPlaceOrderButtonClick(PharmacyModel PharmacyModel) {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if(TextUtils.isEmpty(inputAddress.getText().toString())) {
            inputAddress.setError("Please enter address ");
            return;
        }else if( TextUtils.isEmpty(inputCardNumber.getText().toString())) {
            inputCardNumber.setError("Please enter card number ");
            return;
        }
        //start success activity..
        Intent i = new Intent(PlaceYourOrderActivity.this, OrderSucceessActivity.class);
        i.putExtra("PharmacyModel", PharmacyModel);
        startActivityForResult(i, 1000);
    }

    private void initRecyclerView(PharmacyModel PharmacyModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(PharmacyModel.getMenus());
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}