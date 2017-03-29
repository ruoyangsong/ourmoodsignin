package yifanwang.mymood1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MyHistoryActivity extends AppCompatActivity {
    private ListView moodlist;
    private ArrayList<Mood> mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);

    }
    @Override
    protected void onStart(){
        super.onStart();
        int i = 0;
        String username;
        moodlist = (ListView) findViewById(R.id.list);

        OurMoodApplication app = (OurMoodApplication) getApplication();
        username = app.getUsername();

        Boolean offline = false;
        Context ctx = getApplicationContext();
        final ArrayList<Mood> lists = new MoodList().getMoodLists(ctx,offline);


        MoodlistAdpater moodlistAdpater = new MoodlistAdpater(ctx,lists);
        moodlist.setAdapter(moodlistAdpater);

        moodlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditDeleteMoodActivity.class);
                Gson gS = new Gson();
                String target = gS.toJson(lists.get(position));
                intent.putExtra("MOOD",target);
                startActivity(intent);
            }
        });
    }

    public void Filter(View view){
        Intent intent = new Intent(this,Filter2.class);
        startActivity(intent);

    }
    public void SeeInMap(View view){
        Intent intent = new Intent(this,SeeMapActivity.class);
        startActivity(intent);

    }
    public void backtohome(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
