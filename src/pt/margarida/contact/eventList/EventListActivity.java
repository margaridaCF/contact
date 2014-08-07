package pt.margarida.contact.eventList;

import pt.margarida.contact.db.ContactCollectContract;
import pt.margarida.contact.db.ContactCollectorUriMatcher;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class EventListActivity extends ListActivity implements
    LoaderManager.LoaderCallbacks<Cursor> {
	// private Cursor cursor;
	private SimpleCursorAdapter adapter;

    static final String[] LIST_PROJECTION = new String[] {ContactCollectContract.Project._ID,
    	ContactCollectContract.Project.COL_NAME};

    // This is the select criteria
    static final String LIST_SELECTION = "((" + 
    		ContactCollectContract.Project.COL_NAME + " NOTNULL) )";

	private static final String URI_SEL_PROJECT = "pt.margarida.contact.selProject";


	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		this.getListView().setDividerHeight(2);
		fillData();
	}


	// Opens the second activity if an entry is clicked
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, EventListActivity.class);
		// Pass id
		Uri projectUri = Uri.parse(ContactCollectContract.Project.CONTENT_ITEM_TYPE + "/" + id);
		i.putExtra(URI_SEL_PROJECT, projectUri);

		startActivity(i);
	}
	
	public void createProject(View view) {
		Intent intent = new Intent(this, NewProjectActivity.class);
		startActivity(intent);
	}

	private void fillData() {
		// Fields from the database (projection)
		// Must include the _id column for the adapter to work
		String[] from = new String[] { ContactCollectContract.Project.COL_NAME, ContactCollectContract.Project._ID };
		// Fields on the UI to which we map
		int[] to = new int[] { R.id.label };

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, R.layout.row, null, from,
				to, 0);
		setListAdapter(adapter);
	}

	// creates a new loader after the initLoader () call
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { ContactCollectContract.Project.COL_NAME, ContactCollectContract.Project._ID};
//				ContactCollectContract.Project.COL_END_DATE, ContactCollectContract.Project.COL_START_DATE, ContactCollectContract.Project.COL_ENDLESS};
		CursorLoader cursorLoader = new CursorLoader(this,
				ContactCollectorUriMatcher.UriCC.PROJECT.getUri(), projection, null, null, ContactCollectContract.Project.COL_NAME+" DESC");
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// data is not available anymore, delete reference
		adapter.swapCursor(null);
	}

} 
