package galaxym4.dev;
import galaxym4.dev.command.AddCommand;
import galaxym4.dev.command.CommandHistory;
import galaxym4.dev.command.GroupCommand;
import galaxym4.dev.command.MoveCommand;
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
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private enum AppMode { DRAW, MOVE, SELECT }
    private AppMode currentMode = AppMode.DRAW;
    private ShapeType selectedShapeType = ShapeType.CIRCLE;

    private ShapeGroup mainCanvas = new ShapeGroup();
    private CommandHistory history = new CommandHistory();

    private Shape selectedShape = null;
    private double lastX, lastY;
    private double startDragX, startDragY;

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

        Button btnCircle = new Button("Agregar Circle");
        Button btnRectangle = new Button("Agregar Rectangle");
        Button btnMove = new Button("Mover Figura");
        Button btnSelect = new Button("Seleccionar");
        Button btnGroup = new Button("Agrupar");
        Button btnUndo = new Button("Deshacer");
        Button btnRedo = new Button("Rehacer");

        toolbar.getChildren().addAll(btnCircle, btnRectangle, btnMove, btnSelect, btnGroup, btnUndo, btnRedo);
        root.setTop(toolbar);

        btnCircle.setOnAction(e -> { currentMode = AppMode.DRAW; selectedShapeType = ShapeType.CIRCLE; });
        btnRectangle.setOnAction(e -> { currentMode = AppMode.DRAW; selectedShapeType = ShapeType.RECTANGLE; });
        btnMove.setOnAction(e -> currentMode = AppMode.MOVE);
        btnGroup.setOnAction(e -> {
            List<Shape> selectedShapes = new ArrayList<>();
            for (Shape s : mainCanvas.getShapes()) {
                if (s.isSelected()) {
                    selectedShapes.add(s);
                }
            }

            if (selectedShapes.size() > 1) {
                GroupCommand cmd = new GroupCommand(mainCanvas, selectedShapes);
                history.executeCommand(cmd);
                redraw(gc, canvas);
            }
        });
        btnSelect.setOnAction(e -> currentMode = AppMode.SELECT);
        btnUndo.setOnAction(e -> { history.undo(); redraw(gc, canvas); });
        btnRedo.setOnAction(e -> { history.redo(); redraw(gc, canvas); });

        canvas.setOnMousePressed(e -> {
            if (currentMode == AppMode.DRAW) {
                Shape newShape = ShapeFactory.createShape(selectedShapeType, e.getX(), e.getY());
                AddCommand command = new AddCommand(mainCanvas, newShape);
                history.executeCommand(command);
                redraw(gc, canvas);
            }
            else if (currentMode == AppMode.MOVE) {
                selectedShape = mainCanvas.getClickedShape(e.getX(), e.getY());
                if (selectedShape != null) {
                    lastX = e.getX(); lastY = e.getY();
                    startDragX = e.getX(); startDragY = e.getY();
                    scrollPane.setPannable(false);
                }
            }
            else if (currentMode == AppMode.SELECT) {
                Shape clickedShape = mainCanvas.getClickedShape(e.getX(), e.getY());
                if (clickedShape != null) {
                    clickedShape.setSelected(!clickedShape.isSelected());
                } else {
                    mainCanvas.setSelected(false);
                }
                redraw(gc, canvas);
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (currentMode == AppMode.MOVE && selectedShape != null) {
                double dx = (e.getX() - lastX);
                double dy = (e.getY() - lastY);

                selectedShape.move(dx, dy);

                lastX = e.getX();
                lastY = e.getY();
                redraw(gc, canvas);
            }
        });

        canvas.setOnMouseReleased(e -> {
            if (currentMode == AppMode.MOVE && selectedShape != null) {
                double totalDx = (e.getX() - startDragX);
                double totalDy = (e.getY() - startDragY);

                if (totalDx != 0 || totalDy != 0) {
                    selectedShape.move(-totalDx, -totalDy);

                    MoveCommand command = new MoveCommand(selectedShape, totalDx, totalDy);
                    history.executeCommand(command);
                }

                selectedShape = null;
                scrollPane.setPannable(true);
            }
        });

        Scene scene = new Scene(root, 800, 600);
        scene.setOnKeyPressed(e -> {
            if (e.isControlDown() && e.getCode() == javafx.scene.input.KeyCode.Z) { history.undo(); redraw(gc, canvas); }
            else if (e.isControlDown() && e.getCode() == javafx.scene.input.KeyCode.Y) { history.redo(); redraw(gc, canvas); }
        });

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