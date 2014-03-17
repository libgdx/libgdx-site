<?

$step = $_REQUEST["step"];
if (!$step) $step = 1;

?>

<html>
<head>
<title>Getting Help</title>
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<style>
body, td, th {
	font-family: tahoma;
}
th {
	text-align: right;
	padding-right: 6px;
	white-space: nowrap;
}
input[type=text] {
	width: 30em;
}
.content {
	width: 640px;
}
textarea {
	width: 800;
	height: 16em;
}
.do {
	font-weight: bold;
	color: green;
}
.dont {
	font-weight: bold;
	color: red;
}
</style>
</head>

<body>

<img src="http://libgdx.badlogicgames.com/img/logo.png" width=300 height=50><br><br>

<h2 style="margin-top:5px">Problem Wizzard</h2>

<div class="content">

<?
function nextButton () {
	?>
	<input type="button" value="Next" onclick="if (!window.next || next()) $('#main')[0].submit()">
	<?
}

switch ($step) {
case 1: ?>
	The libgdx community is glad to help you when you get stuck or encounter a bug. Please use this wizard when posting a problem on the forum to make it as easy as possible for others to help you. <b>Requests for help that use this wizard get priority over requests that do not.</b>
	<h3>Step 1 - Helping Yourself</h3>
	Please go through this short checklist to be sure you have not missed an easy to find solution.
	<ol>
	<li> Are you using the latest <a href="http://libgdx.badlogicgames.com/download.html">nightly build</a>? If not, please try that first, as issues are being fixed every single day. You can also <a href="https://github.com/libgdx/libgdx/wiki/Working-from-source">run from source</a>.
	<li> Have you read the documentation on the <a href="https://github.com/libgdx/libgdx/wiki">libgdx wiki</a>? It can also be very helpful to look at the <a href="http://libgdx.badlogicgames.com/nightlies/docs/api/">javadocs</a> and <a href="https://github.com/libgdx/libgdx">source code</a> (don't be shy!). Search the <a href="https://github.com/libgdx/libgdx/tree/master/tests/gdx-tests/src/com/badlogic/gdx/tests">tests</a> for a specific class to find example code.
	<li> Have you <a href="http://www.badlogicgames.com/forum/search.php">searched the forum</a> for your problem?
	<li> Have you searched the <a href="https://github.com/libgdx/libgdx/issues">issue tracker</a> for your problem? Be sure to search "All issues", not just "Open issues".
	</ol>
	When you have a problem, often the libgdx chat room is the fastest way to get help. Usually it has 100+ people, any one of which could answer your question <i>right now</i>. The chat room is <a href="irc://irc.freenode.net/libgdx">#libgdx on irc.freenode.net</a>. You can join and start chatting <a href="http://webchat.freenode.net/?channels=libgdx">right through your browser</a>.<br><br>
	If you still wish to post on the <a href="http://www.badlogicgames.com/forum/">forum</a>, please continue to the next step.<br><br>
	<?
	nextButton();
	break;
case 2: ?>
	<style>.content div { display:none; padding-left:2em }</style>
	<h3>Step 2 - Which backend?</h3>
	<span class="do">Do:</span> Check which backend(s) your problem is occurring on.<br><br>

	<label><input type="checkbox" name="backend" value="All" checked onchange="chosen(0, this)"> All, the problem is not specific to any one backend.</label><br>
	
	<label><input type="checkbox" name="backend" value="Android" onchange="chosen(0, this)"> Android</label><br>
	<div id="Android">
		Note: Android issues can sometimes be more difficult do to device manufacturers breaking things or buggy drivers.<br>
		<table cellspacing=0 cellpadding=0 border=0>
		<tr><th>Device name:</th><td><input type="text" name="Android device"></td>
		<tr><th>Android version:</th><td><input type="text" name="Android version"></td>
		</table>
	</div>

	<label><input type="checkbox" name="backend" value="Desktop" onchange="chosen(0, this)"> Desktop (LWJGL, JGLFW)</label><br>
	<div id="Desktop">
		<table cellspacing=0 cellpadding=0 border=0>
		<tr><th>OS:</th><td><input type="text" name="Desktop OS"></td>
		<tr><th>Backend(s):</th><td>
			<input type="checkbox" name="LWJGL"> LWJGL &nbsp;
			<input type="checkbox" name="JGLFW"> JGLFW
		</td>
		<tr><th>OpenGL version:</th><td><input type="text" name="Desktop OpenGL"></td>
		</table>
	</div>

	<label><input type="checkbox" name="backend" value="iOS" onchange="chosen(0, this)"> iOS (RoboVM)</label><br>
	<div id="iOS">
		<table cellspacing=0 cellpadding=0 border=0>
		<tr><th>iOS version:</th><td><input type="text" name="iOS version"></td>
		<tr><th>Device:</th><td><input type="text" name="iOS device"></td>
		</table>
	</div>
	
	<label><input type="checkbox" name="backend" value="GWT" onchange="chosen(0, this)"> GWT (WebGL)</label><br>
	<div id="GWT">
		<table cellspacing=0 cellpadding=0 border=0>
		<tr><th>OS:</th><td><input type="text" name="Browser OS"></td>
		<tr><th>Browser name:</th><td><input type="text" name="Browser name"></td>
		<tr><th>Browser version:</th><td><input type="text" name="Browser version"></td>
		</table>
	</div>

	<br>
	<? nextButton(); ?>

	<script>
	function chosen (i, checkbox) {
		var checked = checkbox.checked;
		if (checked) {
			if (checkbox.value == "All") {
				$("[type=checkbox]").prop("checked", false).each(chosen);
				checkbox.checked = checked;
			} else
				$("[type=checkbox]")[0].checked = false;
		}
		$("#" + checkbox.value)[checked ? "show" : "hide"]({duration:500});
	}
	$(function () { $("[type=checkbox]").each(chosen); });
	function get (parent, child) {
		var objects = $("#" + parent + " [name='" + child + "']:visible");
		if (!objects.length) return "";
		if (objects[0].type == "checkbox") return !objects[0].checked ? "" : (child + ", ");
		return $.trim(objects.val()) ? (child + ": " + $.trim(objects.val()) + ", ") : "";
	}
	function next () {
		if (!$("[type=checkbox]:checked").length) return alert("Please choose a backend or all backends.");
		var android = "";
		android += get("Android", "Android device");
		android += get("Android", "Android version");
		if ($("#Android:visible").length && !android) return alert("Please enter Android information.");
		if (android) android = "Android, " + android.substring(0, android.length - 2) + "\n";
		
		var desktop = get("Desktop", "Desktop OS");
		desktop += get("Desktop", "LWJGL");
		desktop += get("Desktop", "JGLFW");
		desktop += get("Desktop", "Desktop OpenGL");
		if ($("#Desktop:visible").length && !desktop) return alert("Please enter desktop information.");
		if (desktop) desktop = "Desktop, " + desktop.substring(0, desktop.length - 2) + "\n";
		
		var ios = get("iOS", "iOS version");
		ios += get("iOS", "iOS device");
		if ($("#iOS:visible").length && !ios) return alert("Please enter iOS information.");
		if (ios) ios = "iOS, " + ios.substring(0, ios.length - 2) + "\n";
		
		var gwt = get("GWT", "Browser OS");
		gwt += get("GWT", "Browser name");
		gwt += get("GWT", "Browser version");
		if ($("#GWT:visible").length && !gwt) return alert("Please enter GWT information.");
		if (gwt) gwt = "GWT, " + gwt.substring(0, gwt.length - 2) + "\n";

		$('#main [name=backend]').val((android + desktop + ios + gwt).trim());
		return true;
	}
	</script>
	<?
	break;
case 3: ?>
	<h3>Step 3 - Issue Title</h3>
	<span class="do">Do:</span> Write a clear and short topic title. Mention the major section of libgdx the issue is associated with (scene2d, 3D, audio, texture packing, etc) as well as a few words that describe your problem.<br><br>
	<span class="dont">Do not:</span> Write a title that does not describe the topic (such as "please help" or "I have a problem"), use all caps, exclamation marks, etc.<br><br>
	<table cellspacing=0 cellpadding=0 border=0>
	<tr><th>Title:</th><td><input type="text" id="title"></td>
	</table>
	<br>
	<? nextButton(); ?>
	<script>
	function next () {
		var title = $.trim($("#title").val());
		if (!title) return alert("Please enter a title.");
		if (title.length < 10) return alert("Please enter a longer title.");
		$('#main [name=title]').val(title);
		return true;
	}
	</script>
	<?
	break;
case 4: ?>
	<h3>Step 4 - Context</h3>
	<span class="do">Do:</span> Describe <b>what</b> you are trying to achieve. If it might help your question get answered, also explain the reasons why. If specific solutions are unacceptable, list them and why.<br><br>
	<span class="dont">Do not:</span> Describe how you are trying to achieve your goal, what does not work, or anything else except <b>what</b> you are trying to achieve.<br><br>
	<textarea id="context"></textarea><br><br>
	<? nextButton(); ?>
	<br><br>
	<i>Example: I want to layout scene2d widgets in a grid and I want one of the grid cells to span two rows.</i>
	<script>
	function next () {
		var context = $.trim($("#context").val());
		if (!context) return alert("Please describe your issue's context.");
		if (context.length < 10) return alert("Please describe more about your issue's context.");
		$('#main [name=context]').val(context);
		return true;
	}
	</script>
	<?	
	break;
case 5: ?>
	<h3>Step 5 - Problem Statement</h3>
	<span class="do">Do:</span> Concisely describe the problems you encounter when trying to achieve what you wrote in the last step. <b>Describe each approach you have tried</b> and, for each of those, <b>explain what you expected</b> and <b>what actually happened</b>.<br><br>
	<span class="dont">Do not:</span> Be vague. No one wants to guess what your actual problem is and often they do not have the time or patience to ask for the information you should have included from the start.<br><br>
	<textarea id="problem"></textarea><br><br>
	<? nextButton(); ?>
	<script>
	function next () {
		var problem = $.trim($("#problem").val());
		if (!problem) return alert("Please describe your problem.");
		if (problem.length < 10) return alert("Please describe more about your problem.");
		$('#main [name=problem]').val(problem);
		return true;
	}
	</script>
	<?
	break;
case 6: ?>
	<h3>Step 6 - Exceptions</h3>
	<span class="do">Do:</span> Provide any error messages and the <b>entire</b> exception stacktrace for any exceptions that occurred.<br><br>
	<span class="dont">Do not:</span> Claim you encountered an error without providing the error and <b>entire</b> exception stacktrace.<br><br>
	<textarea id="exception"></textarea><br><br>
	<input type="checkbox" id="noException"> No exception or error message occurred.<br><br>
	<? nextButton(); ?>
	<script>
	function next () {
		var exception = $.trim($("#exception").val());
		var noException = $("#noException")[0].checked;
		if (!noException && (!exception || exception.length < 10)) return alert("Please paste your exception or error message.");
		$('#main [name=exception]').val(noException ? "" : exception);
		return true;
	}
	</script>
	<?
	break;
case 7: ?>
	<style>
	textarea {
		height: 32em;
	}
	</style>
	<h3>Final Step - Executable Example</h3>
	Example code that can be copied, pasted and run is the best way to get help. Those helping you save time because they can quickly fix your code or bug, verify the fix, and show you the result. No matter what, an executable example has to be written to properly test, fix, and verify the fix. <b>If you cannot debug and fix the problem yourself, then you should provide an executable example so that someone else can fix it for you.</b><br><br>
	Creating executable example code does take time. You need to dismantle your application and reconstruct the relevant parts in a new, bare-bones application that shows the problem. Quite often just by doing this you will figure out the problem. If not, you will get help quickly and the people helping you will have more time to help more people.<br><br>
	Example code should be contained entirely in a single class (use static member classes if needed) and executable, meaning it has a main method and can simply be copied, pasted and run. Do not use a GdxTest, as that cannot be copy, pasted and run.
	<h4>Example resources</h4>
	Often executable examples need resources, such as an image or sound file. It is extra work for those trying to help if they must download your specific resources. Instead, whenever possible you should <b>use resources from the gdx-tests project by creating your example in the gdx-tests-lwjgl project</b>. This enables your example code to be simply pasted into the gdx-tests-lwjgl project and run.<br><br>
	<span class="dont">Do not:</span> Provide a code snippet that is not executable. Unless you are blatantly misusing the API, most problems cannot be solved just by looking at a code snippet. Code snippets lead to vague guesses at what might be wrong instead of a real answer to your problem.<br><br>
	<span class="do">Do:</span> Provide example code that can be <b>copied, pasted into the gdx-tests-lwjgl project and run</b>. Your code must have a main method, all code needed must be provided, etc.<br><br>
	<span class="dont">Do not:</span> Provide code that cannot be copied, pasted into the gdx-tests-lwjgl project and run.<br><br>
	<span class="dont">Do not:</span> Provide a code snippet that is not executable. Code snippets show that you are unwilling to put in the effort to properly present your problem and that you do not respect the time that others spend to help you. In most cases you might as well not ask your question.<br><br>
	<span class="do">Do:</span> Use the radio buttons below as a barebones starting point to build your executable example. Paste into the gdx-tests-lwjgl project, add your code, done.<br><br>
	Barebones executable example:<br>
	<label><input type="radio" name="bare" value="app" checked> Application</label><br>
	<label><input type="radio" name="bare" value="batch"> SpriteBatch</label><br>
	<label><input type="radio" name="bare" value="stage"> Stage</label><br>
	<label><input type="radio" name="bare" value="none"> None</label><br><br>
	<textarea id="app">import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL10;

public class Barebones extends ApplicationAdapter {
    public void create () {
        // your code here
    }

    public void render () {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // your code here
    }

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new Barebones());
    }
}</textarea>
	<textarea id="batch" style="display:none">import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BarebonesBatch extends ApplicationAdapter {
    SpriteBatch batch;
    Texture texture;

    public void create () {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("data/badlogic.jpg"));
    }

    public void render () {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texture, 100, 100);
        batch.end();
    }

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new BarebonesBatch());
    }
}</textarea>
	<textarea id="stage" style="display:none">import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class BarebonesStage extends ApplicationAdapter {
    Stage stage;

    public void create () {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        Label label = new Label("Some Label", skin);
        TextButton button = new TextButton("Some Button", skin);

        Table table = new Table();
        stage.addActor(table);
        table.setFillParent(true);

        table.debug();
        table.defaults().space(6);
        table.add(label);
        table.add(button);
    }

    public void render () {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.draw();
        Table.drawDebug(stage);
    }

    public static void main (String[] args) throws Exception {
        new LwjglApplication(new BarebonesStage());
    }
}</textarea>
	<textarea id="none" style="display:none"></textarea><br><br>
	This is the final step. An executable example is very important, please do not skip it.<br><br>
	<? nextButton(); ?>
	<script>
	$("input[name='bare']").click(function () {
		var height = $("textarea:visible").height();
		$("textarea").hide();
		$("#" + this.value).show().height(height);
	});
	function next () {
		var code = $.trim($("textarea:visible").val());
		if (!code || code.length < 10) return alert("Please provide executable example code.");
		$('#main [name=code]').val(code);
		return true;
	}
	</script>
	<?
	break;
