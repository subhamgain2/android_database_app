package com.modoxlab.subham.dbdemo;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etID, etFirstName, etLastName, etAddress, etSalary;
    private RadioButton rbAdd, rbUpdate, rbRemove;
    private TextView tvOutput;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.modoxlab.dedsec.dbdemo.R.layout.activity_main);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataBase.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dataBase.closeDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.modoxlab.dedsec.dbdemo.R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case com.modoxlab.dedsec.dbdemo.R.id.asListAll:
                StringBuilder stringBuilder = new StringBuilder();
                Cursor cursor = dataBase.getAll();
                if (cursor.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No records found!", Toast.LENGTH_SHORT).show();
                } else {
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        stringBuilder.append(cursor.getInt(cursor.getColumnIndex(dataBase.getId())));
                        stringBuilder.append(" - ");
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex(dataBase.getFirstName())));
                        stringBuilder.append(" - ");
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex(dataBase.getLastName())));
                        stringBuilder.append(" - ");
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex(dataBase.getAddress())));
                        stringBuilder.append(" - ");
                        stringBuilder.append(cursor.getDouble(cursor.getColumnIndex(dataBase.getSalary())));
                        stringBuilder.append(" - ");
                        stringBuilder.append("\n");
                    }
                    tvOutput.setText(stringBuilder.toString());
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {

        etID = (EditText) findViewById(com.modoxlab.dedsec.dbdemo.R.id.etID);
        etFirstName = (EditText) findViewById(com.modoxlab.dedsec.dbdemo.R.id.etFirstName);
        etLastName = (EditText) findViewById(com.modoxlab.dedsec.dbdemo.R.id.etLastName);
        etAddress = (EditText) findViewById(com.modoxlab.dedsec.dbdemo.R.id.etAddress);
        etSalary = (EditText) findViewById(com.modoxlab.dedsec.dbdemo.R.id.etSalary);
        rbAdd = (RadioButton) findViewById(com.modoxlab.dedsec.dbdemo.R.id.rbAdd);
        rbUpdate = (RadioButton) findViewById(com.modoxlab.dedsec.dbdemo.R.id.rbUpdate);
        rbRemove = (RadioButton) findViewById(com.modoxlab.dedsec.dbdemo.R.id.rbRemove);
        tvOutput = (TextView) findViewById(com.modoxlab.dedsec.dbdemo.R.id.tvOutput);
        dataBase = new DataBase(getApplicationContext());
    }

    public void cleanETs() {
        etID.setText("");
        etFirstName.setText("");
        etLastName.setText("");
        etAddress.setText("");
        etSalary.setText("");
        tvOutput.setText("");
    }

    public void radioButtonHandler(View view) {
        cleanETs();
        switch (view.getId()) {
            case com.modoxlab.dedsec.dbdemo.R.id.rbAdd:
                etID.setVisibility(View.GONE);
                etFirstName.setVisibility(View.VISIBLE);
                etLastName.setVisibility(View.VISIBLE);
                etAddress.setVisibility(View.VISIBLE);
                etSalary.setVisibility(View.VISIBLE);
                break;

            case com.modoxlab.dedsec.dbdemo.R.id.rbRemove:
                etID.setVisibility(View.VISIBLE);
                etFirstName.setVisibility(View.GONE);
                etLastName.setVisibility(View.GONE);
                etAddress.setVisibility(View.GONE);
                etSalary.setVisibility(View.GONE);
                break;

            case com.modoxlab.dedsec.dbdemo.R.id.rbSearch:
                etID.setVisibility(View.VISIBLE);
                etFirstName.setVisibility(View.GONE);
                etLastName.setVisibility(View.GONE);
                etAddress.setVisibility(View.GONE);
                etSalary.setVisibility(View.GONE);
                break;

            case com.modoxlab.dedsec.dbdemo.R.id.rbUpdate:
                etID.setVisibility(View.VISIBLE);
                etFirstName.setVisibility(View.VISIBLE);
                etLastName.setVisibility(View.VISIBLE);
                etAddress.setVisibility(View.VISIBLE);
                etSalary.setVisibility(View.VISIBLE);
        }
    }

    public void submitHandler(View view) {
        String etid = etID.getText().toString().trim();
        String etfn = etFirstName.getText().toString().trim();
        String etln = etLastName.getText().toString().trim();
        String etaddr = etAddress.getText().toString().trim();
        String etsal = etSalary.getText().toString().trim();
        Boolean id = etid.matches("");
        Boolean fn = etfn.matches("");
        Boolean ln = etln.matches("");
        Boolean addr = etaddr.matches("");
        Boolean sal = etsal.matches("");
        if (rbAdd.isChecked()) {
            if (fn || ln || addr || sal) {
                Toast.makeText(getApplicationContext(), "Enter all fields!", Toast.LENGTH_SHORT).show();
            } else {
                long result = dataBase.insert(etfn, etln, etaddr, Double.parseDouble(etsal));
                if (result == -1) {
                    Toast.makeText(getApplicationContext(), "Could not add record!", Toast.LENGTH_SHORT).show();
                } else {
                    cleanETs();
                    Toast.makeText(getApplicationContext(), "Record added successfully! ID = " + result, Toast.LENGTH_SHORT).show();
                }
            }
        } else if (rbUpdate.isChecked()) {
            if (id || fn || ln || addr || sal) {
                Toast.makeText(getApplicationContext(), "Enter all fields!", Toast.LENGTH_SHORT).show();
            } else {
                int result = dataBase.update(Integer.parseInt(etid), etfn, etln, etaddr, Double.parseDouble(etsal));
                if (result == 0) {
                    Toast.makeText(getApplicationContext(), "No record found with matching id!", Toast.LENGTH_SHORT).show();
                } else {
                    cleanETs();
                    Toast.makeText(getApplicationContext(), "Record updated!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (rbRemove.isChecked()) {
            if (id) {
                Toast.makeText(getApplicationContext(), "Enter id!", Toast.LENGTH_SHORT).show();
            } else {
                int result = dataBase.delete(Integer.parseInt(etid));
                if (result == 0) {
                    Toast.makeText(getApplicationContext(), "No record found with matching id!", Toast.LENGTH_SHORT).show();
                } else {
                    cleanETs();
                    Toast.makeText(getApplicationContext(), "Record deleted!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (id) {
                Toast.makeText(getApplicationContext(), "Enter id!", Toast.LENGTH_SHORT).show();
            } else {
                Cursor result = dataBase.search(Integer.parseInt(etid));
                if (result.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No record found with matching id!", Toast.LENGTH_SHORT).show();
                } else {
                    cleanETs();
                    StringBuilder stringBuilder = new StringBuilder();
                    Cursor cursor = dataBase.getAll();
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        stringBuilder.append(cursor.getInt(cursor.getColumnIndex(dataBase.getId())));
                        stringBuilder.append(" - ");
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex(dataBase.getFirstName())));
                        stringBuilder.append(" - ");
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex(dataBase.getLastName())));
                        stringBuilder.append(" - ");
                        stringBuilder.append(cursor.getString(cursor.getColumnIndex(dataBase.getAddress())));
                        stringBuilder.append(" - ");
                        stringBuilder.append(cursor.getDouble(cursor.getColumnIndex(dataBase.getSalary())));
                        stringBuilder.append(" - ");
                        stringBuilder.append("\n");
                    }
                    tvOutput.setText(stringBuilder.toString());
                }
            }
        }
    }
}
