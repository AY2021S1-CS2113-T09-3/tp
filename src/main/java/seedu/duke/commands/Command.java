package seedu.duke.commands;

public abstract class Command {
    public abstract void execute();

    public abstract boolean isExit();
}