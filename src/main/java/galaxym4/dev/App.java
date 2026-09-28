package galaxym4.dev;
import galaxym4.dev.command.AddCommand;
import galaxym4.dev.command.CommandHistory;
import galaxym4.dev.factory.ShapeFactory;
import galaxym4.dev.factory.ShapeType;
import galaxym4.dev.model.Circle;
import galaxym4.dev.model.Shape;
import galaxym4.dev.model.ShapeGroup;
import galaxym4.dev.model.Rectangle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private ShapeType selectedShapeType = ShapeType.CIRCLE;
    private ShapeGroup mainCanvas = new ShapeGroup();
    private CommandHistory history = new CommandHistory();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Canvas canvas = new Canvas(2000, 2000);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        ScrollPane scrollPane = new ScrollPane(canvas);
        scrollPane.setPannable(true);
        root.setCenter(scrollPane);

        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: #dddddd;");

        Button btnCircle = new Button("Agregar Círculo");
        Button btnRectangle = new Button("Agregar Rectángulo");
        Button btnUndo = new Button("Deshacer");
        Button btnRedo = new Button("Rehacer");

        toolbar.getChildren().addAll(btnCircle, btnRectangle, btnUndo, btnRedo);
        root.setTop(toolbar);

        btnCircle.setOnAction(e -> selectedShapeType = ShapeType.CIRCLE);
        btnRectangle.setOnAction(e -> selectedShapeType = ShapeType.RECTANGLE);

        btnUndo.setOnAction(e -> {
            history.undo();
            redraw(gc, canvas);
        });

        btnRedo.setOnAction(e -> {
            history.redo();
            redraw(gc, canvas);
        });

        canvas.setOnMouseClicked(e -> {
            Shape newShape = ShapeFactory.createShape(
                    selectedShapeType,
                    (int) e.getX(),
                    (int) e.getY()
            );

            AddCommand command = new AddCommand(mainCanvas, newShape);
            history.executeCommand(command);

            redraw(gc, canvas);
        });

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Interactive Whiteboard - Design Patterns");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void redraw(GraphicsContext gc, Canvas canvas) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        mainCanvas.draw(gc);
    }

    public static void main(String[] args) {
        launch(args);
    }
}