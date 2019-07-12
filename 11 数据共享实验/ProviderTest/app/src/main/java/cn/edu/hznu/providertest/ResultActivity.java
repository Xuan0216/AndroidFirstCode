package cn.edu.hznu.providertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private List<Contact> contactList=new ArrayList<Contact>();
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        contactList = (List<Contact>) getIntent().getSerializableExtra("contactList");
        adapter = new ContactAdapter(ResultActivity.this,R.layout.result_item,contactList);

        ListView listView = findViewById(R.id.list_view);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
