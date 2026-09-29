package galaxym4.dev.command;

import galaxym4.dev.model.Shape;

public class MoveCommand implements Command {
    private Shape shape;
    private double dx, dy;

    public MoveCommand(Shape shape, double dx, double dy) {
        this.shape = shape;
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void execute() {
        shape.move(dx, dy);
    }

    @Override
    public void undo() {
        shape.move(-dx, -dy);
    }
}
