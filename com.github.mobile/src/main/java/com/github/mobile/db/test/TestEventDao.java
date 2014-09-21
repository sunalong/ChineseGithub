package com.github.mobile.db.test;

import java.util.List;

import org.eclipse.egit.github.core.event.Event;

import android.test.AndroidTestCase;
import android.util.Log;

import com.github.mobile.db.dao.EventDao;

/**
 * Event的测试类
 * @author sunalong
 *
 */
public class TestEventDao extends AndroidTestCase {

    private static final String TAG = "TestEventDao";
    private EventDao dao;

    /**
     * 测试向event表中添加数据功能
     */
    public void testAdd(){
        for(int i=0;i<5;i++){
            dao.add("ID_"+i, i/2==0, "TYPE_"+i);
        }
    }

    public void testFindAll(){
        List<Event> eventList = dao.findAll();
        Log.i(TAG,"打印event:"+eventList);

        if(eventList!=null){
            for(int i=0;i<eventList.size();i++){
                Log.i(TAG,"第"+i+"个event:"+eventList.get(i));
            }
        }
    }
    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
        dao = new EventDao(getContext());;
    }
    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
        if(dao !=null)
            dao = null;
    }
}
