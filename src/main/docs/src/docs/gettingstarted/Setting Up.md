---
title: Setting up Environment
tags:
- setup
- IDE
- eclipse
- JDK
- jvm
- intellij
- idea
- netbeans
- android
- iOS
- html
- environment

---

<h1>Setting up Environment</h1>

This article provides you with the information required before you start importing a LibGDX project.

LibGDX projects are built and managed with the Gradle build tool.
This allows you to develop in almost any environment that takes your fancy, even if
you are working in a team.

The 3 major java IDEs, IDEA/Android Studio, Eclipse and Netbeans are all supported, just make sure you have
set them up correctly.

---

## Setting up your Development Environment
 - **IDE Configuration**
    - [IDEA/Android Studio](#setting-up-intellij-idea-android-studio)
    - [Eclipse](#setting-up-eclipse)
    - [Netbeans](#setting-up-netbeans)
    - [Command Line](#command-line)<br>
 <br>
---

## Setting up Intellij IDEA/Android Studio
Minimum:
- Java Development Kit 7+ (Must be JDK)
- Intellij IDEA 2016+, (community edition is sufficient) [Link](https://www.jetbrains.com/idea/download/#section=windows)
- Android Studio, 2.2+ [Link](https://developer.android.com/studio/index.html)

Android:
- Android SDK (If using IDEA) [Link](https://developer.android.com/studio/index.html#downloads)

iOS:
- RoboVM OSS Intellij plugin (if using RobovmOSS) [Link](http://robovm.mobidevelop.com/)
- Multi OS Engine Intellij plugin (if using Multi OS Engine) [Link](https://plugins.jetbrains.com/idea/plugin/8559-multi-os-engine-plugin)

## Setting up Eclipse
Minimum:
- Java Development Kit 7+ (Must be JDK)
- Eclipse Mars+ with Buildship [link](https://www.eclipse.org/downloads/)

Android:
- Android SDK [Link](https://developer.android.com/studio/index.html#downloads)
- ADT Tools [Link](https://developer.android.com/studio/tools/sdk/eclipse-adt.html)

iOS:
- RobovmOSS Eclipse plugin [Link](http://robovm.mobidevelop.com/)
- Multi OS Engine plugin [Link](https://multi-os-engine.org/start/)

## Setting up Netbeans
Haha good one...

## Command Line
Minimum:
- Java Development Kit 7+ (Must be JDK)

Android:
- Android SDK
- Set the ANDROID_HOME environment variable, or use gradle.properties