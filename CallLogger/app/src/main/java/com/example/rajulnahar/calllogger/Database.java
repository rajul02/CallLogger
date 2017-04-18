package com.example.rajulnahar.calllogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajul Nahar on 10-04-2017.
 */

public class Database extends SQLiteOpenHelper {
    public static String DatabaseName = "CallLogger.db";


    public static String Table_UserDetails = "UserDetails";
    public static String Col_UserDetails_Id = "id";
    public static String Col_UserDetails_Name = "name";
    public static String Col_UserDetails_Phone = "phone";

    public static String Table_LogInfo = "LogInfo";
    public static String Col_LogInfo_Id = "id";
    public static String Col_LogInfo_Name = "Name";
    public static String Col_LogInfo_Msg = "Msg";
    public static String Col_LogInfo_Time = "Time";


    public String createTableUserDetails = "Create table "+Table_UserDetails+"(" +
            Col_UserDetails_Id+" integer primary key autoincrement, " +
            Col_UserDetails_Name+" text," +
            Col_UserDetails_Phone+ " text" + ")";

    public String createTableLogInfo = "Create table "+Table_LogInfo+"(" +
            Col_LogInfo_Id+" integer primary key autoincrement, " +
            Col_LogInfo_Name+ " text," +
            Col_LogInfo_Msg+ " text," +
            Col_LogInfo_Time+" text" + ")";


    public SQLiteDatabase sqLiteDatabase;
    private static Database mInstance = null;
    private String sql;
    public static Database getInstance(Context context){
        if(mInstance == null){
            mInstance = new Database(context);
        }
        return mInstance;
    }

    private Database(Context context) {
        super(context, DatabaseName,null, 1);
        sqLiteDatabase = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTableLogInfo);
        db.execSQL(createTableUserDetails);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "Drop Table "+Table_LogInfo;
        db.execSQL(sql);
        sql = "Drop Table "+Table_UserDetails;
        db.execSQL(sql);
        onCreate(db);

    }

    public List<UserDetails> getUserDetails(){
        List<UserDetails> userDetails = new ArrayList<>();
        sql = "select * from " + Table_UserDetails;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                userDetails.add(setUserDetailsFromCursor(cursor));
            }while (cursor.moveToNext());
        }
        return userDetails;
    }



    public void test(){
        sql = "insert into " + Table_UserDetails + "("+Col_UserDetails_Phone+", "+ Col_UserDetails_Name +") values('1234567890','rajul')";
        sqLiteDatabase.execSQL(sql);

    }

    public UserDetails setUserDetailsFromCursor(Cursor cursor){
        UserDetails userDetails = new UserDetails();
        userDetails.id = cursor.getInt(cursor.getColumnIndex(Col_UserDetails_Id));
        userDetails.name = cursor.getString(cursor.getColumnIndex(Col_UserDetails_Name));
        userDetails.phone = cursor.getString(cursor.getColumnIndex(Col_UserDetails_Phone));

        return userDetails;
    }

    public long addUserDetails(UserDetails userDetails){
        return sqLiteDatabase.insertOrThrow(Table_UserDetails,null,getValues(userDetails));
    }

    public ContentValues getValues(UserDetails userDetails){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_UserDetails_Name,userDetails.name);
        contentValues.put(Col_UserDetails_Phone,userDetails.phone);


        return contentValues;
    }

    public long addLogInfo(LogInfo logInfo){
        return sqLiteDatabase.insertOrThrow(Table_LogInfo,null,getValuesLogInfo(logInfo));
    }

    public ContentValues getValuesLogInfo(LogInfo logInfo){
        ContentValues contentValues = new ContentValues();
        //contentValues.put(Col_Favourites_Id,favourites.id);
        contentValues.put(Col_LogInfo_Msg,logInfo.msg);
        contentValues.put(Col_LogInfo_Name,logInfo.name);
        contentValues.put(Col_LogInfo_Time,logInfo.time);
        return contentValues;
    }
    public List<LogInfo> getLogInfo(){
        List<LogInfo> logInfos =new ArrayList<>();
        sql = "select * from " + Table_LogInfo;
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                logInfos.add(setLogInfoFromCursor(cursor));
            }while (cursor.moveToNext());
        }
        return logInfos;
    }

    public LogInfo setLogInfoFromCursor(Cursor cursor){
        LogInfo logInfo = new LogInfo();
        logInfo.id = cursor.getInt(cursor.getColumnIndex(Col_LogInfo_Id));
        logInfo.name = cursor.getString(cursor.getColumnIndex(Col_LogInfo_Name));
        logInfo.msg = cursor.getString(cursor.getColumnIndex(Col_LogInfo_Msg));
        logInfo.time = cursor.getString(cursor.getColumnIndex(Col_LogInfo_Time));

        return logInfo;
    }



    public void deleteTables(){
        SQLiteDatabase db = sqLiteDatabase;
        String sql = "Drop Table if exists "+Table_UserDetails;
        db.execSQL(sql);
        sql = "Drop Table if exists "+Table_LogInfo;
        db.execSQL(sql);
        onCreate(db);
    }
}
