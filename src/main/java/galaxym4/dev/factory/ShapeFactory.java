package galaxym4.dev.factory;

import galaxym4.dev.model.Circle;
import galaxym4.dev.model.Rectangle;
import galaxym4.dev.model.Shape;

public class ShapeFactory {
    public static Shape createShape(ShapeType type, double x, double y) {
        switch (type) {
            case CIRCLE:
                return new Circle(x, y);
            case RECTANGLE:
                return new Rectangle(x, y);
            default:
                throw new IllegalArgumentException("Unknown shape type: " + type);
        }
    }
}
