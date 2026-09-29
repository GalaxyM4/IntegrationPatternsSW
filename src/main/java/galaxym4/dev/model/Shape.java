package galaxym4.dev.model;

import javafx.scene.canvas.GraphicsContext;

public interface Shape {
    void draw(GraphicsContext gc);
    void move(double dx, double dy);
    boolean contains(double x, double y);
    void setSelected(boolean selected);
    boolean isSelected();
}
