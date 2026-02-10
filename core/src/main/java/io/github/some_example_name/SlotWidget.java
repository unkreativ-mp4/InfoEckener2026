package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class SlotWidget extends Table {

    private ItemStack itemStack;
    private Image slotItemTexture;
    private Label itemAmount;

    public SlotWidget(ItemStack pItemStack, BitmapFont font) {

        this.itemStack = pItemStack;
        slotItemTexture = new Image();
        slotItemTexture.setVisible(false);

        Label.LabelStyle style = new Label.LabelStyle(font, null);
        itemAmount = new Label("", style);
        itemAmount.setAlignment(Align.bottomRight);
        itemAmount.setVisible(false);

        add(slotItemTexture);
        add(itemAmount);

        setStack(pItemStack);
    }

    public void clearStack() {
        slotItemTexture.setDrawable(null);
        slotItemTexture.setVisible(false);
        itemAmount.setText("");
        itemAmount.setVisible(false);
    }

    public void setStack(ItemStack stack) {
        if (stack == null) {
            clearStack();
            return;
        }

        TextureRegion region = new TextureRegion(stack.getItem().getItemTexture());
        slotItemTexture.setDrawable(new TextureRegionDrawable(region));
        slotItemTexture.setVisible(true);

        if (stack.getAmount() > 1) {
            itemAmount.setText(Integer.toString(stack.getAmount()));
            itemAmount.setVisible(true);
        } else {
            itemAmount.setVisible(false);
        }
    }



}
