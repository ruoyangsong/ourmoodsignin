package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-12.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;


/**
 * Created by junzhuo on 3/11/17.
 */

public class SignupsuccessActivity extends AppCompatActivity {
    Button startSurfing;
    TextView et;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupsuccess);
        initView();
        Intent intent = getIntent();
        String target = intent.getStringExtra("USER");
        Gson gS = new Gson();
        user= gS.fromJson(target,User.class);
        et.setText(user.getUsername());
    }

    private void initView() {
        startSurfing = (Button) findViewById(R.id.startsurfring);
        startSurfing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupsuccessActivity.this, MainActivity.class);
                Gson gS = new Gson();
                String target = gS.toJson(user);
                intent.putExtra("USER",target);
                startActivity(intent);
                startActivity(intent);
            }
        });
        et = (TextView) findViewById(R.id.username_display_et);
    }
}