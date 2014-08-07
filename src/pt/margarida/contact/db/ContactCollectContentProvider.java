package pt.margarida.contact.db;


import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class ContactCollectContentProvider extends ContentProvider{
	private static final ContactCollectorUriMatcher sURIMatcher = new ContactCollectorUriMatcher(UriMatcher.NO_MATCH);
	private static final String TAG = "ContactCollectContentProvider";
	private ContactCollectDbOpenHelper database;
	
	@Override
	public boolean onCreate() {
//		// The provider is closed for business until fully initialized
//		CountDownLatch mReadAccessLatch = new CountDownLatch(1);
//		CountDownLatch mWriteAccessLatch = new CountDownLatch(1);
//
//		HandlerThread mBackgroundThread = new HandlerThread("ContactsProviderWorker",
//                Process.THREAD_PRIORITY_BACKGROUND);
//        mBackgroundThread.start();
//        Handler mBackgroundHandler = new Handler(mBackgroundThread.getLooper()) {
//            @Override
//            public void handleMessage(Message msg) {
//                performBackgroundTask(msg.what, msg.obj);
//            }
//        };
		
		
		
		
		database = new ContactCollectDbOpenHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO user uri matcher + db access
		int uriType = sURIMatcher.match(uri);
		// Uisng SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		switch (uriType) {
		case ContactCollectorUriMatcher.PROJECT:
			// check if the caller has requested a column which does not exists
			checkProjectColumns(projection);
			// Set the table
			queryBuilder.setTables(ContactCollectContract.Project.tableName);
//			// adding the ID to the original query
//			queryBuilder.appendWhere(ContactCollectContract.Project._ID + "=*");
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		StringBuilder projectionS = new StringBuilder();
		for(int i = 0; i < projection.length; i++){
			projectionS.append(projection[i]);
			projectionS.append(", ");
		}
		Log.i(TAG, "Query: projection:"+projectionS);
		SQLiteDatabase db = database.getWritableDatabase();
	    Cursor cursor = queryBuilder.query(db, projection, selection,
	        selectionArgs, null, null, sortOrder);
	    // make sure that potential listeners are getting notified
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}
	
	private void checkProjectColumns(String[] projection) {
	    String[] available = { ContactCollectContract.Project.COL_DELETED,
	    		ContactCollectContract.Project.COL_END_DATE, ContactCollectContract.Project.COL_ENDLESS,
	    		ContactCollectContract.Project.COL_NAME, ContactCollectContract.Project.COL_START_DATE,
	    		ContactCollectContract.Project._ID};
	    if (projection != null) {
	      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
	      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
	      // check if all columns which are requested are available
	      if (!availableColumns.containsAll(requestedColumns)) {
	        throw new IllegalArgumentException("Unknown columns in projection");
	      }
	    }
	  }

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO usar uri matcher + db access
		int uriType = sURIMatcher.match(uri);
		// Put this in AsyncTask and use 
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    int rowsDeleted = 0;
	    long id = 0;
	    switch (uriType) {
	    case ContactCollectorUriMatcher.PROJECT:
	    	id = sqlDB.insert(ContactCollectorUriMatcher.UriCC.PROJECT.path, null, values);
	    	Log.i(TAG, "Inserted "+values.toString());
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return Uri.parse(ContactCollectContract.Project.CONTENT_ITEM_TYPE + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO usar uri matcher + db access
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO usar uri matcher + db access
		return 0;
	}
	
}
