package com.example.duancuahang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duancuahang.Class.Category;
import com.example.duancuahang.Class.Manuface;
import com.example.duancuahang.Class.ProductData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Detailproduct extends AppCompatActivity {
    Toolbar toolbar;

    TextView tvNameProduct_DetailProduct, tvCategory_DetailProduct, tvManufacce_DetailProduct, tvDescription_DetailProduct, tvPriveProduct_DetailProdcut, tvQuanlityProduct_DetailProduct;

    ImageView ivImageProduct_DetailProduct;

    private Category categoryProduct;
    private Manuface manufaceProduct;
    private ProductData productData = new ProductData();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailproduct);
        setControl();
        setIniazation();
        pushInformationProductToBackground();
        setEvent();
    }

    //    hàm khởi tạo
    private void setIniazation() {
        Intent intent = getIntent();
        productData = (ProductData) intent.getSerializableExtra("informationProduct_Detail");
        System.out.println("Detaikl product: " + productData.toString());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //    hàm ánh xạ
    private void setControl() {
        toolbar = findViewById(R.id.toolBar_DetalProduct);
        ivImageProduct_DetailProduct = findViewById(R.id.ivImageProduct_DetailProduct);
        tvNameProduct_DetailProduct = findViewById(R.id.tvNameProduct_DetailProduct);
        tvCategory_DetailProduct = findViewById(R.id.tvCategory_DetailProduct);
        tvManufacce_DetailProduct = findViewById(R.id.tvManufacce_DetailProduct);
        tvDescription_DetailProduct = findViewById(R.id.tvDescription_DetailProduct);
        tvPriveProduct_DetailProdcut = findViewById(R.id.tvPriveProduct_DetailProdcut);
        tvQuanlityProduct_DetailProduct = findViewById(R.id.tvQuanlityProduct_DetailProduct);
    }

//ham đưa giá trị lên giao diện
    private void pushInformationProductToBackground(){
        setInformationProduct();
        setCategory(productData.getKeyCategoryProduct());
        setImageProduct(productData.getUrlImageProduct());
        setManuface(productData.getKeyManufaceProduct());
    }

    //    hàm đưa thông tin sản phẩm lên textView
    private void setInformationProduct() {
        tvNameProduct_DetailProduct.setText(productData.getNameProduct());
        tvDescription_DetailProduct.setText(productData.getDescriptionProduct());
        tvPriveProduct_DetailProdcut.setText(String.valueOf(productData.getPriceProduct()) + "VND");
        tvQuanlityProduct_DetailProduct.setText(String.valueOf(productData.getQuanlityProduct()));
    }

    private void setImageProduct(String url){
        Picasso.get().load(url).into(ivImageProduct_DetailProduct);
    }

    //    lấy Category dựa vào id
    private void setCategory(String idCategory) {
        databaseReference = firebaseDatabase.getReference("Category");
        Query query = databaseReference.orderByChild("idCategory").equalTo(idCategory);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    String idCategory = categorySnapshot.child("idCategory").getValue().toString();
                    String nameCategory = categorySnapshot.child("nameCategory").getValue().toString();
                    categoryProduct = new Category(idCategory, nameCategory);
                    tvCategory_DetailProduct.setText(categoryProduct.getNameCategory());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //    hàm lấy manuface ra
    private void setManuface(String idManuface) {
        databaseReference = firebaseDatabase.getReference("Manuface");
        Query query = databaseReference.orderByChild("idManuface").equalTo(idManuface);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
                    String idManuface1 = categorySnapshot.child("idManuface").getValue().toString();
                    String nameManuface = categorySnapshot.child("nameManuface").getValue().toString();
                    String idCategoryt_manuface = categorySnapshot.child("keyManuface_Category").getValue().toString();
                    manufaceProduct = new Manuface(idManuface1, idManuface1, nameManuface);
                    tvManufacce_DetailProduct.setText(manufaceProduct.getNameManuface());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //    hàm bắt sự kiện nhấn vào nút back của toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //    hàm bắt sự kiện
    private void setEvent() {

    }
}