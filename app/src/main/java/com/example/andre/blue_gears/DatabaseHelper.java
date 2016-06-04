package com.example.andre.blue_gears;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andre on 6/2/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_bluegears";

    // Table Names
    private static final String DB_TABLE = "table_bluegears";

    // column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATETIME = "datetime";
    private static final String KEY_DATETIME_SELECTECTED = "datetime_selected";
    private static final String KEY_IMAGE = "image_data";

    // Table create statement
    private static final String CREATE_TABLE_IMAGE = "CREATE TABLE " + DB_TABLE + "("+
            KEY_ID+" INTEGER PRIMARY KEY," +
            KEY_NAME + " TEXT," +
            KEY_DESCRIPTION + " TEXT," +
            KEY_DATETIME + " TEXT," +
            KEY_DATETIME_SELECTECTED + " TEXT," +
            KEY_IMAGE + " BLOB"+
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating table
        db.execSQL(CREATE_TABLE_IMAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
        onCreate(db);
    }

    public void addItens(ArrayList<ItemListView> item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values;
        int i;
        for (i = 0; i < item.size(); i++) {
            values = new ContentValues();
            values.put(KEY_NAME, item.get(i).getName());
            values.put(KEY_DESCRIPTION, item.get(i).getDescription());
            values.put(KEY_DATETIME, item.get(i).getDatetime());
            values.put(KEY_DATETIME_SELECTECTED, item.get(i).getDatetime_selected());
            values.put(KEY_IMAGE, item.get(i).getImg());
            db.insert(DB_TABLE, null, values);
        }
        db.close(); // Closing database connection
    }

    // Getting All Contacts
    public List<ItemListView> getAllItens() {
        List<ItemListView> itensList = new ArrayList<ItemListView>();
// Select All Query
        String selectQuery = "SELECT * FROM " +DB_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemListView item = new ItemListView();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setDescription(cursor.getString(2));
                item.setDatetime(cursor.getString(3));
                item.setDatetime_selected(cursor.getString(4));
                item.setImg(cursor.getBlob(5));

                itensList.add(item);
            } while (cursor.moveToNext());
        }

        db.close();
        return itensList;

    }

    public void removeAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DB_TABLE);
        db.close();
    }
}
