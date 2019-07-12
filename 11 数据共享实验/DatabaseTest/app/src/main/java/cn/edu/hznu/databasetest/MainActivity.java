package cn.edu.hznu.databasetest;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private List<Contact> contactList=new ArrayList<>();
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化联系人列表
        initContact();

        //调用系统的拨号程序
        adapter=new ContactAdapter(MainActivity.this,R.layout.contact_item,contactList);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = (Contact)parent.getAdapter().getItem(position);
                String mobile = contact.getMobile();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ mobile));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initContact();
    }

    public void initContact(){
        dbHelper=new MyDatabaseHelper(this,"ContactList.db",null,1);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        contactList.clear();
        //查询contact表中所有的数据
        Cursor cursor=db.query("contact",null,null,
                null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                //遍历Cursor对象，取出数据并打印
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String mobile=cursor.getString(cursor.getColumnIndex("mobile"));

                Contact contact=new Contact(false,name,mobile);
                contactList.add(contact);

                Log.d("MainActivity","name is "+name);
                Log.d("MainActivity","mobile is "+mobile);
            }while (cursor.moveToNext());
        }
        cursor.close();
        adapter=new ContactAdapter(MainActivity.this,R.layout.contact_item,contactList);
        ListView listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                //打开新建联系人页面
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
                break;
            case R.id.delete_item:
                //删除所选联系人信息
                Map<Integer, Boolean> isCheck_delete = adapter.getMap();
                for(int i=0;i<contactList.size();i++) {
                    if (isCheck_delete.get(i) != null && isCheck_delete.get(i)) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        String a = contactList.get(i).getName().toString().trim();
                        String b = contactList.get(i).getMobile().toString().trim();
                        db.delete("contact", "name = ? and mobile = ?", new String[]{a, b});
                        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent1);
                        //adapter.notifyDataSetChanged();
                    }
                }
                break;
            default:
        }
        return true;
    }
}
