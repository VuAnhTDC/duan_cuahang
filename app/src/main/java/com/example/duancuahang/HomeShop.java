package com.example.duancuahang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duancuahang.Class.ShopData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.duancuahang.Fragment.OrderWaitForTakeGoodsFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class HomeShop extends AppCompatActivity {

//    khai báo biến giao diện
    Toolbar toolbar;
    LinearLayout linearLayout_SanPhamCuaToi_ScreenHome;
    View linearLayout_ViewRating, linearLayout_OrderCancelled, linearLayout_WaitTakeGoods;
    TextView tvNameShop_ScreenHome, tvBillHistory;
    ImageView ivAvataShop_ScreenHome;
    Context context;
   // CallbackManager callbackManager;
    private ShopData shopData = new ShopData();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

//    cmt
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_shop);
        //callbackManager = CallbackManager.Factory.create();
        SharedPreferences sharedPreferences = getSharedPreferences("InformationShop", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (sharedPreferences.contains("informationShop")) {
           String jsonShop = sharedPreferences.getString("informationShop", "");
           Gson gson = new Gson();
            shopData = gson.fromJson(jsonShop, ShopData.class);
        } else {
            // Dữ liệu không tồn tại, có thể là người dùng đã đăng xuất hoặc lần đầu sử dụng ứng dụng
        }


//        if (isLoggedIn) {
//            setContentView(R.layout.activity_home_shop);
//        } else {
//            Intent intent = new Intent(HomeShop.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        System.out.println("Shopdata Share: " + shopData.toString());


        context = this;
        setControl();
        setIntiazation();
        setEvent();
        setSupportActionBar(toolbar);

    }

    private void setIntiazation() {
        tvNameShop_ScreenHome.setText(shopData.getShopName());
        if (shopData.getUrlImgShopAvatar().isEmpty()) {
            ivAvataShop_ScreenHome.setImageResource(R.drawable.iconshop);
        } else {
            Picasso.get().load(shopData.getUrlImgShopAvatar()).into(ivAvataShop_ScreenHome);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    //    Xu ly su kien
    private void setEvent() {
        linearLayout_SanPhamCuaToi_ScreenHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Productlist.class);
                startActivity(intent);
            }
        });
        tvBillHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeShop.this, OrderListActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itMessage_Actionbar){
            Intent intent = new Intent(context, MessageActivity.class);
            intent.putExtra("idUser","0372907720");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    //    Anh xa
    private void setControl() {
        toolbar = findViewById(R.id.toolBar_ScreenHome);
        linearLayout_SanPhamCuaToi_ScreenHome = findViewById(R.id.linearLayout_SanPhamCuaToi_ScreenHome);
        tvNameShop_ScreenHome = findViewById(R.id.tvNameShop_ScreenHome);
        tvBillHistory = findViewById(R.id.tvBillHistory);
        ivAvataShop_ScreenHome = findViewById(R.id.ivAvataShop_ScreenHome);
        linearLayout_WaitTakeGoods = findViewById(R.id.linearLayout_WaitTakeGoods);
        linearLayout_OrderCancelled = findViewById(R.id.linearLayout_OrderCancelled);
        linearLayout_ViewRating = findViewById(R.id.linearLayout_ViewRating);
    }

    //    Thêm item và ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}