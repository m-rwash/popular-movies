package com.rwash.popularmovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.rwash.popularmovieapp.fragments.MoviesGridFragment;

public class MainActivity extends AppCompatActivity
{
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public static boolean twoPane=false;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null)
        {
            isTwoPane();
            if(twoPane)
            {
                Log.v(LOG_TAG,"TWO PANE");
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frameLayoutMoviesGrid,new MoviesGridFragment()).commit();
            }
            else
            {
                Log.v(LOG_TAG,"ONE PANE");
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container,new MoviesGridFragment()).commit();
            }

        }

    }

    private void isTwoPane()
    {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frameLayoutDetailContainer);
        if(frameLayout != null)
            twoPane = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings)
        {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}