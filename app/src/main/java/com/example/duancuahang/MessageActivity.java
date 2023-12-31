package com.example.duancuahang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.duancuahang.Class.Customer;
import com.example.duancuahang.Class.ItemMessage;
import com.example.duancuahang.Class.MessageData;
import com.example.duancuahang.Class.ShopData;
import com.example.duancuahang.RecyclerView.Message_Adapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    Toolbar toolBar_Message;
    RecyclerView rcvContent_Message;
    EditText edtInputContent_Message;
    Button btnSend_Message;
    Context context;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    ArrayList<String> arrIdItemMessage = new ArrayList<>();
    Message_Adapter messageAdapter;
    private ShopData shopData = new ShopData();
    private String idUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        context = this;
        setControl();
        setIntiazation();
        getMessageUser();
        setEvent();
    }

    private void sendMessage() {
        databaseReference = firebaseDatabase.getReference("ItemMessage");
        String keyPush = databaseReference.push().toString().substring(databaseReference.push().toString().lastIndexOf("/"));
        ItemMessage itemMessage = new ItemMessage(keyPush,shopData.getIdShop()+"-Shop",edtInputContent_Message.getText().toString());
        databaseReference.child(keyPush).setValue(itemMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                clearInput();
                hideKeyboard();
                databaseReference = firebaseDatabase.getReference("Message/"+shopData.getIdShop()+"/"+idUser);
                databaseReference.child(itemMessage.getIdItemMessage()+"/idItemMessage").setValue(itemMessage.getIdItemMessage());
                databaseReference = firebaseDatabase.getReference("Message/"+idUser+"/"+shopData.getIdShop());
                databaseReference.child(itemMessage.getIdItemMessage()+"/idItemMessage").setValue(itemMessage.getIdItemMessage());
            }
        });

    }

    private void getMessageUser() {
        databaseReference = firebaseDatabase.getReference("Message/" + shopData.getIdShop()+"/"+idUser);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrIdItemMessage.clear();
                if(snapshot.exists()){
                    for (DataSnapshot itemMess:
                         snapshot.getChildren()) {
                        String idMessage = itemMess.child("idItemMessage").getValue().toString();
                        arrIdItemMessage.add(idMessage);
                        messageAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEvent() {
        btnSend_Message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtInputContent_Message.getText().toString().isEmpty()) {
                    sendMessage();
                }
            }
        });
    }

    //    xóa giá trị vừa nhập
    private void clearInput() {
        edtInputContent_Message.setText(null);
    }

    //    ẩn bàn phím
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setIntiazation() {
        Intent intent = getIntent();
        idUser = intent.getStringExtra("idUser");

        SharedPreferences sharedPreferences = getSharedPreferences("InformationShop", Context.MODE_PRIVATE);
        String jsonShop = sharedPreferences.getString("informationShop", "");
        Gson gson = new Gson();
        shopData = gson.fromJson(jsonShop, ShopData.class);

//        gán giá trị cho adapter RecyclerView message
        messageAdapter = new Message_Adapter(arrIdItemMessage, context);
        rcvContent_Message.setLayoutManager(new LinearLayoutManager(context));
        rcvContent_Message.setAdapter(messageAdapter);

        setSupportActionBar(toolBar_Message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setControl() {
        toolBar_Message = findViewById(R.id.toolBar_Message);
        rcvContent_Message = findViewById(R.id.rcvContent_Message);
        edtInputContent_Message = findViewById(R.id.edtInputContent_Message);
        btnSend_Message = findViewById(R.id.btnSend_Message);
    }
}