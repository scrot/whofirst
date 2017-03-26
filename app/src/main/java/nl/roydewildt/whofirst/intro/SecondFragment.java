package nl.roydewildt.whofirst.intro;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import nl.roydewildt.whofirst.MainActivity;
import nl.roydewildt.whofirst.R;

import static android.content.Context.MODE_PRIVATE;

public class SecondFragment extends Fragment {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> people = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.second_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.intro_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick(v);
            }
        });

        ListView lv = (ListView) v.findViewById(R.id.people_list);

        people = new ArrayList<>();
        SharedPreferences prefs = getContext().getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE );
        Set peopleSet = prefs.getStringSet("people", new HashSet<String>());
        people.addAll(peopleSet);

        adapter = new ArrayAdapter<>(v.getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, people);
        lv.setAdapter(adapter);

        return v;
    }


    public void onFabClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add person");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Add person and notify change
                adapter.add(input.getText().toString());

                // Save people in SharedPreferences
                SharedPreferences prefs = getContext().getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE );
                SharedPreferences.Editor editor = prefs.edit();
                HashSet<String> peopleSet = new HashSet<>();
                peopleSet.addAll(people);
                editor.putStringSet("people", peopleSet);
                editor.commit();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
