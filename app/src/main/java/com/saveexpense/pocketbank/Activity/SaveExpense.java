package com.saveexpense.pocketbank.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;

import com.saveexpense.pocketbank.Activity.SingleFragmentActivity;

import static com.saveexpense.pocketbank.Activity.R.id.fragment_container;


public class SaveExpense extends SingleFragmentActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_save_expense;
    }

    @Override
    protected int getFragmentContainerId(){
        return fragment_container;
    }

    @Override
    protected Fragment createFragment() {
        return new SaveExpenseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ExpenseList.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
