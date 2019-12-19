package xyz.juanrodriguez.www.wordsworthtoremember3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper  {
    //Table
    public static final String TABLE_NAME = "words";
    //Column
    public static final String COL_WORD_ID = "_id";
    public static final String COL_WORD_WORD = "_word";
    public static final String COL_WORD_DEFINITION = "_definition";
    static final String[] columns = new String[]{DBHelper.COL_WORD_ID,
            DBHelper.COL_WORD_WORD, DBHelper.COL_WORD_DEFINITION};

    //DB info
    private static final String DATABASE_NAME = "emp.db";
    private static final int DATABASE_VERSION = 1;

    //SQLite statement
    private static final String DATABASE_CREATE =  "CREATE TABLE " + TABLE_NAME
            + "(" + COL_WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_WORD_WORD + " TEXT NOT NULL, " + COL_WORD_DEFINITION + " TEXT NOT NULL);";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("DB Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
        System.out.println("Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        System.out.println("DB Updated");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}