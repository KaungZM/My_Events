package com.example.myevents;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

public class EditActivity extends AppCompatActivity {

    DatePickerDialog datePicker;
    EditText edit1;
    EditText edit2;
    EditText edit3;
    EditText edit4;
    EventDb eventDb;
    Intent intent;
    int position;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit Event");
        intent = getIntent();
        position = (int)intent.getExtras().get("position");
        if(intent.hasExtra("id")){
            id = (int)intent.getExtras().get("id");
        }
        getDatabase();
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
        edit3 = (EditText) findViewById(R.id.edit3);
        edit4 = (EditText) findViewById(R.id.edit4);
        edit2.setInputType(InputType.TYPE_NULL);
        edit2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(EditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edit2.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                            }
                        }, year, month, day);
                datePicker.show();
            };
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setEdits();
    }

    public void setEdits() {
        if(position > -1) {
            edit1.setText(MainActivity.adapter.getList().get(position).getName());
            edit2.setText(MainActivity.adapter.getList().get(position).getDate());
            edit3.setText(MainActivity.adapter.getList().get(position).getLocation());
            edit4.setText(MainActivity.adapter.getList().get(position).getDescription());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if (position > -1) {
                    update();
                } else {
                    done();
                }
                break;
            case R.id.delete:
                delete();
        }
        return true;
    }

    private void getDatabase() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                eventDb = EventDb.getDatabase(getApplicationContext());
            }
        });
    }

    private void done() {
        Event event = new Event(edit1.getText().toString(), edit2.getText().toString(), edit3.getText().toString(), edit4.getText().toString());
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if (eventDb == null) {
                    getDatabase();
                }
                eventDb.eventDao().add(event);
            }
        });
        finish();
    }

        private void update() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if(eventDb == null) {
                    getDatabase();
                }
//                Log.d("debug", "Update with query");
//                eventDb.eventDao().updateQuery(id, edit1.getText().toString(), edit2.getText().toString(), edit3.getText().toString(), edit4.getText().toString());
                Log.d("debug", "update room style");
                Event event = new Event(id, edit1.getText().toString(), edit2.getText().toString(), edit3.getText().toString(), edit4.getText().toString());
                eventDb.eventDao().update(event);
            }
        });
        finish();
    }

    private void delete() {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                eventDb.eventDao().delete(MainActivity.adapter.getList().get(position));
                finish();
            }
        });
    }
}