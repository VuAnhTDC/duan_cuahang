package com.example.duancuahang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.duancuahang.Class.Shop;
import com.example.duancuahang.Class.ShopData;
import com.example.duancuahang.Class.ShowMessage;
import com.example.duancuahang.Class.Validates;
import com.example.duancuahang.FireBaseAuthenticator.FireBaseAuthenticator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    //Biến giao diện
    EditText edtSoDienThoai,edtMatKhau;
    CheckBox chkLuuTaiKhoan;
    Button btnDangNhap;
    TextView tvQuenMatKhau, tvDangKyTaiKhoan;
    View vLoginScreen;
    private Context context;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    NestedScrollView nestedScrollView_ScreenLogin;

    ProgressBar progressbar_Loading_ScreenLogin;

    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();
        setEvent();
        context = this;
        ShowMessage.context = this;
    }

    private void setEvent() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kiểm tra thông tin đăng nhập có bị bỏ trống không
                if (edtSoDienThoai.getText().toString().isEmpty() || edtMatKhau.getText().toString().isEmpty()) {
                    ShowMessage.showMessage("Thông tin đăng nhập không được bỏ trống. Vui lòng kiểm tra lại!!!");
                    return;
                }

                if (Validates.validPhone(edtSoDienThoai.getText().toString()) || Validates.validPassword(edtMatKhau.getText().toString())) {
                    // isLoading = true;
                    if (isLoading) {
                        hideKeyboard();
                        progressbar_Loading_ScreenLogin.setVisibility(View.VISIBLE);
                    }
                    // Kiểm tra xem user có tồn tại không
                    databaseReference = firebaseDatabase.getReference("Shop/"+edtSoDienThoai.getText().toString());
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean found = false;
                            if(snapshot.exists()){
                                ShopData shopData = snapshot.getValue(ShopData.class);
                                if(shopData.getPassword().equals(edtMatKhau.getText().toString())){
                                    if (shopData.getStatus() == 0) {
                                        ShowMessage.showMessage("Tài khoản đang chờ ADMIN duyệt đăng ký. Vui lòng chờ thông báo!");
                                    } else if (shopData.getStatus() == 2) {
                                        ShowMessage.showMessage("Tài khoản đã bị khóa.\nLiên hệ ADMIN để biết thêm chi tiết!!!\nCall ADMIN: 0867861024");
                                    } else if (shopData.getStatus() == 1) {
                                        // Đăng nhập thành công
                                        // Lưu thông tin của người dùng vào SharedPreferences
                                        SharedPreferences sharedPreferences1 = getSharedPreferences("InformationShop", Context.MODE_PRIVATE);
                                        Gson gson = new Gson();
                                        String json = gson.toJson(shopData);
                                        SharedPreferences.Editor editor = sharedPreferences1.edit();
                                        editor.putString("informationShop", json);
                                        editor.apply();
                                        Intent intent = new Intent(context, HomeShop.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    ShowMessage.showMessage("Sai mật khẩu. Vui lòng thử lại!!!");
                                }
                            } else {
                                ShowMessage.showMessage("Tài khoản không tồn tại!!!");
                            }
//                            //boolean found = false;
//                            for (DataSnapshot shopItem : snapshot.getChildren()) {
//                                String shopPhoneNumber = shopItem.child("shopPhoneNumber").getValue(String.class);
//                                if (shopPhoneNumber.equals(edtSoDienThoai.getText().toString())) {
//                                    found = true;
//                                    if (shopItem.child("password").getValue(String.class).equals(edtMatKhau.getText().toString())) {
//                                        if (Integer.parseInt(shopItem.child("status").getValue().toString()) == 0) {
//                                            ShowMessage.showMessage("Tài khoản đang chờ ADMIN duyệt đăng ký. Vui lòng chờ thông báo!");
//                                            return;
//                                        } else if (Integer.parseInt(shopItem.child("status").getValue().toString()) == 2) {
//                                            ShowMessage.showMessage("Tài khoản đã bị khóa.\nLiên hệ ADMIN để biết thêm chi tiết!!!\nCall ADMIN: 0867861024");
//                                            return;
//                                        } else if (Integer.parseInt(shopItem.child("status").getValue().toString()) == 1) {
//                                            // Đăng nhập thành công
//                                            ShopData shopData = shopItem.getValue(ShopData.class);
//                                            // Lưu thông tin của người dùng vào SharedPreferences
//                                            SharedPreferences sharedPreferences1 = getSharedPreferences("InformationShop", Context.MODE_PRIVATE);
//                                            Gson gson = new Gson();
//                                            String json = gson.toJson(shopData);
//                                            SharedPreferences.Editor editor = sharedPreferences1.edit();
//                                            editor.putString("informationShop", json);
//                                            editor.apply();
//                                            Intent intent = new Intent(context, HomeShop.class);
//                                            startActivity(intent);
//                                            finish();
//                                            return;
//                                        }
//                                    }
//                                }
//                            }
//                            if (!found) {
//                                ShowMessage.showMessage("Tài khoản không tồn tại!!!");
//                            } else {
//                                ShowMessage.showMessage("Sai mật khẩu. Vui lòng thử lại!!!");
//                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            ShowMessage.showMessage("Lỗi không truy vấn được dữ liệu: " + error);
                        }
                    });
                }
            }
        });

        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ForgotPasswordActivity.class);
                context.startActivity(intent);
            }
        });
        //Chuyển sang màn hình đăng ký
        tvDangKyTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RegistrationActivity.class);
                context.startActivity(intent);
            }
        });
        edtSoDienThoai.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (Validates.validPhone(edtSoDienThoai.getText().toString()) == false) {
                        edtSoDienThoai.selectAll();
                        edtSoDienThoai.requestFocus();
                        edtSoDienThoai.setError("Số điện thoại phải đủ 10 ký tự số và bắt đầu bằng số 0!");
                    } else {
                        edtMatKhau.requestFocus();
                    }
                }
                return false;
            }
        });
        edtMatKhau.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    if (Validates.validPassword(edtMatKhau.getText().toString()) == false) {
                        edtMatKhau.selectAll();
                        edtMatKhau.requestFocus();
                        edtMatKhau.setError("Mật khẩu không được bỏ trống!");
                    } else {
                        hideKeyboard();
                    }
                }
                return false;
            }
        });

        //Sự kiện ẩn bàn phím
        vLoginScreen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
    }

//    câm không cho người dùng thao tác
    private void setEnbaleComponentScreen(){
        edtMatKhau.setEnabled(false);
        edtSoDienThoai.setEnabled(false);
        btnDangNhap.setEnabled(false);
        nestedScrollView_ScreenLogin.setEnabled(false);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void setControl() {
        vLoginScreen = findViewById(R.id.vLoginScreen);
        edtSoDienThoai = findViewById(R.id.edtSoDienThoai);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        chkLuuTaiKhoan = findViewById(R.id.chkLuuTaiKhoan);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvQuenMatKhau = findViewById(R.id.tvQuenMatKhau);
        tvDangKyTaiKhoan = findViewById(R.id.tvDangKyTaiKhoan);
        nestedScrollView_ScreenLogin = findViewById(R.id.nestedScrollView_ScreenLogin);
        progressbar_Loading_ScreenLogin = findViewById(R.id.progressbar_Loading_ScreenLogin);
    }
}