#Facebook-Friends-list
## Create a Facebook app
* Login to facebook developer page
[https://developers.facebook.com/apps/](https://developers.facebook.com/apps/)
* Under “My Apps” Click to “Add a New App”

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/1.png)

* Then select your platform “Android”

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/2.png)

* Then enter your valid app name

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/3.png)

* Then click on “Create New Facebook App ID”
* Then select your app “Category”  for example I select “Books”

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/4.png)

* Then click “Create App ID”
* Then It will move direct to “quickstarts” page. Have steps to help you to add facebook sdk to your project

As I am using “Android Studio” Add code dependencies 
``` Java      
repositories {
    mavenCentral()
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:21.0.3'
    compile 'com.facebook.android:facebook-android-sdk:4.0.0'
}
```
* Then click “Build” then “Rebuild” or  “Sync Project with Gradle file”

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/5.png)

* Then move to “App Info” step and enter your packageName and your default Activity Name

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/6.png)

* Then click “Next” in my case you will see this message because I used invalid packageName but I will click on “use this package name” because I just create a demo app 

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/7.png)

* Then  create and add the “key Hashes” then click “Next”
* “Finished!” 
* Refresh developer page under “My Apps” you will find your app

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/8.png)


## Login using “LoginButton”
* In activity_main.xml
``` XML
<com.facebook.login.widget.LoginButton
    android:id="@+id/fbButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center_horizontal"
    />
```

* In “AndroidManifest.xml”
``` Java
<uses-permission android:name="android.permission.INTERNET"/>
```
Under  ``` <application ```
``` Java
<meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/facebook_app_id"/>
```
Then add facebook loginActivity to your Manifest
``` Java
<activity android:name="com.facebook.FacebookActivity"
    android:configChanges=
        "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
    android:theme="@android:style/Theme.Translucent.NoTitleBar"
    android:label="@string/app_name" />
```
* Then on “MainActivity.java”
``` Java
private TextView tvLoginResult;
private LoginButton btnFBLogin; 

private CallbackManager callbackManager;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        tvLoginResult = (TextView) findViewById(R.id.tv_LoginResult);
        btnFBLogin = (LoginButton) findViewById(R.id.fbButton); 

        //init the callback manager
        callbackManager = CallbackManager.Factory.create();

        btnFBLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                tvLoginResult.setText( "Success Login" + "\n"+
                        "User = " + loginResult.getAccessToken().getUserId() + "\n" +
                                "Token = " + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        //to pass Results to your facebook callbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
```


## Login Using Login Manager
* In onclick button event add
``` Java
    //"user_friends" this will return only the common friends using this app
    LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
            Arrays.asList("public_profile", "user_friends", "email"));
```
* Then at “OnCreate” we need to registerCallback to loginManager

``` Java
   LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
    @Override
    public void onSuccess(LoginResult loginResult) {
        btnShowFriendsList.setVisibility(View.VISIBLE);
        tvLoginResult.setText( "Success Login" + "\n"+
                        "User = " + loginResult.getAccessToken().getUserId() + "\n" +
                        "Token = " + loginResult.getAccessToken().getToken()
        );
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException e) {

    }
});

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        //to pass Results to your facebook callbackManager
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

```

## Display Friends list
* onClick event for button “btnShowFriendsList” start “FBFriendsListActivity”
``` Java
Intent intent = new Intent(this, FBFriendsListActivity.class);
startActivity(intent);
```

* onCreate start call getFBFriendsList()
``` Java
private void getFBFriendsList() {
    //fbToken return from login with facebook
    GraphRequestAsyncTask r = GraphRequest.newGraphPathRequest(fbToken,
            "/me/taggable_friends", new Callback() {

                @Override
                public void onCompleted(GraphResponse response) {
                    parseResponse(response.getJSONObject());
                }
            }

    ).executeAsync();

}
```
* onCompleted you need to parse the response
``` Java
private void parseResponse(JSONObject friends ) {

        try {
            JSONArray friendsArray = (JSONArray) friends.get("data");
            if (friendsArray != null) {
                for (int i = 0; i < friendsArray.length(); i++) {
                    FriendItem item = new FriendItem();
                    try {
                        item.setUserId(friendsArray.getJSONObject(i).get(
                                "id")
                                + "");

                        item.setUserName(friendsArray.getJSONObject(i).get(
                                "name")
                                + "");
                        JSONObject picObject = new JSONObject(friendsArray
                                .getJSONObject(i).get("picture") + "");
                        String picURL = (String) (new JSONObject(picObject
                                .get("data").toString())).get("url");
                        item.setPictureURL(picURL);
                        friendsList.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                // facebook use paging if have "next" this mean you still have friends if not start load fbFriends list
                String next = friends.getJSONObject("paging")
                        .getString("next");
                if (next != null) {
                    getFBFriendsList(next);
                } else {
                    loadFriendsList();
                }
            }
        } catch (JSONException e1) {
            loadFriendsList();
            e1.printStackTrace();
        }
    }
```
* Facebook use paging if have "next" this mean you still have friends if not start load fbFriends list
* To get remaining friends call “getFBFriendsList(String next)” I am using “volley” 
``` Java
-	private void getFBFriendsList(String next) {
    //here i used volley to get next page
    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
    StringRequest sr = new StringRequest(Request.Method.GET, next,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    JSONObject friends = null;
                    try {
                        friends = new JSONObject(response);
                        parseResponse(friends);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
        }
    }) {
        @Override
        protected Map<String, String> getParams() {
            return null;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("Content-Type", "application/x-www-form-urlencoded");
            return params;
        }
    };

    queue.add(sr);
}
```
* To update your listView just call “loadFriendsList()”
``` Java
private void loadFriendsList() {
    swipeLayout.setRefreshing(false);
    if ((friendsList != null) && (friendsList.size() > 0)) {
        lvFriendsList.setVisibility(View.VISIBLE);
        friendsAdapter.notifyDataSetChanged();
    } else {
        lvFriendsList.setVisibility(View.GONE);
    }
}
```
## Note
* Only facebook user under Roles can see the friend list (administrators,  developers, or testers)

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/Role.png)

* You need to add your app to Facebook Review, to make all users see his friends list 
* Under “Status & Review” click on “Start a Submission”

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/Status_Review.png)

* Then select “Taggable Friends” from left menu

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/sub_1.png)

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/sub_1_1.png)
* Then click to “Add Items”
* you will move direct to “Items in Review” 
* At “Items in Review” you enter all required fields below 

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/sub_2.png)

* After that at bottom you can click on “Submit for Review” button

![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/sub_3.png)

* You will received email from facebook about the review result

if everything is good you will get the approval like below 
![](https://github.com/sallySalem/Facebook-Friends-list/blob/master/image/sub_4.png)

But if anything missing or wrong you will not get the approval, you can fix it and submit it for review again.
