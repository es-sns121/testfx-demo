package clickapp;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClickApplicationTest extends ApplicationTest
{
    @Override 
    public void start(Stage stage)
    {
        Parent sceneRoot = new ClickApplication.ClickPane();
        Scene scene = new Scene(sceneRoot, 300, 60);
        stage.setScene(scene);
        stage.show();
    }

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
    
    /**
     * The standard TestFX way of testing a GUI.
     */
    @Test 
    public void clickOnButton()
    {

        /*
         *  Alternative ways of identifying a scene graph node.
         *
         *  clickOn(".button");
         *  clickOn("click me!"); 
         */
        
        clickOn("#button-1");
        
        TextField textField = (TextField) lookup("#textfield-1").query();
        assertEquals("You clicked the button.", textField.getText());
        textField.setText("");
    }
    
    /**
     * A way of testing a GUI using TestFX that does not require manipulation of the actual mouse pointer.
     * @throws Exception
     */
    @Test 
    public void myClickOnButton() throws Exception
    {
        Button button = (Button) lookup("#button-1").query();
        myClickOn(button);
        
        TextField textField = (TextField) lookup("#textfield-1").query();
        assertEquals("You clicked the button.", textField.getText());
        textField.setText("");
    }
    
}
