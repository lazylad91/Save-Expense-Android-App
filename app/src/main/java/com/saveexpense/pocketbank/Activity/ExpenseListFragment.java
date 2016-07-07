package com.saveexpense.pocketbank.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saveexpense.pocketbank.Database.DBProvider;
import com.saveexpense.pocketbank.model.Expense;

import java.util.List;

public class ExpenseListFragment extends Fragment {

    private ExpenseAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_expense_list_fragment,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.expense_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setLayoutManager(layoutManager);
        updateUI();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        DBProvider db = new DBProvider(getActivity());
        List<Expense> expenses = db.getAllExpense();

        if (mAdapter == null) {
            mAdapter = new ExpenseAdapter(expenses);
            recyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


    private class ExpenseHolder extends RecyclerView.ViewHolder
           implements View.OnClickListener {

        private TextView mcategoryTextView;
        private TextView mDateTextView;
        private TextView mPriceTextView;

        private Expense mExpense;

        public ExpenseHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mcategoryTextView = (TextView) itemView.findViewById(R.id.categoryTextView);
            mDateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
            mPriceTextView = (TextView) itemView.findViewById(R.id.priceTextView);
        }

        public void bindExpense(Expense expense) {
            mExpense = expense;
            mcategoryTextView.setText(mExpense.getCategory());
            mDateTextView.setText(mExpense.getDate().toString());
            mPriceTextView.setText(mExpense.getPrice().toString());
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ExpenseDetail.class);
            intent.putExtra("Expense_Id",mExpense.getId());
            startActivity(intent);
        }


    }

    private class ExpenseAdapter extends RecyclerView.Adapter<ExpenseHolder> {
        private List<Expense> mExpenses;

        public ExpenseAdapter(List<Expense> expenses) {
            mExpenses = expenses;
        }

        @Override
        public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_expense, parent, false);
            return new ExpenseHolder(view);
        }

        @Override
        public void onBindViewHolder(ExpenseHolder holder, int position) {
            Expense expense = mExpenses.get(position);
            holder.bindExpense(expense);
        }

        @Override
        public int getItemCount() {
            return mExpenses.size();
        }

    }

}
