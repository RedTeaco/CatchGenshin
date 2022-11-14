package org.redtea.GUI.Controller;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Eden_Leaf
 */
@Getter
public class label extends JLabel {
    public label(String name) {
        super.setText(name);
        super.setFont(getSelfDefinedFont(new File("").getAbsolutePath() + "\\src\\main\\resources\\fonts\\Genshin.ttf", 18));
        super.setVisible(true);
    }

    public label(String name, String filename) {
        String path = new File("").getAbsolutePath() + "\\src\\main\\resources\\";
        super.setText(name);
        super.setFont(getSelfDefinedFont(path + "fonts\\Genshin.ttf", 18));
        setImg(path + "icons\\" + filename);
        super.setSize(250, 150);
        super.setVisible(true);
    }

    public static java.awt.Font getSelfDefinedFont(String filepath, int Size) {
        java.awt.Font font;
        File file = new File(filepath);
        try {
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, file);
            font = font.deriveFont(java.awt.Font.PLAIN, Size);
        } catch (FontFormatException | IOException e) {
            return null;
        }
        return font;
    }

    public static java.awt.Font getSelfDefinedFont(String filepath) {
        java.awt.Font font;
        File file = new File(filepath);
        try {
            font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, file);
            font = font.deriveFont(java.awt.Font.PLAIN);
        } catch (FontFormatException | IOException e) {
            return null;
        }
        return font;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
    }

    public void setImg(String filename) {
        ImageIcon icon = new ImageIcon(filename);
        Image img = icon.getImage();
        img = img.getScaledInstance(75, 75, Image.SCALE_AREA_AVERAGING);
        icon.setImage(img);
        super.setIcon(icon);
    }
}