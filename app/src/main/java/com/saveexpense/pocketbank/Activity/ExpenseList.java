package com.saveexpense.pocketbank.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

public class ExpenseList extends SingleFragmentActivity {

    @Override
    protected int getLayoutResId(){
      return R.layout.activity_expense_list;
    }

    @Override
    protected int getFragmentContainerId(){
        return R.id.fragment_container2;
    }

    @Override
    protected Fragment createFragment() {
        return new ExpenseListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SaveExpense.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
