package pt.margarida.contact.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class ContactCollectContract {
	/** The authority for the contacts provider */
	public static final String AUTHORITY = "pt.margarida.contactcollector";
	/** A content:// style uri to the authority for the contacts provider */
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
	
	private static final String uriTable= "vnd.android.cursor.dir/vnd."+AUTHORITY+".provider.";
	private static final String uriRow= "vnd.android.cursor.item/vnd."+AUTHORITY+".provider.";
	
	public static final class Project implements BaseColumns {
		public final static String tableName = "Projects";

		/**
		 * Not instantiable.
		 */
		private Project() {
		}

		/**
		 * The content:// style URI for this table.  
		 */
		public static final Uri CONTENT_URI =
				Uri.withAppendedPath(AUTHORITY_URI, tableName);

		/**
		 * The MIME-type of {@link #CONTENT_URI} providing a directory of
		 * contact directories.
		 */
		public static final String CONTENT_TYPE = uriTable + tableName;

		/**
		 * The MIME type of a {@link #CONTENT_URI} item.
		 */
		public static final String CONTENT_ITEM_TYPE = uriRow + tableName;
		

        public static final String COL_NAME = "name";
        public static final String COL_START_DATE = "start";
        public static final String COL_END_DATE = "end";
        public static final String COL_ENDLESS = "endless";
        public static final String COL_DELETED = "deleted";
	}
	
	public static final class Contact implements BaseColumns {
		private final static String tableName = "Contact";

		/**
		 * Not instantiable.
		 */
		private Contact() {
		}

		/**
		 * The content:// style URI for this table.  
		 */
		public static final Uri CONTENT_URI =
				Uri.withAppendedPath(AUTHORITY_URI, "contacts");

		/**
		 * The MIME-type of {@link #CONTENT_URI} providing a directory of
		 * contact directories.
		 */
		public static final String CONTENT_TYPE = uriTable + tableName;

		/**
		 * The MIME type of a {@link #CONTENT_URI} item.
		 */
		public static final String CONTENT_ITEM_TYPE = uriRow + tableName;
	}

}
