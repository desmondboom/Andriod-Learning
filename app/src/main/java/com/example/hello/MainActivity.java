package com.example.hello;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_CONTACT_REQUEST = 1;
    Button button_1;
    Button button_2;
    Button button_3;
    Button button_4;
    Button button_5;
    Button button_6;
    Button button_7;

    public static final String TAG = "[MainActivity]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "this is main activity");
        Log.d(TAG, "thread : " + Thread.currentThread().getId());

        button_1 = findViewById(R.id.button_1);
        button_1.setOnClickListener(v -> openConstraintActivity());

        button_2 = findViewById(R.id.button_2);
        button_2.setOnClickListener(v -> openLoginActivity());

        button_3 = findViewById(R.id.button_3);
        button_3.setOnClickListener(v -> openPickUpContact());

        button_4 = findViewById(R.id.button_4);
        button_4.setOnClickListener(v -> openFragmentActivity());

        button_5 = findViewById(R.id.button_5);
        button_5.setOnClickListener(v -> openRecyclerViewActivity());

        button_6 = findViewById(R.id.button_6);
        button_6.setOnClickListener(v -> openThreadActivity());

        button_7 = findViewById(R.id.button_7);
        button_7.setOnClickListener(v -> openHandlerActivity());

    }

    private void openHandlerActivity() {
        startActivity(new Intent(this, HandlerActivity.class));
    }

    private void openThreadActivity() {
        startActivity(new Intent(this, ThreadActivity.class));
    }

    private void openRecyclerViewActivity() {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    private void openFragmentActivity() {
        Intent intent = new Intent(this, MyFragmentActivity.class);
        startActivity(intent);
    }

    private void openPickUpContact() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setDataAndType(Uri.parse("content://contacts"), "vnd.android.cursor.dir/phone_v2");
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    private void openConstraintActivity() {
        Intent intent = new Intent(this, ConstraintActivity.class);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(), new String[]{"display_name", "data1"}, null, null, null);
            cursor.moveToNext();
            @SuppressLint("Range")
            String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range")
            String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
            Toast.makeText(this, contactName + " " + phoneNum, Toast.LENGTH_SHORT).show();
        }
    }

}