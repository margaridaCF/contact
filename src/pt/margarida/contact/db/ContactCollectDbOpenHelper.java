package pt.margarida.contact.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ContactCollectDbOpenHelper extends SQLiteOpenHelper{
	public static final String TAG = "ContactCollectDbOpenHelper";
	// Database creation SQL statement
    private static final String PROJECT_CREATE = "create table " 
    		+ ContactCollectContract.Project.tableName
    		+ "(" 
    		+ ContactCollectContract.Project._ID + " integer primary key autoincrement, " 
    		+ ContactCollectContract.Project.COL_NAME + " text not null, " 
    		+ ContactCollectContract.Project.COL_START_DATE + " real ," 
    		+ ContactCollectContract.Project.COL_END_DATE + " real ," 
    		+ ContactCollectContract.Project.COL_ENDLESS + " integer not null," 
    		+ ContactCollectContract.Project.COL_DELETED + " integer not null" 
    		+ ");";
    

    public ContactCollectDbOpenHelper(Context context) {
      super(context, ContactCollectDAO.DATABASE_NAME, null, ContactCollectDAO.DATABASE_VERSION);
//      context.deleteDatabase(ContactCollectDAO.DATABASE_NAME);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
    	try {
    		database.execSQL(PROJECT_CREATE);
    		Log.w(TAG, "The creation query has been successfully executed. \n"+PROJECT_CREATE);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
