import mycellium.Cell;
import mycellium.Mycellium;
import processing.core.PApplet;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class DigitPrint extends PApplet {
    private Mycellium organism;

    public static void main(String[] args) {
        PApplet.main(DigitPrint.class);
    }

    @Override
    public void settings() {
        size(SCALE * WIDTH, SCALE * HEIGHT);
        randomSeed(SEED);
        noiseSeed(floor(random(MAX_INT)));
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        stroke(STROKE_COLOR.red(), STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());

        Cell.initGrid(this);
        organism = new Mycellium();
    }

    @Override
    public void draw() {
        scale(SCALE);
        organism.render();

        if (!organism.grow()) {
            noLoop();
            saveSketch(this);
        }

    }
}
