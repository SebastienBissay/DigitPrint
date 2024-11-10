package mycellium;

import java.util.ArrayList;

import static parameters.Parameters.*;
import static processing.core.PApplet.floor;

public class Mycellium {

    ArrayList<Cell> cells;

    public Mycellium() {
        cells = new ArrayList<Cell>();
        cells.add(new Cell(floor(WIDTH / 2f), floor(HEIGHT / 2f)));
    }

    public boolean grow() {
        boolean hasNew = false;
        for (int i = 0; i < cells.size(); i++) {
            Cell c = cells.get(i);
            c.senseField();
            if (c.getBestField() < MAX_ACCEPTABLE_FIELD_VALUE) {
                Cell newCell = c.mitosis();
                newCell.generateField();
                cells.add(newCell);
                hasNew = true;
            }
        }
        return hasNew;
    }

    public void render() {
        cells.forEach(c -> c.getChildren().forEach(c::drawLine));
    }
}
