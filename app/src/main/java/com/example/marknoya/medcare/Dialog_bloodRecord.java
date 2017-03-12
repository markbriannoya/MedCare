package com.example.marknoya.medcare;

import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by JJ Noya on 3/8/2017.
 */

public class Dialog_bloodRecord extends DialogFragment {
    SQLiteDatabase db;

    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_blood_record,null);

        TextView bloodpressure = (TextView)view.findViewById(R.id.bloodpressure);
        final EditText setblood = (EditText)view.findViewById(R.id.NewBloodPressure) ;
        Button cancel = (Button)view.findViewById(R.id.Cancel);


        db = getActivity().openOrCreateDatabase("appDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user(bloodPressure VARCHAR,time VARCHAR);");

        Cursor c = db.rawQuery("SELECT * FROM user ORDER BY time DESC LIMIT 1",null);
        StringBuffer buffer = new StringBuffer();
        if(c.moveToFirst()) {
            buffer.append(c.getString(0));
            bloodpressure.setText(buffer.toString());
        }else {
            bloodpressure.setText("--/--");
        }



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Canceled",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        Button save = (Button)view.findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSQL("INSERT INTO user VALUES('" +setblood.getText()+ "','datetime()');");
                Toast.makeText(view.getContext(),"Blood Pressure updated!",Toast.LENGTH_SHORT).show();


               // db.recordBlood(setblood.getText().toString());
                Toast.makeText(view.getContext(),"Saved",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return view;
    }




}
