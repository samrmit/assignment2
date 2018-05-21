import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
public class Guess extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        double WIDTH = 400;
        double HEIGHT = 400;
        Pane pane = new Pane();
        for (int i = 0; i < 8; i++) {
            boolean isWhite = i % 2 == 0;
            for (int j = 0; j < 8; j++) {
                Rectangle rectangle
                        = new Rectangle(i * WIDTH / 8, j * HEIGHT / 8,
                        WIDTH / 8, HEIGHT / 8);
                rectangle.setStroke(Color.BLACK );
                if (isWhite) {
                    rectangle.setFill(Color.WHITE );
                } else {
                    rectangle.setFill(Color.BLACK );
                }
                isWhite = !isWhite;
                pane.getChildren().add(rectangle);
            }
        }
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        primaryStage.setTitle("Guess"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
    public static void main(String[] args) {
        launch(args);
    }
}
