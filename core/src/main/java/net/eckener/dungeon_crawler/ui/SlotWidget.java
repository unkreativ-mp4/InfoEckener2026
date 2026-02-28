package net.eckener.dungeon_crawler.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import net.eckener.dungeon_crawler.items.ItemStack;

public class SlotWidget extends Stack {

    private Image slotItemTexture;
    private Label itemAmount;

    public SlotWidget(ItemStack pItemStack, BitmapFont font) {

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
