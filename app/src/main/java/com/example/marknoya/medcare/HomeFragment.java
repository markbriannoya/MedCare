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
    private Database db;
    private Session session;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        db = new Database(getContext());
        session = new Session(getContext());




        final TextView bloodpressure = (TextView)view.findViewById(R.id.bloodpressure);
        TextView record = (TextView)view.findViewById(R.id.bloodrecord);


        bloodpressure.setText(db.getBloodpressure(session.getUserid()));

        record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {

                Dialog_bloodRecord bloodRec = new Dialog_bloodRecord();
                bloodRec.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        bloodpressure.setText(db.getBloodpressure(session.getUserid()));
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
