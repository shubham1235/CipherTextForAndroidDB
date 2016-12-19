package com.example.shubham_v.ciphertextforandroiddb;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;




public class MainActivity extends AppCompatActivity {

    EditText employee_name, employee_id , employee_department;
    TextView showdatabase_textView;
    Button insert_button, update_button,fetch_button,numberOfRowbtn;
    public  File databaseFile;
    SQLiteDatabase db;
    DataBaseClassWithSqlCipher dataBaseClassWithSqlCipher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        employee_name = (EditText)findViewById(R.id.emplotyee_name_id);
        employee_id =(EditText)findViewById(R.id.employeID_id);
        employee_department = (EditText)findViewById(R.id.department_id);
        showdatabase_textView = (TextView)findViewById(R.id.data_text_id);
        insert_button = (Button)findViewById(R.id.insert_id);
        update_button = (Button)findViewById(R.id.update_id);
        fetch_button = (Button)findViewById(R.id.fetch_id);
        employee_id.setInputType(InputType.TYPE_CLASS_NUMBER);
        numberOfRowbtn = (Button)findViewById(R.id.number_button_id);



        SQLiteDatabase.loadLibs(getApplicationContext());
        databaseFile = getDatabasePath("Quantum.db");
        databaseFile.mkdirs();
        databaseFile.delete();
        db = SQLiteDatabase.openOrCreateDatabase(databaseFile,"rocklay",null);

         dataBaseClassWithSqlCipher = new DataBaseClassWithSqlCipher(this);

        insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = employee_id.getText().toString();
                String name = employee_name.getText().toString();
                String department = employee_department.getText().toString();

               long inserAuthentication = dataBaseClassWithSqlCipher.InsertDataDB(id,name,department);

                employee_id.setText("");
                employee_name.setText("");
                employee_department.setText("");


                if(inserAuthentication != -1 )
                {
                    Toast.makeText(MainActivity.this, "data insert", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "data not insert", Toast.LENGTH_SHORT).show();
                }

            }
        });


        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Long UpdateAuthentication =  dataBaseClassWithSqlCipher.UpdateEmployeeDetail(employee_id.getText().toString(),employee_name.getText().toString(),employee_department.getText().toString());

                if(UpdateAuthentication != -1 )
                {
                    Toast.makeText(MainActivity.this, "data update", Toast.LENGTH_SHORT).show();

                    String alldata;
                    Cursor cursor = dataBaseClassWithSqlCipher.GetData(employee_id.getText().toString());
                    cursor.moveToFirst();
                    if(cursor.getCount() > 0) {
                        //do stuff

                        alldata = "id :" + cursor.getString(cursor.getColumnIndex("employee_id")) + "     name :" + cursor.getString(cursor.getColumnIndex("employee_name")) + "     department:" + cursor.getString(cursor.getColumnIndex("employee_department"));
                        showdatabase_textView.setText(alldata);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "update  cursor is empty ", Toast.LENGTH_SHORT).show();
                        cursor.close();
                    }

                }
                else {
                    Toast.makeText(MainActivity.this, "data not update", Toast.LENGTH_SHORT).show();
                }

            }
        });

        fetch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String alldata;
                Cursor cursor = dataBaseClassWithSqlCipher.GetData(employee_id.getText().toString());
                cursor.moveToFirst();
                if(cursor.getCount() > 0) {
                    //do stuff

                    alldata = "id :" + cursor.getString(cursor.getColumnIndex("employee_id")) + "     name :" + cursor.getString(cursor.getColumnIndex("employee_name")) + "     department:" + cursor.getString(cursor.getColumnIndex("employee_department"));
                    showdatabase_textView.setText(alldata);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "yar cursor is empty ", Toast.LENGTH_SHORT).show();
                cursor.close();
                }
            }
        });

        numberOfRowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               int numberofrow = dataBaseClassWithSqlCipher.numberOfRows();

                String numberstring   = String.valueOf(numberofrow);
                Toast.makeText(MainActivity.this,numberstring, Toast.LENGTH_SHORT).show();

            }
        });

    }

}
