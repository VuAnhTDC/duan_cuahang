package com.example.duancuahang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duancuahang.Class.ShopData;
import com.example.duancuahang.RecyclerView.ProductListViewPagerAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class InformationAccountActivity extends AppCompatActivity {

    Toolbar toolBar_Information;
    TextView tvChangePassword, tvShopOwner, tvShopPhoneNumber, tvShopName, tvShopEmail, tvShopAddress, tvLogOut;
    ImageView imgAvartar;
    private ShopData shopData = new ShopData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_account);
        SharedPreferences sharedPreferences1 = getSharedPreferences("InformationShop", Context.MODE_PRIVATE);
        String jsonShop = sharedPreferences1.getString("informationShop","");
        Gson gson = new Gson();
        shopData = gson.fromJson(jsonShop, ShopData.class);
        setControl();
        setIntiazation();
        setEvent();
    }

    private void setEvent() {
        //Sự kiện đăng xuất tài khoản
        tvLogOut.setOnClickListener(v -> {

        });
        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationAccountActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        toolBar_Information = findViewById(R.id.toolBar_Information);
        tvChangePassword = findViewById(R.id.tvChangePassword);
        tvShopOwner = findViewById(R.id.tvShopOwner);
        tvShopPhoneNumber = findViewById(R.id.tvShopPhoneNumber);
        tvShopName = findViewById(R.id.tvShopName);
        tvShopEmail = findViewById(R.id.tvShopEmail);
        tvShopAddress = findViewById(R.id.tvShopAddress);
        imgAvartar = findViewById(R.id.imgAvartar);
        tvLogOut = findViewById(R.id.tvLogOut);
    }

    private void setIntiazation() {
        tvShopOwner.setText(shopData.getShopOwner());
        tvShopPhoneNumber.setText(shopData.getShopPhoneNumber());
        tvShopName.setText(shopData.getShopName());
        tvShopEmail.setText(shopData.getShopEmail());
        tvShopAddress.setText(shopData.getShopAddress());

        if (shopData.getUrlImgShopAvatar().isEmpty()){
            imgAvartar.setImageResource(R.drawable.iconshop);
        }
        else {
            Picasso.get().load(shopData.getUrlImgShopAvatar()).into(imgAvartar);
        }
//        ---------------- kích hoạt button back của toolbar
        setSupportActionBar(toolBar_Information);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //set menu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editinfor,menu);
        return super.onPrepareOptionsMenu(menu);
    }
    //Add menu vào Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    //Event click back  button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        } else if (item.getItemId() == R.id.action_editinfor){
            Toast.makeText(this, "Chuyển sang màn hình Edit Infor", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}