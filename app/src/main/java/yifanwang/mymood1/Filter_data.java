package yifanwang.mymood1;


import java.util.ArrayList;
import android.util.Log;

public class Filter_data {
    private ArrayList<Mood> userMood;
    private ArrayList<Mood> allMood;

    public ArrayList<Mood> getAllMood(User user){
        int i=0;
        ArrayList<String> followerList = user.getFollowlist();
        while(i<followerList.size()){
            String followerName = followerList.get(i);

            ElasticsearchController.GetUserTask getUserTask = new ElasticsearchController.GetUserTask();
            getUserTask.execute(followerName);
            try {
                user=getUserTask.get();
            }catch(Exception e) {
                Log.i("Error", "Failed to get the tweets out of the async object");
            }
            allMood.addAll(user.getMoodlist());
            i++;

        }
        return allMood;
    }

    public ArrayList<Mood> getUserMood(User user){
       userMood.addAll(user.getMoodlist());
        return userMood;
    }

}
