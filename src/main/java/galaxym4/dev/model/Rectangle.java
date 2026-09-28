package galaxym4.dev.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle implements Shape {
    private float x, y, width = 30, height = 50;
    public Rectangle(float x, float y) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(x, y, width, height);
    }
    @Override
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }
}
