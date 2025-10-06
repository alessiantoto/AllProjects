package g62098.dev3.oxono.Model.Command;

import g62098.dev3.oxono.Model.OxonoException;

import java.util.Stack;

public class CommandManager {
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();


    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear(); // Clear redo history after a new command
    }

    public void undo() throws OxonoException {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
        else{
            throw new OxonoException("Nothing to undo");
        }
    }

    public void redo() throws OxonoException{
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
        else {
            throw new OxonoException("Nothing to redo");
        }
    }

    public void clearHistory(){
        undoStack.clear();
        redoStack.clear();
    }
}
