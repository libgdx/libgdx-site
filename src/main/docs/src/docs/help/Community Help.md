---
title: Community help
tags:
- help
- assist
- problem
- error
- fix
- bananas

---

<h1>Community help</h1>


If you have a bug that you can't squash, want some help understanding something about the framework, or how to implement something, look no further.

- [Help channels](#forums-and-irc)
- [Tips for getting issues resolved quickly](#tips-for-getting-issues-resolved-quickly)

<br>
<hr>
<br>

## Forums and irc

The forums and irc channel are great places to go for getting your issues resolved. 

- [LibGDX forums](http://badlogicgames.com/forum/index.php)
    
    The forums are a great place to go if you want to discuss at length anything related to LibGDX.

- IRC Channel

    We have an active irc channel **#libgdx** on the **freenode** server at **irc.freenode.net**.  Core contributors lurk here, as well as lots of other contributors and developers.
    Lots of friendly people to help you with your issues, and talk about anything LibGDX.


<br>
<hr>
<br>

## Tips for getting issues resolved quickly

Often when people ask questions, their scope is huge, and it's not clear what exactly the issue is, let alone where a bug could be hiding.  There are very simple steps you can take to resolve this,
and more often than not, when you follow them you will most likely find the issue yourself.

- **Isolate the issue**

 The most important component, is to try to isolate the issue as much as possible.  The easiest way to do this is to start fresh with an empty ApplicationListener,
 rebuild up a small app that demonstrates the issue.  Don't be tempted to try to hack away at a huge rats nest of code until you think you have a small application.

 _e.g. if you are trying to debug a networking issue, the sample app doesn't have to have a Spritebatch, or any rendering etc._

 After you do this, you will probably find the issue, but if you don't, you now have an easy sample that you can post on the forums, or pastebin a link to in the irc channel. Now everyone can immediately test your demo app,
 and you are much more likely to get assistance.

- **Format your code**

 Having clear formatted code goes a long way when showing people a demonstration, lots of people will be put off by something that is hard to follow, and looks a mess.
 Invest in a few minutes to tidy up your code, use correct java conventions, and keep your indenting uniform.

- **Supply all the info required**

 Most of the information you supply may seem trivial, but there are a lot of moving parts, and all kinds of weird combinations can cause things to go awry. 
 
 What Java version? What Gradle version? What LibGDX version? How are you launching your application? What IDE/Command Line?

 Do you have any stacktraces? Post them in full.

