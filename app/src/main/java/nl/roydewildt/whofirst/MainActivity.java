package nl.roydewildt.whofirst;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.tbouron.shakedetector.library.ShakeDetector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    View v;
    private ArrayList<String> people;
    public static String PREFS_NAME = "WhoFirstPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showFirstTimeLaunchScreen();


        getSavedPeople();

        ShakeDetector.create(this, new ShakeDetector.OnShakeListener() {
            @Override
            public void OnShake() {
                TextView tv = (TextView) findViewById(R.id.shake_text);

                // import if user reset the application
                if(people.size() == 0){
                    getSavedPeople();
                }

                // Show random user from people in snackbar and textview, if people not empty
                if(people.size() > 0) {
                    String randPerson = people.get(new Random().nextInt(people.size()));
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.main_view), "You have shoken " + randPerson, Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                    snackbar.show();
                    tv.setText(randPerson);
                } else {
                    tv.setText("Empty :(");
                }
            }
        });
    }

    private void getSavedPeople() {
        people = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE );
        final Set peopleSet = prefs.getStringSet("people", new HashSet<String>());
        people.addAll(peopleSet);
    }

    private void showFirstTimeLaunchScreen() {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE );
                boolean isFirstStart = prefs.getBoolean("firstStart", true);
                if (isFirstStart) {
                    Intent i = new Intent(MainActivity.this, IntroActivity.class);
                    startActivity(i);

                    SharedPreferences.Editor e = prefs.edit();
                    e.putBoolean("firstStart", false);
                    e.commit();
                }
            }
        });

        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE );
            SharedPreferences.Editor prefEditor = prefs.edit();
            prefEditor.putStringSet("people", new HashSet<String>());
            prefEditor.putBoolean("firstStart", true);
            prefEditor.commit();

            // cleanup and restart activity
            this.recreate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ShakeDetector.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShakeDetector.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShakeDetector.destroy();
    }
}
