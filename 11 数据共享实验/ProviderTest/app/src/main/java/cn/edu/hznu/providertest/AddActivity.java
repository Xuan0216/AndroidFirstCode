package cn.edu.hznu.providertest;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button addData= findViewById(R.id.add_btn);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=((TextView)findViewById(R.id.contact_name)).getText().toString().trim();
                String mobile=((TextView)findViewById(R.id.contact_tel)).getText().toString().trim();

                if(name.isEmpty()||mobile.isEmpty()){
                    Toast.makeText(AddActivity.this,
                            "联系人姓名或手机号不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    Uri uri = Uri.parse("content://cn.edu.hznu.databasetest.provider/contact");

                    ContentValues values = new ContentValues();
                    values.put("name", name);
                    values.put("mobile", mobile);

                    Uri newUri = getContentResolver().insert(uri, values);
                    newName = newUri.getPathSegments().get(1);

                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
