# testfx-demo
Demo of how to test a very simple javafx application using testfx

## Requirements
Java version 8 or greater.

## Building
After aquiring the source code, run the following in the topmost project directory

    mvn clean install

If that succeeds you will have an executable jar file in the `target` directory and you will have seen a GUI unit test execute during the build process.

## How TestFX works

On a high level overview, TestFX works by accessing the nodes added to the scene graph and then checking and manipulating those nodes during tests.

TestFX identifies specific nodes in the scene graph in two ways. Firstly, when traversing the scene graph it will check the contents of its text attribute 
and identify nodes by their current text. This has an issue however. If two different scene graph nodes have the same text, then the scene graph query will return
the node that was first encountered. The second option is to search for a CSS ID. When traversing the scene graph it will check the CSS ID that has been 
applied to the node. If the ID matches the ID provided in the node query, then it will return that node.

Using these two methods to get nodes on the scene graph, TestFX allows you to test their functionality.

Since you have the pointers to the nodes you can manipulate them through their public methods, but TestFX also provides the functionality to simulate user input
through the mouse pointer. They do this throught the `clickOn` method in the `TestFXRobot` class. This method takes a string argument that is either the CSS ID of
the scene graph node or the value of text property of the desired scene graph node. TestFX will then move the mouse pointer to the center of the found node, and
click the mouse pointer. This actually moves the mouse pointer, so if you move the mouse when this is happening the test will fail. This can be overcome by
aquiring the scene graph node you want to click, and then using the `fire` method to fire an event on the node. This needs to happen on the JavaFX application thread
and not the thread the unit test is likely running in. Using a future can allow the test thread to synchronize with the application thread. Hand a future to the
application thread and fire the node. Retrieve the future in the test thread. This will cause the test thread to wait for the application thread.

```
public void myClickOn(Button button) throws Exception
{
    CompletableFuture<Void> future = new CompletableFuture<>();

    Platform.runLater(() ->
    {
        button.fire();
        future.complete(null);
    });

    future.get();
}

```

## An Example Test

```
@Test
public void clickOnButton()
{
	// Get the button.
	Button button = lookup("#button-id").query();
	// Get the text field the button will modify.
	TextField textField = lookup("#textfield-id").query();

	myClickOn(button);

	assertEquals("Some text", textField.getText());
}
```