case 8: ?>
	<h3>Finished!</h3>
	Thank you for completing this wizard. Please use the information below to post your issue on the <a href="http://www.badlogicgames.com/forum/" target="_blank">forum</a>. The [WIZ] tag in the title will ensure that your thread stands out as being a well thought out, higher priority issue.<br>
	<h4>Title</h4>
	[WIZ] <?= $_REQUEST["title"] ?>
	<h4>Body</h4>
<textarea><?
if ($_REQUEST["backend"]) echo  "Backend:\n" . trim($_REQUEST["backend"]) . "\n\n";
echo "Problem context:\n" . $_REQUEST["context"] . "\n\n";
echo "Problem statement:\n" . $_REQUEST["problem"] . "\n\n";
if ($_REQUEST["exception"]) echo "Error:\n[code]" . $_REQUEST["exception"] . "[/code]\n\n";
echo "Executable example code:\n[code]" . $_REQUEST["code"] . "[/code]\n\n";
?></textarea>
	<?
	exit;
}

?>
<form action="<?= $_SERVER["PHP_SELF"] ?>" method="POST" id="main">
<input type="hidden" name="step" value="<?= ++$step ?>">
<input type="hidden" name="backend" value="<?= $_REQUEST["backend"] ?>">
<input type="hidden" name="title" value="<?= $_REQUEST["title"] ?>">
<input type="hidden" name="context" value="<?= $_REQUEST["context"] ?>">
<input type="hidden" name="problem" value="<?= $_REQUEST["problem"] ?>">
<input type="hidden" name="exception" value="<?= $_REQUEST["exception"] ?>">
<input type="hidden" name="code" value="<?= $_REQUEST["code"] ?>">
</form>

</div>

</body>
</html>
