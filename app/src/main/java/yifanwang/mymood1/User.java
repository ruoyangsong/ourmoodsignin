package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-12.
 */

/**
 * Created by junzhuo on 3/12/17.
 */

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{

    private String username;
    private ArrayList<String> followlist;
    private ArrayList<Mood> moodlist;

    public ArrayList<String> getFollowlist() {
        return followlist;
    }

    public ArrayList<Mood> getMoodlist() {
        return moodlist;
    }

    public User(){}
    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);

    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void addFollow(String username){
        this.followlist.add(username);
    }
    public void deleteFollow(String username){
        this.followlist.remove(username);
    }

    public boolean haveFollow(String username){
        return this.followlist.contains(username);
    }

    public void addMood(Mood mood){
        this.moodlist.add(mood);
    }
    public void deleteMood(Mood mood){
        this.moodlist.remove(mood);
    }
    public boolean haveMood(Mood mood){
        return this.moodlist.contains(mood);
    }
}
