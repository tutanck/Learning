package com.example.ajoan.todolist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mySelf = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_keywords);

        //final ArrayList<String> data = new ArrayList<>();
        //data.add("default item");//debug

        final ArrayList<Item> data = new ArrayList<>();

        final Button badd=(Button)findViewById(R.id.mainbutton);
        //badd.setEnabled(false); //@see xml
        final EditText etxt= (EditText)findViewById(R.id.maininput);
        ListView lv= (ListView)findViewById(R.id.mainlistview);


        //final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        final TodoAdapter adapter =new TodoAdapter(this, R.layout.list_item, data );
        lv.setAdapter(adapter);

        /**
         * Bizarerie : ne marche plus lorsque ne s'applique pas a une vue standard d' android */
        /*lv.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    data.remove(position);
                    adapter.notifyDataSetChanged();
            }
        });*/

        etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(etxt.getText().length()>0)
                    badd.setEnabled(true);
                else
                    badd.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        badd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Toast.makeText(mySelf,"ok",Toast.LENGTH_SHORT).show();
                Log.i("SttsAf ", "baddclick with text :"+etxt.getText());
                adapter.add(new Item(false,etxt.getText().toString())); //performs an add operation on inner data struct
                etxt.setText("");//sert aussi d'event pour refresh l'adapter (implicit notifyDataSetChanged)
            }
        });

    }
}