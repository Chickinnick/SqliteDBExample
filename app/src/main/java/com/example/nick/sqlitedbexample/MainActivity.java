package com.example.nick.sqlitedbexample;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.Logger;


public class MainActivity extends Activity implements View.OnClickListener{

    Button buttonAdd;
    Button buttonRead;
    Button buttonClear;
    Button buttonDelete;
    Button buttonUpdate;

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextId;


    DbHelper dbHelper;

    TextView textView;
    private String LOG_TAG = "Mytag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = (Button)findViewById(R.id.btn_add);
        buttonAdd.setOnClickListener(this);

        buttonRead = (Button)findViewById(R.id.btn_read);
        buttonRead.setOnClickListener(this);

        buttonClear = (Button)findViewById(R.id.btn_clear);
        buttonClear.setOnClickListener(this);

        buttonDelete = (Button)findViewById(R.id.btn_delete);
        buttonDelete.setOnClickListener(this);

        buttonUpdate = (Button)findViewById(R.id.btn_update);
        buttonDelete.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.edit_text_name);
        editTextEmail = (EditText) findViewById(R.id.edit_text_email);

        editTextId = (EditText) findViewById(R.id.edit_text_id);

        textView = (TextView) findViewById(R.id.log_txt_view);


        dbHelper = new DbHelper(this);


        buttonAdd.setOnClickListener(this);




    }


    @Override
    public void onClick(View v) {



        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String id= editTextId.getText().toString();
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        switch (v.getId()) {
            case R.id.btn_add:
                Log.d(LOG_TAG,"--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение


                cv.put("name", name);
                cv.put("email", email);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);
                textView.append("" +
                        "\nINSERTED IN TABLE  ID = " + rowID +
                        " \n name = " + cv.getAsString(name) +
                        "\nemail= " + cv.getAsString(email));
                                break;
            case R.id.btn_read:
                Log.d(LOG_TAG, "--- Rows in mytable: ---");
                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("mytable", null, null, null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if (c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("email");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", email = " + c.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла

                        textView.append(  "\nID = " + c.getInt(idColIndex)+
                                "\n name = " + c.getString(nameColIndex) +
                                        "\nemail= " + c.getString(emailColIndex));


                    } while (c.moveToNext());
                } else
                    Log.d(LOG_TAG, "0 rows");
                c.close();
                break;
            case R.id.btn_clear:
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG_TAG, "deleted rows count = " + clearCount);


                textView.append(
                        "\ndeleted rows count = " + clearCount
                );

                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }

    @Override
    protected void onDestroy() {

    }
}

