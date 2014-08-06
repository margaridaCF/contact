package pt.margarida.contact.db;


import java.util.Date;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class ContactCollectDAO  {

    public static final String DATABASE_NAME = "contactColect.db";
    public static final int DATABASE_VERSION = 1;
    
    private SQLiteDatabase db;
    
    /**
     * Store a new project with dates.
     * @param values
     * @return
     */
    public ContentValues insertDatedProject(String name, Date start, Date end){
    	ContentValues values = new ContentValues();
    	values.put(ContactCollectContract.Project.COL_NAME, name);
    	values.put(ContactCollectContract.Project.COL_START_DATE, start.getTime());
    	values.put(ContactCollectContract.Project.COL_END_DATE, end.getTime());
    	values.put(ContactCollectContract.Project.COL_ENDLESS, 0);
    	values.put(ContactCollectContract.Project.COL_DELETED, 0);
    	return values;
    }

	private boolean insertValues(ContentValues values) {
		int res = (int) db.insert(ContactCollectContract.Project.tableName, null, values);
    	if(res == -1) return false;
    	else return true;
	}
    
    /**
     * Store a new project that is endless i.e. has no dates.
     * @param values
     * @return
     */
    public boolean insertEndlessProject(String name, boolean endless){
    	ContentValues values = new ContentValues();
    	values.put(ContactCollectContract.Project.COL_NAME, name);
    	values.put(ContactCollectContract.Project.COL_ENDLESS, 1);
    	values.put(ContactCollectContract.Project.COL_DELETED, 0);
    	return insertValues(values);
    }
}
