package com.github.mobile.db.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.egit.github.core.event.Event;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.mobile.db.EventDBOpenHelper;

/**
 * Event表的增删改查的工具类
 * @author sunalong
 *
 */
public class EventDao {

    private EventDBOpenHelper helper;

    /**
     * 在构造函数中初始化数据库的帮助类
     */
    public EventDao(Context context) {
        helper = new EventDBOpenHelper(context);
    }

    /**
     * 向event表中插入数据
     * @param id
     * @param isPublic
     * @param type
     * @return
     */
    public long add(String id,boolean isPublic,String type){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("isPublic", isPublic);
        values.put("type", type);
        long insert = db.insert("event", null, values );
        return insert;
    }
    /**
     * 查找event表中的所有记录
     * @return
     */
    public List<Event> findAll(){
//        List<Event> eventList = Collections.emptyList();
        List<Event> eventList = new ArrayList<Event>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("event", null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("id"));
            int isPublic = cursor.getInt(cursor.getColumnIndex("isPublic"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            Event event = new Event();
            event.setId(id);
            event.setPublic(isPublic==1);
            event.setType(type);
            eventList.add(event);
        }
        return eventList;

    }
}
