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
    public void move(double dx, double dy) {
        for (Shape shape : shapes) shape.move(dx, dy);
    }

    @Override
    public boolean contains(double x, double y) {
        for (Shape s : shapes) {
            if (s.contains(x, y)) return true;
        }
        return false;
    }

    public Shape getClickedShape(double x, double y) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape s = shapes.get(i);
            if (s.contains(x, y)) {
                return s;
            }
        }
        return null;
    }

}
