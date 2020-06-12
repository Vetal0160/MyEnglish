package com.example.myenglish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnAdd, btnNext, Memorize, btnClear, btnUpdate, btnDelete;
    EditText AddWords, AddTranslate;
    TextView tvWords,tvWordsrus;
    CheckBox checkBox;

    DBHelper dbHelper;
    int randomNum, English, Russian;
    public Cursor cursor;
    String idrand, Eng, Rus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnNext = (Button) findViewById(R.id.next);
        btnNext.setOnClickListener(this);

        Memorize = (Button) findViewById(R.id.btnMemorize);
        Memorize.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);

        AddWords = (EditText) findViewById(R.id.AddWords);
        AddTranslate = (EditText) findViewById(R.id.AddTranslate);
        tvWords = (TextView) findViewById(R.id.tvWords);
        tvWordsrus = (TextView) findViewById(R.id.tvWordsrus);

        checkBox = (CheckBox) findViewById(R.id.checkBox);

        dbHelper = new DBHelper(this);
        Check();
    }
     public void Check(){
        if
        (checkBox.isChecked()) {
            btnClear.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }

        else {
            btnClear.setVisibility(View.INVISIBLE);
            btnUpdate.setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }
}
public int GetRand(){
        Random rand = new Random();
        return randomNum = rand.nextInt(cursor.getCount());
}
    @Override
    public void onClick(View v) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Eng = AddWords.getText().toString();
        Rus = AddTranslate.getText().toString();
        cursor = database.query(DBHelper.TABLE_WORDS, null, null, null, null, null, null);
      //  idrand = String.valueOf(GetRand());


        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {

            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_WORDS, Eng);
                contentValues.put(DBHelper.KEY_TRANSLATE, Rus);
                contentValues.put(DBHelper.KEY_BOOLEAN, 0);

                database.insert(DBHelper.TABLE_WORDS, null, contentValues);
                break;

            case R.id.next:
                int Id = cursor.getColumnIndex(DBHelper.KEY_ID);
                English = cursor.getColumnIndex(DBHelper.KEY_WORDS);
                Russian = cursor.getColumnIndex(DBHelper.KEY_TRANSLATE);
                int Boolean = cursor.getColumnIndex(DBHelper.KEY_BOOLEAN);

                if (cursor.getCount() != 0) {
                    if (cursor.getInt(Boolean) == 1){
                        for (int i = 0; i <= cursor.getCount(); i++ )
                            if (cursor.getInt(Boolean) == 0) {
                                cursor.moveToPosition(i);
                                tvWords.setText(cursor.getInt(Id) +"   "+ cursor.getString(English));
                                tvWordsrus.setText(cursor.getString(Russian));
                                break;
                            }
                    break;
                    }
                    else {
                        tvWords.setText(cursor.getInt(Id) +"   "+ cursor.getString(English));
                        tvWordsrus.setText(cursor.getString(Russian));
                    }
                }
                cursor.close();
                break;

            case R.id.btnMemorize:
              //  database.execSQL("DROP TABLE IF EXISTS "+DBHelper.TABLE_WORDS);
               // database.delete(DBHelper.TABLE_WORDS, null, null);;
                idrand = String.valueOf(randomNum+1);
                contentValues.put(DBHelper.KEY_BOOLEAN, 1);
                database.update(DBHelper.TABLE_WORDS, contentValues, "_id=?", new String[] {idrand});
            break;
            case R.id.btnClear:
                database.delete(DBHelper.TABLE_WORDS, null, null);
                tvWordsrus.setText("");
                tvWords.setText("");
                break;
            case R.id.btnUpdate:
                contentValues.put(DBHelper.KEY_WORDS, Eng);
                contentValues.put(DBHelper.KEY_TRANSLATE, Rus);
                contentValues.put(DBHelper.KEY_BOOLEAN, 0);
                database.update(DBHelper.TABLE_WORDS, contentValues, DBHelper.KEY_ID + "= ?", new String[] {idrand});
                break;
            case R.id.btnDelete:
                database.delete(DBHelper.TABLE_WORDS, DBHelper.KEY_ID + "=" + idrand, null);

        }
        dbHelper.close();
        Check();
    }
}