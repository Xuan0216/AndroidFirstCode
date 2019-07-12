package cn.edu.hznu.labactivitydatatransfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView name = (TextView)findViewById(R.id.name);
        TextView passwd = (TextView)findViewById(R.id.passwd);
        TextView gender = (TextView)findViewById(R.id.gender);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        name.setText(bundle.getString("name"));
        passwd.setText(bundle.getString("passwd"));
        gender.setText(bundle.getString("gender"));

        Button btn = (Button)findViewById(R.id.goback);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
