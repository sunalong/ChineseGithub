package com.github.mobile.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Event的数据库的Helper
 * @author sunalong
 *
 */
public class EventDBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG ="EventDBOpenHelper";

    /**
     * 在构造函数中创建或打开数据库
     * @param context
     */
    public EventDBOpenHelper(Context context) {
        super(context, "Event.db", null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG,"Event.db被创建了");
        String sql="create table event(id varchar(20),isPublic boolean,type varchar(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"Event.db被更新了,TODO...");
    }

}
