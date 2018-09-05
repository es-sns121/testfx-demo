package clickapp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClickApplication extends Application {
    // application for acceptance tests.
    @Override 
    public void start(Stage stage) {
        Parent sceneRoot = new ClickPane();
        Scene scene = new Scene(sceneRoot, 300, 60);
        stage.setScene(scene);
        stage.show();
    }

    // scene object for unit tests
    public static class ClickPane extends VBox {
        public ClickPane() {
            super();
            
            /* Important bits */
            
            Button button = new Button("click me!");
            button.setId("button-1");
            
            TextField textField = new TextField();
            textField.setId("textfield-1");

            /* -------------- */

            
            button.setMinSize(50, 15);
            textField.setMinSize(100, 15);
            button.setOnAction(actionEvent -> textField.setText("You clicked the button."));
            
            GridPane grid = new GridPane();
            
            grid.add(button, 0, 0);
            grid.add(textField, 1, 0);
            GridPane.setHgrow(button, Priority.ALWAYS);
            GridPane.setHgrow(textField, Priority.ALWAYS);
            grid.setVgap(10);
            grid.setHgap(10);
            grid.setPadding(new Insets(10));
           
            getChildren().addAll(grid);
        }
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
