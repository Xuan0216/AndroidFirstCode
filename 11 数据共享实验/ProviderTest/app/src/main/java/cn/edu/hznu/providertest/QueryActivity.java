package cn.edu.hznu.providertest;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QueryActivity extends AppCompatActivity {

    private List<Contact>contactList=new ArrayList<Contact>();
    private RadioButton radioName;
    private RadioButton radioMobile;
    private String queryWord;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        Button queryData= findViewById(R.id.query_btn);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryWay();
            }
        });
    }

    public void queryWay(){

        //查询数据
        Uri uri=Uri.parse("content://cn.edu.hznu.databasetest.provider/contact/");

        radioName= findViewById(R.id.radioName);
        radioMobile= findViewById(R.id.radioMobile);
        queryWord=((TextView)findViewById(R.id.query_word)).getText().toString().trim();

        if (radioName.isChecked()){     //按姓名查询
            cursor = getContentResolver().query(uri, new String[]{"id,name,mobile"},
                    "name like ?", new String[]{"%" + queryWord + "%"}, "id asc");
        }
        else if(radioMobile.isChecked()){   // 按手机号码查询
            cursor = getContentResolver().query(uri, new String[]{"id,name,mobile"},
                    "mobile like ?", new String[]{"%" + queryWord + "%"}, "id asc");
        }

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String mobile = cursor.getString(cursor.getColumnIndex("mobile"));

                Contact contact = new Contact(name, mobile);
                contactList.add(contact);
                Log.d("QueryActivity", "Name: " + name + " Mobile:" + mobile);
            }
            cursor.close();
        }
        Intent intent = new Intent(QueryActivity.this, ResultActivity.class);
        intent.putExtra("contactList",(Serializable)contactList);
        startActivity(intent);
    }
}

