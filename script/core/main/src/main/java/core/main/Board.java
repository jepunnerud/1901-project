package core.main;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private String boardName;
    private String description;
    private List<Note> notes = new ArrayList<>();
    private final int MAX_NOTES = 6;

    public Board(String boardName, String description) {
        this.boardName = boardName;
        this.description = description;
    }

    public Board(String boardName) {
        this.boardName = boardName;
        this.description = "";
    }

    public Board() {
        this.boardName = "";
        this.description = "";
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardDescription() {
        return description;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public void setBoardDescription(String description) {
        this.description = description;
    }

    public void addNote(Note note) {
        if (note != null && getNotes().size() < MAX_NOTES)
            notes.add(note);
    }

    @Override
    public String toString() {
        return notes.stream().map(i -> i.toString()).reduce("", (i, j) -> {
            return i + j + "\n";
        });
    }

}
