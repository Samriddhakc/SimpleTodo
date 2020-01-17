package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT="item_text";
    public static final String KEY_ITEM_POSITION="item_position";
    public static final int EDIT_TEXT_CODE=20;
    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    itemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd=findViewById(R.id.btnAdd);
        etItem=findViewById(R.id.etItem);
        rvItems=findViewById(R.id.rvItems);

        items=new ArrayList<>();
        loadItems();

        itemsAdapter.onLongClickListener onLongClickListener=new itemsAdapter.onLongClickListener(){
            @Override
            public void onLongClickListener(int position) {
                //delete the items from the model
                //notify yhe adapter
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed",Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };

        itemsAdapter.onClickListener onClickListener= new itemsAdapter.onClickListener() {
            @Override
            public void onClickListener(int position) {
                     Log.d("MainActivity","Clicked at position"+position);
                     //create the new activity
                    Intent i = new Intent(MainActivity.this,EditActivity.class);


                    //pass data being edited
                    i.putExtra(KEY_ITEM_TEXT,items.get(position));
                    i.putExtra(KEY_ITEM_POSITION,position);
                    startActivityForResult(i,EDIT_TEXT_CODE);
                    //display the activity
            }
        };

       itemsAdapter=new itemsAdapter(items,onLongClickListener,onClickListener);
       rvItems.setAdapter(itemsAdapter);
       rvItems.setLayoutManager((new LinearLayoutManager(this)));

       btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               String addedtodoItem=etItem.getText().toString();

               //Add the item to list
                // Notify the adapter

               items.add(addedtodoItem);
               itemsAdapter.notifyDataSetChanged();
               etItem.setText("");
               Toast.makeText(getApplicationContext(),"Item was added",Toast.LENGTH_SHORT).show();
               saveItems(); }
        });

    }

    //Handle the result of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && requestCode==EDIT_TEXT_CODE){
            //Retrieve the updated text value
            String itemText=data.getStringExtra(KEY_ITEM_TEXT);
            int position=data.getExtras().getInt(KEY_ITEM_POSITION);

            //extract the original position of the edited item from the position key
            items.set(position,itemText);
            //notify the adapter
            itemsAdapter.notifyItemChanged(position);
            //presist the changes
            saveItems();
            Toast.makeText(getApplicationContext(),"Item was updated!!",Toast.LENGTH_SHORT).show();


        }
        else{
            Log.d("MainActivity","Unknown to the on activity result");

        }


    }


    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
   }

    //This function will load items by reading line by line of our data file
    //Save by writing new items to the file

    private void loadItems(){

        try {
            items= new ArrayList<>(FileUtils.readLines(getDataFile(),Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items",e);
            items=new ArrayList<>();
            e.printStackTrace();
        }
    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items",e);
        }

    }

}
