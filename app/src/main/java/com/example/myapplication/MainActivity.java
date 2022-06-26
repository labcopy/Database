package com.example.myapplication;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText name = (EditText) findViewById(R.id.nameEdit);
        EditText roll = (EditText) findViewById(R.id.rollEdit);
        Button insert = (Button) findViewById(R.id.insert);
        Button delete = (Button) findViewById(R.id.delete);
        Button view = (Button) findViewById(R.id.view);
        Button update = (Button) findViewById(R.id.update);
        SQLiteDatabase db = openOrCreateDatabase("studentdb", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists student(name VARCHAR(20),roll INTEGER);");
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.execSQL("insert into student values( '" + name.getText() +  "'," + roll.getText() + ");");
                    name.setText("");
                    roll.setText("");
                }
                catch (SQLException e){
                    Toast.makeText(MainActivity.this,"Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.execSQL("delete from student where roll = " + roll.getText());
                name.setText("");
                roll.setText("");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.execSQL("update student set name = '" + name.getText() + "' where roll = " + roll.getText());
                    name.setText("");
                    roll.setText("");
                    Toast.makeText(MainActivity.this,"Updated",Toast.LENGTH_SHORT).show();
                }
                catch (SQLException e) {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor c = db.rawQuery("select * from student",null);
                StringBuffer buffer = new StringBuffer();
                while(c.moveToNext()){
                    buffer.append("name: " + c.getString(0));
                    buffer.append("\nroll: " + c.getString(1) + "\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(buffer);
                builder.setTitle("Data");
                builder.show();
            }
        });
    }
}