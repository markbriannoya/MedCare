package com.example.marknoya.medcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class Database extends SQLiteOpenHelper {

    public static final String TAG = Database.class.getSimpleName();
    public static final String DB_NAME = "MedCare.db";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASS = "password";

    public static final String BLOODPRESSURE_TABLE = "user_bloodpressure";
    public static final String COLUMN_BLOODPRESSURE = "bloodpressure";
    public static final String COLUMN_TIME = "time";

    /*
       create table users(
        ip integer primary key autoincrement,
        email text,
        password text);
     */

    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASS + " TEXT)";

    public static final String CREATE_TABLE_BLOODPRESSURE = "CREATE TABLE " + BLOODPRESSURE_TABLE + "("
            + COLUMN_ID + " INTEGER,"
            + COLUMN_BLOODPRESSURE + " TEXT,"
            + COLUMN_TIME +" DATETIME DEFAULT CURRENT_TIMESTAMP)";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_BLOODPRESSURE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXIST " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXIST " + BLOODPRESSURE_TABLE);
        onCreate(db);
    }

    /**
     *  Storing user details in database
     */
    public void addUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, password);

        long id = db.insert(USER_TABLE, null, values);
        db.close();

        Log.d(TAG, "user inserted" + id);
    }

    public boolean getUser (String email, String pass) {
        String selectQuery = "select * from " + USER_TABLE + " where "
                + COLUMN_EMAIL + " = " + "'" + email + "'" + " and " + COLUMN_PASS + " = " + "'" + pass +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){

            return true;
        }
        cursor.close();
        db.close();

        return false;

    }
    public boolean checkUser (String email) {
        String selectQuery = "select * from " + USER_TABLE + " where "
                + COLUMN_EMAIL + " = " + "'" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){

            return true;
        }
        cursor.close();
        db.close();

        return false;
    }
    public String getId(String email) {
        String selectQuery = "select _id from " + USER_TABLE + " where "
                + COLUMN_EMAIL + " = " + "'" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            return cursor.getString(0);
        }
        cursor.close();
        db.close();

        return null;
    }
    public String[] getUserinfo(String userid){
        String selectQuery = "select * from " + USER_TABLE + " where "
                + COLUMN_ID + " = " + "'" + userid + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            String[] info = {cursor.getString(1),cursor.getString(2)};
            return info;



        }
        cursor.close();
        db.close();

        return null;
    }
    public void addBloodpressure(String id,String bloodpressure){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_BLOODPRESSURE, bloodpressure);



        db.insert(BLOODPRESSURE_TABLE, null, values);
        db.close();
    }
    public String getBloodpressure(String id){
        String selectQuery = "select bloodpressure,time from " + BLOODPRESSURE_TABLE + " where "
                + COLUMN_ID + " = " + "'" + id + "' order by time desc limit 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {


            return cursor.getString(0);
        }
        cursor.close();
        db.close();

        return "--";

    }
}



















    /*
     public void recordBlood(String blood){

        db = openOrCreateDatabase("appDB", Context.MODE_PRIVATE,null);
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
    }
}*/

