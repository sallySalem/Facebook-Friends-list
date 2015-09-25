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
## Login Using Login Manager
## Display Friends list
