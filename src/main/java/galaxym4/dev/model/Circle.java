package galaxym4.dev.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape {
    private double x, y, radius = 30;
    private boolean selected = false;

    public Circle(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(selected ? Color.GREEN : Color.BLUE); // Usa Color.RED en Rectangle
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }
    @Override
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
    }
    @Override
    public boolean contains(double x, double y) {
        return Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2) <= Math.pow(radius, 2);
    }
    @Override
    public void setSelected(boolean selected) { this.selected = selected; }
    @Override
    public boolean isSelected() { return selected; }
}
