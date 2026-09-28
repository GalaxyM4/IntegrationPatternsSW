package galaxym4.dev.model;

import javafx.scene.canvas.GraphicsContext;

public interface Shape {
    void draw(GraphicsContext gc);
    void move(float dx, float dy);
}
