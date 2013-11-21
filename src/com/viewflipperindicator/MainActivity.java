package com.viewflipperindicator;

import com.example.myfirstapp.R;
import com.pageIndicator.CirclePageIndicator;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	private CirclePageIndicator indic ;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        indic = (CirclePageIndicator) findViewById(R.id.viewflowindic);
		
		indic.setViewPager(6);
		indic.setCurrentItem(2);
    }


    public void gotoScrollImg(View view){
    	Intent intent = new Intent(MainActivity.this, SlideActivity.class);
    	//Start the Second Activity
    	startActivity(intent);
    }
   


	private void openSettings() {
		// TODO Auto-generated method stub
		
	}


	private void openSearch() {
		// TODO Auto-generated method stub
		
	}
    
    
}
