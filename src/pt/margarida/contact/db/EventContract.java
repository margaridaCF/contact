package pt.margarida.contact.db;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

public class EventContract implements BaseColumns{
	// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public EventContract() {}

    /* Inner class that defines the table contents */
        public static final String TABLE_EVENT = "event";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_START_DATE = "start";
        public static final String COLUMN_END_DATE = "end";
        public static final String COLUMN_ENDLESS = "endless";
        public static final String COLUMN_DELETED = "deleted";
    
 // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table " 
    		+ TABLE_EVENT
    		+ "(" 
    		+ _ID + " integer primary key autoincrement, " 
    		+ COLUMN_NAME + " text not null, " 
    		+ COLUMN_START_DATE + " real ," 
    		+ COLUMN_END_DATE + " real " 
    		+ COLUMN_ENDLESS + " integer not null," 
    		+ COLUMN_DELETED + " integer not null" 
    		+ ");";
    
    public static void onCreate(SQLiteDatabase database) {
    	database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
    		int newVersion) {
    	Log.w(EventContract.class.getName(), "Upgrading database from version "
    			+ oldVersion + " to " + newVersion
    			+ ", which will destroy all old data");
    	database.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
    	onCreate(database);
    }
}
