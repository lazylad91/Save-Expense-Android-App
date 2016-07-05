package com.saveexpense.pocketbank.Activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.saveexpense.pocketbank.Database.DBProvider;
import com.saveexpense.pocketbank.model.Expense;

import java.util.Date;
import java.util.List;

public class SaveExpenseFragment extends Fragment {

    private String category="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_save_expense_fragment,container,false);
        Button button = (Button) view.findViewById(R.id.Add);
        final EditText priceText = (EditText) view.findViewById(R.id.price);
        final EditText noteText = (EditText) view.findViewById(R.id.note);
        final Spinner categoryText = (Spinner) view.findViewById(R.id.category);
        EditText dateText = (EditText) view.findViewById(R.id.date);
        dateText.setEnabled(false);
        categoryText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
                if(!"Select Category".equals(parent.getItemAtPosition(pos).toString())) {
                    Toast.makeText(parent.getContext(),
                            "You Selected : " + parent.getItemAtPosition(pos).toString(),
                            Toast.LENGTH_SHORT).show();
                    category = parent.getItemAtPosition(pos).toString();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                if( priceText.getText().toString().length() == 0 ) {
                    priceText.setError("Amount is required!");
                    valid=false;
                }
                if( noteText.getText().toString().length() == 0 ) {
                    noteText.setError("Note is required!");
                    valid=false;
                }
                if( category.equals("") ){
                    noteText.setError( "Note is required!" );
                    valid=false;
                }
                if(valid) {
                    DBProvider db = new DBProvider(getActivity());
                    Expense expense = new Expense();
                    expense.setNote(noteText.getText().toString());
                    expense.setPrice(Float.parseFloat(priceText.getText().toString()));
                    expense.setDate(new Date());
                    expense.setCategory(category);
                    db.addExpense(expense);
                    clearForm((ViewGroup) view.findViewById(R.id.fragment1));
                    Toast.makeText(getContext(),
                            "Expense Added",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }




    // For Clearing forms edit Text
    private void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }
}
