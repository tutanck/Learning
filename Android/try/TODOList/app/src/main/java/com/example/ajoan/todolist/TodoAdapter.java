package com.example.ajoan.todolist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AJoan on 29/11/2016.
 */
public class TodoAdapter extends ArrayAdapter<Item>{
    private final int ressource;

    TodoAdapter(Context context, int ressource, List<Item> items) {
        super(context, ressource, items);
        this.ressource=ressource;
    }

    public View getView(int position, View itemView, ViewGroup parent){
        //return super.getView(position,convertView,parent);
        if(itemView==null)
            itemView= LayoutInflater.from(getContext()).inflate(ressource,null);

        final TextView tv =(TextView)itemView.findViewById(R.id.itemtextview); //search ViewById only on this itemView
        final Item item = getItem(position);
        tv.setText(item.getText());

        CheckBox cb=(CheckBox )itemView.findViewById(R.id.itemcheckbox);

//  Reset item's stored state
        if(item.isChecked()) {
                ((CheckBox) cb).setChecked(true);
            tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            ((CheckBox) cb).setChecked(false);
            tv.setPaintFlags(tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!item.isChecked()) {
                    item.setChecked(true);
                    ((CheckBox) v).setChecked(true);
                    tv.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    item.setChecked(false);
                    ((CheckBox) v).setChecked(false);
                    tv.setPaintFlags(tv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        ImageView iv = (ImageView) itemView.findViewById(R.id.itemimageview);
        iv.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        remove(item);
                    }
                }
        );

        return itemView;
    }
}
