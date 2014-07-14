package pt.margarida.contact.db;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class DbContentProvider extends ContentProvider {

	  // database
	  private DbHelper database;

	  // used for the UriMacher
	  private static final int EVENTS = 10;
	  private static final int EVENT_ID = 20;

	  private static final String AUTHORITY = "pt.margarida.contact.db.contentprovider";

	  private static final String BASE_PATH = "db";
	  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
	      + "/" + BASE_PATH);
	  
	  private static final String EVENT_PATH = "event";
	  public static final String EVENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	      + "/"+EVENT_PATH+"s";
	  public static final String EVENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	      + "/"+EVENT_PATH;

	  private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	  static {
	    sURIMatcher.addURI(AUTHORITY, BASE_PATH, EVENTS);
	    sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", EVENT_ID);
	  }

	  @Override
	  public boolean onCreate() {
	    database = new DbHelper(getContext());
	    return false;
	  }

	  @Override
	  public Cursor query(Uri uri, String[] projection, String selection,
	      String[] selectionArgs, String sortOrder) {

	    // Uisng SQLiteQueryBuilder instead of query() method
	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

	    // check if the caller has requested a column which does not exists
	    checkColumns(projection);

	    // Set the table
	    queryBuilder.setTables(EventContract.TABLE_EVENT);

	    int uriType = sURIMatcher.match(uri);
	    switch (uriType) {
	    case EVENTS:
	      break;
	    case EVENT_ID:
	      // adding the ID to the original query
	      queryBuilder.appendWhere(EventContract._ID + "="
	          + uri.getLastPathSegment());
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }

	    SQLiteDatabase db = database.getWritableDatabase();
	    Cursor cursor = queryBuilder.query(db, projection, selection,
	        selectionArgs, null, null, sortOrder);
	    // make sure that potential listeners are getting notified
	    cursor.setNotificationUri(getContext().getContentResolver(), uri);

	    return cursor;
	  }

	  @Override
	  public String getType(Uri uri) {
	    return null;
	  }

	  @Override
	  public Uri insert(Uri uri, ContentValues values) {
	    int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    int rowsDeleted = 0;
	    long id = 0;
	    switch (uriType) {
	    case EVENTS:
	      id = sqlDB.insert(EventContract.TABLE_EVENT, null, values);
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return Uri.parse(BASE_PATH + "/" + id);
	  }

	  @Override
	  public int delete(Uri uri, String selection, String[] selectionArgs) {
	    int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    int rowsDeleted = 0;
	    switch (uriType) {
	    case EVENTS:
	      rowsDeleted = sqlDB.delete(EventContract.TABLE_EVENT, selection,
	          selectionArgs);
	      break;
	    case EVENT_ID:
	      String id = uri.getLastPathSegment();
	      if (TextUtils.isEmpty(selection)) {
	        rowsDeleted = sqlDB.delete(EventContract.TABLE_EVENT,
	            EventContract._ID + "=" + id, 
	            null);
	      } else {
	        rowsDeleted = sqlDB.delete(EventContract.TABLE_EVENT,
	            EventContract._ID + "=" + id 
	            + " and " + selection,
	            selectionArgs);
	      }
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return rowsDeleted;
	  }

	  @Override
	  public int update(Uri uri, ContentValues values, String selection,
	      String[] selectionArgs) {

	    int uriType = sURIMatcher.match(uri);
	    SQLiteDatabase sqlDB = database.getWritableDatabase();
	    int rowsUpdated = 0;
	    switch (uriType) {
	    case EVENTS:
	      rowsUpdated = sqlDB.update(EventContract.TABLE_EVENT, 
	          values, 
	          selection,
	          selectionArgs);
	      break;
	    case EVENT_ID:
	      String id = uri.getLastPathSegment();
	      if (TextUtils.isEmpty(selection)) {
	        rowsUpdated = sqlDB.update(EventContract.TABLE_EVENT, 
	            values,
	            EventContract._ID + "=" + id, 
	            null);
	      } else {
	        rowsUpdated = sqlDB.update(EventContract.TABLE_EVENT, 
	            values,
	            EventContract._ID + "=" + id 
	            + " and " 
	            + selection,
	            selectionArgs);
	      }
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
	    return rowsUpdated;
	  }

	  private void checkColumns(String[] projection) {
	    String[] available = { EventContract.COLUMN_NAME,
	        EventContract.COLUMN_START_DATE, EventContract.COLUMN_END_DATE,
	        EventContract._ID, EventContract.COLUMN_ENDLESS,
	        EventContract._COUNT};
	    if (projection != null) {
	      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
	      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
	      // check if all columns which are requested are available
	      if (!availableColumns.containsAll(requestedColumns)) {
	        throw new IllegalArgumentException("Unknown columns in projection");
	      }
	    }
	  }

	} 
