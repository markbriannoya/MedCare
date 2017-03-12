package com.example.marknoya.medcare;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;
import com.example.marknoya.medcare.Database;

public class HomeFragment extends Fragment {
    SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);


        db = getActivity().openOrCreateDatabase("appDB", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user(bloodPressure VARCHAR,time TIMESTAMP);");

        final TextView bloodpressure = (TextView)view.findViewById(R.id.bloodpressure);
        TextView record = (TextView)view.findViewById(R.id.bloodrecord);

        Cursor c = db.rawQuery("SELECT * FROM user ORDER BY time DESC LIMIT 1",null);
        StringBuffer buffer = new StringBuffer();
        if(c.moveToFirst()) {
            buffer.append(c.getString(0));

            bloodpressure.setText(buffer.toString());
        }else {
            bloodpressure.setText("--/--");
        }

        record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                Dialog_bloodRecord bloodRec = new Dialog_bloodRecord();
                bloodRec.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Cursor c = db.rawQuery("SELECT * FROM user ORDER BY time DESC LIMIT 1",null);
                        StringBuffer buffer = new StringBuffer();
                        if(c.moveToFirst()) {
                            buffer.append(c.getString(0));
                            bloodpressure.setText(buffer.toString());
                        }else {
                            bloodpressure.setText("--/--");
                        }
                    }
                });
                bloodRec.show(getActivity().getFragmentManager(),"Blood");

            }
        });




        return view;
    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

}
