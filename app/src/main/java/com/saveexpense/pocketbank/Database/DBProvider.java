package com.saveexpense.pocketbank.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.saveexpense.pocketbank.model.Expense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Parteek on 7/2/2016.
 */
public class DBProvider extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "pocketBank";

    // Contacts table name
    private static final String TABLE_EXPENSE = "expense";

    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PRICE = "price";
    private static final String KEY_NOTE = "note";
    private static final String KEY_DATE = "date";
    private static final String KEY_CATEGORY = "category";

       public DBProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSE_TABLE = "CREATE TABLE " + TABLE_EXPENSE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PRICE + " FLOAT,"
                + KEY_NOTE + " TEXT," + KEY_DATE + " LONG," +  KEY_CATEGORY + " TEXT" + ")";
        Log.d("query", CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_EXPENSE_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        // Creating tables again
        onCreate(db);
    }

    // Adding new expense
    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRICE, expense.getPrice()); // Price
        values.put(KEY_NOTE, expense.getNote()); // Expense Note
        values.put(KEY_DATE, expense.getDate().getTime()); // Expense Date
        values.put(KEY_CATEGORY,expense.getCategory()); // Expense Category
        // Inserting Row
        db.insert(TABLE_EXPENSE, null, values);
        db.close(); // Closing database connection
    }

    // Getting one Expense
    public Expense getExpense(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXPENSE, new String[]{KEY_ID,
                        KEY_PRICE, KEY_DATE,KEY_NOTE,KEY_CATEGORY}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Expense expense = new Expense();
        expense.setId(Integer.parseInt(cursor.getString(0)));
        expense.setPrice(cursor.getFloat(1));
        expense.setDate(new Date(cursor.getLong(2)));
        expense.setNote(cursor.getString(3));
        expense.setCategory((cursor.getString(4)));
        // return expense
        return expense;
    }

    // Getting All Expense
    public List<Expense> getAllExpense() {
        List<Expense> expenseList = new ArrayList<Expense>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EXPENSE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(0)));
                expense.setPrice(cursor.getFloat(1));
                expense.setDate(new Date(cursor.getLong(3)));
                expense.setNote(cursor.getString(2));
                expense.setCategory((cursor.getString(4)));
                // Adding expense to list
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenseList;
    }

    // Getting Expense Count
    public int getExpenseCount() {
        String countQuery = "SELECT  * FROM " + TABLE_EXPENSE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating a Expense
    public int updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PRICE, expense.getPrice());
        values.put(KEY_NOTE, expense.getNote());
        values.put(KEY_DATE, expense.getDate().getTime());
        values.put(KEY_CATEGORY,expense.getCategory());
        // updating row
        return db.update(TABLE_EXPENSE, values, KEY_ID + " = ?",
                new String[]{String.valueOf(expense.getId())});
    }

    // Deleting a expense
    public void deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSE, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
}
