package cn.edu.hznu.databasetest;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText contact_name;
    EditText contact_tel;
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        contact_name=(EditText)findViewById(R.id.contact_name);
        contact_tel = (EditText) findViewById(R.id.contact_tel);
        dbHelper=new MyDatabaseHelper(this,"ContactList.db",null,1);

        Button addData=(Button)findViewById(R.id.add_btn);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=contact_name.getText().toString().trim();
                String tel=contact_tel.getText().toString().trim();
                if(name.isEmpty()||tel.isEmpty()){
                    Toast.makeText(AddActivity.this,
                            "联系人姓名或手机号不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    //添加数据
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    //组装数据
                    values.put("name", name);
                    values.put("mobile", tel);
                    db.insert("contact", null, values);//插入数据
                    values.clear();
                    Toast.makeText(AddActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
