package galaxym4.dev.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class ShapeGroup implements Shape {
    private List<Shape> shapes =  new ArrayList<Shape>();

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public void remove(Shape shape) {
        shapes.remove(shape);
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (Shape shape : shapes) shape.draw(gc);
    }

    @Override
    public void move(float dx, float dy) {
        for (Shape shape : shapes) shape.move(dx, dy);
    }
}
