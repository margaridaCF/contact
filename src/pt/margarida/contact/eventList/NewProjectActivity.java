package pt.margarida.contact.eventList;

import pt.margarida.contact.db.ContactCollectContract;
import pt.margarida.contact.db.ContactCollectorUriMatcher;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class NewProjectActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_project);
		
		// TODO deal with store information on pause
	}

	public void saveProject(View view) {
		// Get values from UI
    	EditText projectNameField = (EditText) findViewById(R.id.projectName);
    	String projectName = projectNameField.getText().toString();
    	// Check Values 
    	int errors = 0;
    	if(projectName == null || projectName.length() == 0 || projectName.equalsIgnoreCase(getResources().getText(R.string.project_name).toString())){
    		errors++;
    		projectNameField.setError(getResources().getText(R.string.project_name).toString());
    	}
    	// show error if need OR Insert in DB
    	if(errors == 0){
    		ContentValues values = new ContentValues();
        	values.put(ContactCollectContract.Project.COL_NAME, projectName);
//        	values.put(ContactCollectContract.Project.COL_START_DATE, start.getTime());
//        	values.put(ContactCollectContract.Project.COL_END_DATE, end.getTime());
        	values.put(ContactCollectContract.Project.COL_ENDLESS, 0);
        	values.put(ContactCollectContract.Project.COL_DELETED, 0);
        	Uri inserUri = Uri.parse(ContactCollectContract.AUTHORITY + ContactCollectContract.Project.tableName);
        	getContentResolver().insert(ContactCollectContract.Project.CONTENT_URI, values);
    	}
		Intent intent = new Intent(this, EventListActivity.class);
		startActivity(intent);
	}
}
