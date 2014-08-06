package pt.margarida.contact.db;

import android.content.UriMatcher;

public class ContactCollectorUriMatcher extends UriMatcher{
	public static final int PROJECT = 1;
	public static final int PROJECT_ID = 2;
	public static final int PROJECT_CONTACT = 3;
	public static final int PROJECT_CONTACT_ID = 4;
	public static final int DELETED_PROJECT = 20;
	public static final int CONTACT = 9;
	public static final int CONTACT_ID = 10;
	public static final int CONTACT_FILTER = 14;
	public static final int CONTACTMETHODS = 18;
	public static final int CONTACTMETHODS_ID = 19;
	
	public ContactCollectorUriMatcher(int code) {
		super(code);
		addURI(ContactCollectContract.AUTHORITY, ContactCollectContract.Project.tableName, PROJECT);
		addURI(ContactCollectContract.AUTHORITY, ContactCollectContract.Project.tableName+"/#", PROJECT_ID);
		addURI(ContactCollectContract.AUTHORITY, ContactCollectContract.Project.tableName+"/#/CONTACT", PROJECT_CONTACT);
		addURI(ContactCollectContract.AUTHORITY, ContactCollectContract.Project.tableName+"/#/CONTACT/#", PROJECT_CONTACT_ID);
		addURI(ContactCollectContract.AUTHORITY, "deleted_PROJECT", DELETED_PROJECT);
		addURI(ContactCollectContract.AUTHORITY, "CONTACT", CONTACT);
		addURI(ContactCollectContract.AUTHORITY, "CONTACT/filter/*", CONTACT_FILTER);
		addURI(ContactCollectContract.AUTHORITY, "CONTACT/#", CONTACT_ID);
		addURI(ContactCollectContract.AUTHORITY, "contact_methods", CONTACTMETHODS);
		addURI(ContactCollectContract.AUTHORITY, "contact_methods/#", CONTACTMETHODS_ID);
	}


}
