package galaxym4.dev.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Shape {
    private float x, y, radius = 30;

    public Circle(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(x, y, radius, radius);
    }
    @Override
    public void move(float dx, float dy) {
        this.x += dx;
        this.y += dy;
    }
}
