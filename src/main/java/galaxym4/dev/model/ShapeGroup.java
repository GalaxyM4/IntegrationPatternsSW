package galaxym4.dev.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class ShapeGroup implements Shape {
    private List<Shape> shapes =  new ArrayList<Shape>();
    private boolean selected = false;

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public void remove(Shape shape) {
        shapes.remove(shape);
    }

    public List<Shape> getShapes() {
        return shapes;
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

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        for (Shape s : shapes) {
            s.setSelected(selected);
        }
    }

    @Override
    public boolean isSelected() {
        return selected;
    }
}
