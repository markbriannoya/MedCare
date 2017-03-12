package com.example.marknoya.medcare;
import android.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Database extends AppCompatActivity {

    SQLiteDatabase db;

    public void recordBlood(String blood){

        db = openOrCreateDatabase("appDB",Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user(bloodPressure VARCHAR,time VARCHAR);");
        db.execSQL("INSERT INTO student VALUES('" +blood+ "','CURRENT_TIMESTAMP');");
        Toast.makeText(this,"Blood Pressure updated!",Toast.LENGTH_SHORT);
    }
    public String getBlood() {
        db = openOrCreateDatabase("appDB",Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user(bloodPressure VARCHAR,time VARCHAR);");
        Cursor c = db.rawQuery("SELECT * FROM user",null);
        StringBuffer buffer = new StringBuffer();
        if(c.moveToFirst()) {
            buffer.append(c.getString(0));
            return buffer.toString();
        }else {
            return "--/--";
        }
    }






    /*
    public void onClick(View view){
        if(view == btAdd){

            db.execSQL("INSERT INTO student VALUES('" + etStdntId.getText()+ "','" + etStdntName.getText() + "','" + etStdntProg.getText() + "');");
            Toast.makeText(this,"Record Added",Toast.LENGTH_SHORT);
            clearText();

        }
        else if(view == btDelete){
            Cursor c = db.rawQuery("SELECT * FROM student WHERE stdnt_id='" + etStdntId.getText() + "'",null);
            if(c.moveToFirst()){
                db.execSQL("DELETE FROM student WHERE stdnt_id='" + etStdntId.getText() + "'");
                Toast.makeText(this,"Record Deleted",Toast.LENGTH_SHORT);
            }
            clearText();
        }
        else if(view == btSearch){
            Cursor c = db.rawQuery("SELECT * FROM student WHERE stdnt_id='" + etStdntId.getText() + "'",null);
            StringBuffer buffer = new StringBuffer();
            if(c.moveToFirst()){

                buffer.append("Name: " + c.getString(1) + "\n");
                buffer.append("Program: "+ c.getString(2)+ "\n\n");

            }
            showMessage("Student Details",buffer.toString());
            clearText();
        }
        else if(view==btView){
            Cursor c = db.rawQuery("SELECT * FROM student", null);

            if(c.getCount()==0) {

                Toast.makeText(this,"No Records",Toast.LENGTH_SHORT);
                return;
            }

            StringBuffer buffer = new StringBuffer();
            while(c.moveToNext()){
                buffer.append("ID: "+ c.getString(0) + "\n");
                buffer.append("Name: "+ c.getString(1) + "\n");
                buffer.append("Program: "+ c.getString(2) + "\n");

            }
            showMessage("Student Details",buffer.toString());

        }
    }
    public void clearText() {
        etStdntProg.setText("");
        etStdntName.setText("");
        etStdntId.setText("");
    }
    public void showMessage(String title, String message){
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }*/
}

