package galaxym4.dev.command;

import galaxym4.dev.model.Shape;
import galaxym4.dev.model.ShapeGroup;

import java.util.ArrayList;
import java.util.List;

public class GroupCommand implements Command {
    private ShapeGroup mainCanvas;
    private ShapeGroup newGroup;
    private List<Shape> shapesToGroup;

    public GroupCommand(ShapeGroup mainCanvas, List<Shape> shapesToGroup) {
        this.mainCanvas = mainCanvas;
        this.shapesToGroup = new ArrayList<>(shapesToGroup);
        this.newGroup = new ShapeGroup();

        for (Shape s : this.shapesToGroup) {
            this.newGroup.add(s);
        }
    }

    @Override
    public void execute() {
        for (Shape s : shapesToGroup) {
            mainCanvas.remove(s);
        }
        mainCanvas.add(newGroup);
        newGroup.setSelected(true);
    }

    @Override
    public void undo() {
        mainCanvas.remove(newGroup);
        for (Shape s : shapesToGroup) {
            mainCanvas.add(s);
        }
    }
}