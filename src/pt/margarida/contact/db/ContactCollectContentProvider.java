package pt.margarida.contact.db;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
		return null;
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
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    Log.w(TAG, "after getWritableDatabase call at ContentProvider");
	    int rowsDeleted = 0;
	    long id = 0;
	    switch (uriType) {
	    case ContactCollectorUriMatcher.PROJECT:
	    	id = sqlDB.insert(ContactCollectContract.Project.tableName, null, values);
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
