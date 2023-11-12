package project;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class TransparentScrollBarUI extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors() {
        // 스크롤바의 색상을 투명하게 설정
        thumbColor = new Color(0, 0, 0);
        thumbDarkShadowColor = new Color(0, 0, 0);
        thumbHighlightColor = new Color(0, 0, 0);
        thumbLightShadowColor = new Color(0, 0, 0);
        trackColor = new Color(0, 0, 0);
        trackHighlightColor = new Color(0, 0, 0);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createEmptyButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createEmptyButton();
    }

    private JButton createEmptyButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}

