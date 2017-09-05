---
title: Packaging a Project
tags:
- deploying
- package
- jar
- ipa
- apk
- export
- packaging
- moe
- robovm
- multi os engine
- EXE
- android
- gradle
- command line

---

<h1> Packaging a LibGDX project </h1>

Packaging your application is easy as one command line task invocation. Its recommended you use the gradle tasks
to package your app, as they are already wired up to bundle everything you need for each platform. However if you feel comfortable setting this up
in your IDE by yourself, feel free!

- [Packaging Desktop project](#packaging-for-desktop)
    - [Creating an .EXE](#further-packaging-desktop)
- [Packaging Android project](#packaging-for-android)
- [Packaging iOS RoboVM project](#packaging-for-robovm)
- [Packaging iOS Multi-OS Engine project](#packaging-for-multi-os-engine)
- [Packaging HTML project](#packaging-for-html)


<br>
## Packaging for desktop

```bash
./gradlew desktop:dist
```

Generates a fat jar in **desktop/build/libs** directory that you can run with 
```bash
java -jar desktop.jar
 ```
 
 <br>
 ### Further Packaging desktop
 You can wrap this jar into a .EXE bundle along with a small JRE so that your end users don't have to have java installed.
 [PackR](https://github.com/libgdx/packr) is a wonderful tool that allows you to do this.

<br>
## Packaging for Android

```bash
./gradlew android:assembleDebug
```

Will generate a debug APK in **android/build/outputs/apk** that you can send to users manually if they have disabled APK source checking.

To generate a signed APK, you can follow the guide here: [Android Signing](https://developer.android.com/studio/publish/app-signing.html)
This goes through how you can do this in **Intellij** and **Android studio**, but also on command line. See the **"Configure Gradle to sign your APK"** section for this.

<br>
## Packaging for RoboVM 

Its recommended you use your IDE plugin to package for RoboVM.

- Create an IPA in **Intellij/Android Studio** documentation [here](http://docs.robovm.com/getting-started/intellij.html#deployment)
- Create an IPA in **Eclipse** documentation [here](http://docs.robovm.com/getting-started/eclipse.html#deployment)

Alternatively run:

```bash
./gradlew ios:createIPA
```

This will require you to setup additional configuration in the build.gradle file for robovm. See [documentation](https://github.com/MobiDevelop/robovm/tree/master/plugins/gradle)

<br>
## Packaging for Multi-OS Engine 


See the official documentation for building an IPA with Multi-OS Engine [here](https://doc.multi-os-engine.org/multi-os-engine/3_getting_started/1_hello_world_app/hello_world_app.html#building-ipa-to-deploy-app-to-appstore)

<br>
## Packaging for HTML

Run the following Gradle task to package the HTML project:

```bash
./gradlew html:dist
```

This will create the resulting javascript and assets in the **html/build/dist** folder.  You have to serve these files by a webserver, (e.g. Apache/nginx)

