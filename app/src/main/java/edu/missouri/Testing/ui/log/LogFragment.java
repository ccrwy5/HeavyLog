package edu.missouri.testing.ui.log;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.missouri.testing.MainActivity;
import edu.missouri.testing.R;

public class LogFragment extends Fragment {

    private LogViewModel dashboardViewModel;
    SharedPreferences sharedPreferences;
    static final String Entry = "entryKey";

    DatabaseHelper mDatabaseHelper;
    private Button addButton;
    private Button viewDataButton;
    private EditText logEntryEditText;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel =
                ViewModelProviders.of(this).get(LogViewModel.class);
        View root = inflater.inflate(R.layout.fragment_log, container, false);


        final TextView textView = root.findViewById(R.id.text_dashboard);
        logEntryEditText = root.findViewById(R.id.logEditText);
        addButton = root.findViewById(R.id.saveButton);
        viewDataButton = root.findViewById(R.id.lastWorkout);
        mDatabaseHelper = new DatabaseHelper(this.getActivity());


        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String newEntry = logEntryEditText.getText().toString();

                if(logEntryEditText.length() != 0){
                    AddData(newEntry);
                    logEntryEditText.setText("");
                }else{
                    toastMessage("You must put something in the text field!");
                }
            }
        });

        viewDataButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), ListDataActivity.class);
                startActivity(intent);
            }
        });



        return root;
    }

    public void AddData(String newEntry){
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if(insertData){
            toastMessage("Log Successful");
        }else{
            toastMessage("Log Unsuccessful");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();
    }


}