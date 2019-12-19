package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBController {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBController(Context context){
        dbHelper = new DBHelper(context);
    }

    public void close(){
        dbHelper.close();
    }

    public void addWord(Word word){
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(dbHelper.COL_WORD_WORD, word.get_word());
        values.put(dbHelper.COL_WORD_DEFINITION, word.get_definition());

        database.insert(DBHelper.TABLE_NAME, null, values);

        System.out.println("Record Added");
        database.close();
    }

    public Word getWord(int _id){
        database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DBHelper.TABLE_NAME, DBHelper.columns, DBHelper.COL_WORD_ID + "=?", new String[]{String.valueOf(_id)}, null, null, null);

        Word word = new Word(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2));

        return word;
    }

    public List<Word> getAllWords() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<Word> wordsList = new ArrayList<Word>();
        String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Word word = new Word(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2));

                wordsList.add(word);
            }while (cursor.moveToNext());
        }

        return wordsList;
    }

    public int updateWord(Word word){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBHelper.COL_WORD_WORD, word.get_word());
        values.put(DBHelper.COL_WORD_DEFINITION, word.get_definition());

        return db.update(DBHelper.TABLE_NAME, values,DBHelper.COL_WORD_ID + " = ?",
                new String[]{String.valueOf(word.get_id())});
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.TABLE_NAME, DBHelper.COL_WORD_ID + " = ?",
                new String[]{String.valueOf(word.get_id())});

        System.out.println("Record Deleted");
        db.close();
    }

    // Deleting single employee
    public void deleteWord(int _id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(DBHelper.TABLE_NAME, DBHelper.COL_WORD_ID + " = ?",
                new String[]{String.valueOf(_id)});
        db.close();
    }
}
