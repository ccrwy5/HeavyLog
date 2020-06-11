package edu.missouri.testing.ui.calculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import edu.missouri.testing.MainActivity;
import edu.missouri.testing.R;

public class CalculatorFragment extends Fragment {

    private CalculatorViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = ViewModelProviders.of(this).get(CalculatorViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calculator, container, false);

        final TextView textView = root.findViewById(R.id.text_notifications);
        final EditText repsEditText = root.findViewById(R.id.repsEditText);
        final EditText poundsEditText = root.findViewById(R.id.poundsEditText);

        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        Button calculateButton = (Button) root.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String poundsString = poundsEditText.getText().toString();
                Double pounds = Double.parseDouble(poundsString);

                String repsString = repsEditText.getText().toString();
                Double reps = Double.parseDouble(repsString);

                Double ORM = pounds * (1 + (reps/30));
                showErrorDialog(ORM);

            }
        });

        Button clearButton = (Button) root.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                poundsEditText.setText("");
                repsEditText.setText("");
            }
        });

        return root;

        }


    public void showErrorDialog(Double ORM){
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Your 1-Rep Max");
        alertDialog.setMessage("Your 1-Rep Max is " + ORM.toString() + " pounds!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        }
    }

