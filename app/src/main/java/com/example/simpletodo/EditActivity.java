package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText edItem;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btnSave = findViewById(R.id.btnSave);
        edItem = findViewById(R.id.edItem);
        getSupportActionBar().setTitle("Edit item");

        edItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        //clicked when the use is done editing

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create an intent which will contain the results
                //pass the results
                //set the result of the intent
                //finish activity, and go back

                Intent intent=new Intent();
                intent.putExtra(MainActivity.KEY_ITEM_TEXT,edItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION,getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
