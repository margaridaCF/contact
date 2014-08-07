package pt.margarida.contact.db;

import android.content.UriMatcher;
import android.net.Uri;

public class ContactCollectorUriMatcher extends UriMatcher{
	public static final int PROJECT = 10;
	public static final int PROJECT_ID = 11;
	public static final int CONTACT = 20;
	public static final int CONTACT_ID = 21;
	
	public ContactCollectorUriMatcher(int code) {
		super(code);
		addURI(ContactCollectContract.AUTHORITY, UriCC.PROJECT.path, UriCC.PROJECT.code);
		addURI(ContactCollectContract.AUTHORITY, UriCC.PROJECT_ID.path, UriCC.PROJECT_ID.code);
		addURI(ContactCollectContract.AUTHORITY, UriCC.PROJECT_CONTACT.path, UriCC.PROJECT_CONTACT.code);
		addURI(ContactCollectContract.AUTHORITY, UriCC.PROJECT_CONTACT_ID.path, UriCC.PROJECT_CONTACT_ID.code);
	}
	
	public enum UriCC {
		PROJECT (ContactCollectorUriMatcher.PROJECT, ContactCollectContract.Project.tableName),
		PROJECT_ID   (ContactCollectorUriMatcher.PROJECT_ID, ContactCollectContract.Project.tableName+"/#"),
		PROJECT_CONTACT   (ContactCollectorUriMatcher.CONTACT, ContactCollectContract.Project.tableName+"/#/CONTACT"),
		PROJECT_CONTACT_ID    (ContactCollectorUriMatcher.CONTACT_ID, ContactCollectContract.Project.tableName+"/#/CONTACT/#");

		public final int code;  
		public final String path;
		private UriCC(int code, String path) {
			this.code = code;
			this.path = path;
		}
		
		public Uri getUri(){
			return Uri.parse("content://" + ContactCollectContract.AUTHORITY + "/"+path);
		}
	}
}


