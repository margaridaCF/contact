package pt.margarida.contact.eventList;

import pt.margarida.contact.db.DbContentProvider;
import pt.margarida.contact.db.EventContract;
import pt.margarida.contact.eventCollect.EventCollectActivity;
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

/*
 * TodosOverviewActivity displays the existing todo items
 * in a list
 * 
 * You can create new ones via the ActionBar entry "Insert"
 * You can delete existing ones via a long press on the item
 */

public class EventListActivity extends ListActivity implements
    LoaderManager.LoaderCallbacks<Cursor> {
	// private Cursor cursor;
	private SimpleCursorAdapter adapter;


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
		Intent i = new Intent(this, EventCollectActivity.class);
		Uri todoUri = Uri.parse(DbContentProvider.CONTENT_URI + "/" + id);
		i.putExtra(DbContentProvider.EVENT_TYPE, todoUri);

		startActivity(i);
	}



	private void fillData() {

		// Fields from the database (projection)
		// Must include the _id column for the adapter to work
		String[] from = new String[] { EventContract.COLUMN_NAME, EventContract._ID };
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
		String[] projection = { EventContract.COLUMN_NAME,
				EventContract.COLUMN_END_DATE, EventContract.COLUMN_START_DATE, EventContract.COLUMN_ENDLESS};
		CursorLoader cursorLoader = new CursorLoader(this,
				DbContentProvider.CONTENT_URI, projection, null, null, null);
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
