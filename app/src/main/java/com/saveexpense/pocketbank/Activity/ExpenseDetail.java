package com.saveexpense.pocketbank.Activity;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.saveexpense.pocketbank.Database.DBProvider;
import com.saveexpense.pocketbank.model.Expense;

import java.util.UUID;


public class ExpenseDetail extends AppCompatActivity {

    DBProvider db;
    int expenseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);
        db = new DBProvider(this);
        expenseId = (int) getIntent().getSerializableExtra("Expense_Id");
        DBProvider db = new DBProvider(this);
        Expense expense = db.getExpense(expenseId);
        TextView dateTextView = (TextView) findViewById(R.id.dateTextView1);
        TextView priceTextView = (TextView) findViewById(R.id.priceTextView1);
        TextView noteTextView = (TextView) findViewById(R.id.noteTextView1);
        dateTextView.setText(expense.getDate().toString());
        priceTextView.setText(expense.getPrice().toString()+" $");
        noteTextView.setText("  "+expense.getNote());
        ImageView imgView = (ImageView) findViewById(R.id.imageView);

       if(expense.getCategory().equals("Food")){
            imgView.setImageResource(R.drawable.ic_restaurant_white_24dp);
        }
        else if(expense.getCategory().equals("Transport")){
            imgView.setImageResource(R.drawable.ic_directions_bus_white_24dp);
        }
        else if(expense.getCategory().equals("Gas")){
            imgView.setImageResource(R.drawable.ic_local_gas_station_white_24dp);
        }
        else if(expense.getCategory().equals("Bills")){
            imgView.setImageResource(R.drawable.ic_lightbulb_outline_white_24dp);
        }
       else if(expense.getCategory().equals("Grocery")){
           imgView.setImageResource(R.drawable.ic_local_grocery_store_white_24dp);
       }
       else if(expense.getCategory().equals("Shopping")){
           imgView.setImageResource(R.drawable.ic_shopping_cart_white_24dp);
       }
       else if(expense.getCategory().equals("Clubbing")){
           imgView.setImageResource(R.drawable.ic_local_drink_white_24dp);
       }




        Log.d("expense1",expense.toString());
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.expensemenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_favourite) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setTitle("Delete Transaction");
            alertDialogBuilder
                    .setMessage("Clicking Delete will delete this transaction!")
                    .setCancelable(false)
                    .setPositiveButton("Delete",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            db.deleteExpense(expenseId);
                            Intent intent = new Intent(ExpenseDetail.this,ExpenseList.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;
        }
        if (id == R.id.action_List) {
          // Back to list View
            Intent intent = new Intent(this,ExpenseList.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
