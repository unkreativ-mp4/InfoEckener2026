package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class CustomLabel {
    private String text;
    private final Label label;
    private int xPosition;
    private int yPosition;

    public CustomLabel(String text, int xPosition, int yPosition){
        this.text = text;
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        BitmapFont font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;

        label = new Label(text, style);
        label.setPosition(xPosition, yPosition); // screen-space pixels
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        label.setText(text);
    }

    public Label getLabel() {
        return label;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
        label.setPosition(xPosition, yPosition);
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
        label.setPosition(xPosition, yPosition);
    }
}
