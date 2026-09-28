package galaxym4.dev.command;

public interface Command {
    void execute();
    void undo();
}
