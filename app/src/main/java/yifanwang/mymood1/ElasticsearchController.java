package yifanwang.mymood1;

/**
 * Created by zheng on 2017-03-20.
 */
import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

/**
 * This class is the controller for Elastic search
 * Referenced with
 * "https://github.com/Hanwen1/lonelyTwitter/blob/master/app/src/main/java/ca/ualberta/cs/lonelytwitter/ElasticsearchTweetController.java"
 */

public class ElasticsearchController {
    private static JestDroidClient client;


    /**
     * add user to elastic search
     */
    public static class AddUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Index index = new Index.Builder(user).index("cmput301w17t14").type("user").id(user.getUsername()).build();


                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("In AsyncTask ID", result.getId());
                    } else {
                        Log.i("Error", "ElasticSearch was not able to add the user.");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the user");
                }

            }
            return null;
        }
    }

    /**
     * get user from elastic search
     */
    public static class GetUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... params) {
            verifySettings();
            /*
            ArrayList<User> users = new ArrayList<User>();
            String query;
            if (params[0] == "") {
                query = "{\"from\":0,\"size\":10}";
            } else {
                query = "{\n" +
                        "    \"query\" : {\n" +
                        "        \"term\" : { \"message\" :\"" + params[0] + "\" }\n" +
                        "    }\n" +
                        "}";
            }
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t14")
                    .addType("user")
                    .build();
            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<User> foundusers = result.getSourceAsObjectList(User.class);
                    users.addAll(foundusers);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return users;
            */
            User user = new User();
            Get get = new Get.Builder("cmput301w17t14", params[0]).type("user").build();

            try{
                JestResult result = client.execute(get);
                user = result.getSourceAsObject(User.class);
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return user;

        }
    }



    /**
     * update user from elastic search
     */
    public static class UpdateUserTask extends  AsyncTask<User, Void, Void> {
        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Update update = new Update.Builder(user).index("cmput301w17t14").type("user").id(user.getUsername()).build();

                try {
                    // where is the client
                    DocumentResult result = client.execute(update);
                    if (result.isSucceeded()) {
                        Log.d("In AsyncTask ID", result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to update the user.");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the user");
                }
            }
            return null;
        }
    }

    /**
     *check if the user is stored in elastic search
     */
    public static class IsExist extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params){
            verifySettings();

            User user = new User();

            Get get = new Get.Builder("cmput301w17t14", params[0]).type("user").build();

            try {
                JestResult result = client.execute(get);
                user = result.getSourceAsObject(User.class);
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            if (user == null) {
                return false;
            }

            return true;
        }
    }


    /**
     * Verify settings.
     */
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }



    public static class GetUsersTask extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... params) {
            verifySettings();
            ArrayList<User> users = new ArrayList<User>();
            String query;
            if (params[0] == "") {
                query = "{\"from\":0,\"size\":10}";
            } else {
                query = "{\n" +
                        "    \"query\" : {\n" +
                        "        \"term\" : { \"message\" :\"" + params[0] + "\" }\n" +
                        "    }\n" +
                        "}";
            }
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w17t14")
                    .addType("user")
                    .build();
            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<User> foundusers = result.getSourceAsObjectList(User.class);
                    users.addAll(foundusers);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return users;
        }
    }
}
