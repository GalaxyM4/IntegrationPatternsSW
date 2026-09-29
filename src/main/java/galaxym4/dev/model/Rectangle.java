package galaxym4.dev.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle implements Shape {
    private double x, y, width = 30, height = 50;
    private boolean selected;
    public Rectangle(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(selected ? Color.GREEN : Color.BLUE);
        gc.fillRect(x, y, width, height);
    }
    @Override
    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }
    @Override
    public boolean contains(double x, double y) {
        return x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height;
    }
    @Override
    public void setSelected(boolean selected) { this.selected = selected; }

    @Override
    public boolean isSelected() { return selected; }
}
