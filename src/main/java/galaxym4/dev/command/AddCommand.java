package galaxym4.dev.command;

import galaxym4.dev.model.Shape;
import galaxym4.dev.model.ShapeGroup;

public class AddCommand implements Command {
    private ShapeGroup canvas;
    private Shape shape;

    public AddCommand(ShapeGroup canvas, Shape shape) {
        this.canvas = canvas;
        this.shape = shape;
    }

    @Override
    public void execute() {
        canvas.add(shape);
    }

    @Override
    public void undo() {
        canvas.remove(shape);
    }
}