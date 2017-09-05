---
title: Contributing
tags:
- open source
- contributing
- source code
- github
- pull
- request
- share
- help
- patreon
- documentation

---

<h1>Contributing</h1>

Contributing to LibGDX can come in a few different forms, you can help out on the forums and irc, pledge to the patreon page, and submit
code and documentation back to the project on github.

If you want to submit code back to the project, please take a moment to review over the guidelines below.

- [Contributing Code](#contributing-code)
 - [Guidelines](#guidelines)
   - [Discussion of changes](#discussion-of-changes)
   - [Formatting](#formatting)
   - [Code Style](#code-style)
   - [Compatibility](#compatibility)
   - [Performance Considerations](#performance-considerations)
   - [Size of code changes](#size-of-code-changes)
 - [How to contribute code](#how-to-contribute-code)
 - [Wiki contributions](#wiki-contributions)
 - [Patreon](#patreon)

<br>
<hr>
<br>

## Guidelines

### Discussion of changes

 If you have a proposal for changes that you aren't sure about, or questions about a specific change you would like to make, you can start a conversation with 
 the developers on the irc channel/forums or by starting up an issue on the issue tracker on Github. 

<br>
### Formatting

If you are working on any of the LibGDX code, we require you to use the formatter that we use. You can find it [here](https://github.com/libgdx/libgdx/blob/master/eclipse-formatter.xml)
Failure to use the formatter will most likely get your pull request rejected.  The formatter provided can be imported into Intellij and Android Studio also, its not just for Eclipse. See [here](https://blog.jetbrains.com/idea/2014/01/intellij-idea-13-importing-code-formatter-settings-from-eclipse/?utm_source=hootsuite&utm_campaign=hootsuite) for official documentation on this.

<div class="well error">
Don't let the formatter run automatically, or on the whole file you are changing.

Only run the formatter on the lines you are specifically changing.
</div>

<br>
### Code Style

LibGDX stands by the usual java style, but we don't have an official coding standard.  

**Please do not do any of the following:**

- Underscores in identifiers
- Hungarian notation
- Prefixes for fields or arguments
- Curly braces on new lines


A few additional notes to keep in mind:

- When creating a new file, make sure to add the Apache file header, as you can see [here](https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Application.java)
- Follow the style of the code in the file you are changing
- If you create a new class, please add documentation that explains the usage and scope of the class.  You don't have to add javadocs for
methods that are self explanatory.
- If your class is thread safe, mention it in the documentation. Its assumed that LibGDX classes are not thread safe by default, so please mention it.

<br>
### Compatibility

Due to the cross platform nature of LibGDX, there are some things you have to avoid when contributing code.  For example. GWT does not support all java features, and Android does not support all desktop jvm features.

<br>
### Considerations for GWT compatibility

You can see what features are supported on GWT [here](http://www.gwtproject.org/doc/latest/RefJreEmulation.html).
Anything that is not there, is not compatible on the GWT backend, so it must either be emulated, or avoided.

Emulation is required if something isn't available in the GWT JRE, or if the regular class requires native code (Matrix4)
An example of emulation is shown [here](https://github.com/libgdx/libgdx/blob/master/backends/gdx-backends-gwt/src/com/badlogic/gdx/backends/gwt/emu/com/badlogic/gdx/math/Matrix4.java) with Matrix4

Common limitations on GWT include:
- Formatting, String.format() not supported
- RegEx,use the Pattern and Matcher provided by the GWT backend
- Reflection, use LibGDX [reflection](https://github.com/libgdx/libgdx/wiki/Reflection) instead
- Multithreading. Timers are supported on GWT, but threads are strictly not.



Determine if any new classes are compatible with GWT, and either **include** or **exclude** elements to the [gwt module](https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx.gwt.xml)

<br>
### Performance considerations

Due to some of the targets of the framework being mobile and the web, along with it being a game development focused framework, its important to keep performance as tight as possible.

On mobile platforms especially, memory management is very important, so we don't create any garbage in the core of LibGDX. (Its advised you dont in your applications either)

A couple of guidelines regarding performance:

- Avoid any temporary object allocation
- Do not make defensive copies
- Avoid locking, LibGDX is not thread safe, unless explicitly specified.
- Do not use boxed primitives
- Use the Collection classes LibGDX provides in the [com.badlogic.gdx.utils](https://github.com/libgdx/libgdx/tree/master/gdx/src/com/badlogic/gdx/utils) package, do not use java collections
- Do not perform argument checks for methods that may be called thousands of times per frame 
- Use pooling if necessary, if possible avoid exposing the internal pooling to the user

<br>
### Size of code changes

To reduce the chances of introducing errant behavior, and to increase the chance that your pull request gets merged, we ask you to keep the requests small and focused.

- Submit a pull request dedicated to solving one issue/feature. Multiple confused pull requests will be denied
- Keep the code changes as small as possible, its very unlikely that huge change sets of 3000 files will be merged 


Pull requests for the master repository will be checked by automation tools, and the core contributors before merging. 
Please do not leave it up to the core contributors to test your code, make sure it compiles, and test on every platform you can, telling us what you have tested on.



<br>
## How to contribute code

LibGDX uses git, with our codebase situated on Github.  In order to submit changes back to the official LibGDX project, you will need to fork the project, clone your fork, push changes back to your fork, and then submit a pull request based on your changes.

For a short yet very detailed explanation of how the system works, check out [this](https://git-scm.com/book/en/v2/Distributed-Git-Contributing-to-a-Project#Forked-Public-Project)


For a shorter rundown, this is the flow:

- Fork the LibGDX repository on github. [docs](https://help.github.com/articles/fork-a-repo/)
- Clone the forked repository. [docs](https://help.github.com/articles/fork-a-repo/)
- Add the LibGDX repository as a remote (to sync latest changes). [docs](https://help.github.com/articles/fork-a-repo/)

- Make your changes to your locally cloned (of your fork), repository. Best to use new branches. 
- Commit your changes, and push the changes back to your forked repository on Github.
- Go to github, view your forked repository, select your branch and request a pull request be created.
- Write a detailed description of what your pull request does, how it has been tested, what platforms it has been tested on, and why it belongs in LibGDX.

Pull requests may be denied for not being ready, or not fitting the scope of the project. Please do not take any offense to having a pull request rejected. We appreciate every contribution, but some code submissions are just not a good fit for the project.


<br>
## Wiki contributions

The [LibGDX Wiki](https://github.com/libgdx/libgdx/wiki) contains lots of detailed descriptions and guides of specific parts of the API. Anyone is free to contribute to the project, keeping documents up to date, and adding new entries.
Please feel free to make your own additions/changes to articles if they need it, it is much appreciated.

<br>
## Patreon

If you would like to contribute to LibGDX monetarily, we have a [Patreon](https://www.patreon.com/libgdx) page that helps support the project.

LibGDX uses build servers and web servers that are used for the website, forum, blog, automated building, and hosting of snapshot and release binaries. We ask kindly for minimal support to cover these monthly costs, with any excess donations being used on test devices and 
development machines for the core contributors.

