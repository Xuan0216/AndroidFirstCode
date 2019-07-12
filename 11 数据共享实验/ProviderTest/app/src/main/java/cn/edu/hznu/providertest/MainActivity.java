package cn.edu.hznu.providertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public String newName;
    public String newMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addData=(Button)findViewById(R.id.AddContact);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开“添加联系人”活动
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        Button queryData=(Button)findViewById(R.id.QueryContact);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开“查询联系人”活动
                Intent intent=new Intent(MainActivity.this,QueryActivity.class);
                startActivity(intent);
            }
        });
    }
}
