/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 *
 * @author kranti
 */
public class VisualizationPanel extends JPanel{
    
    private static BufferedImage imageBuffer;
 
    public VisualizationPanel(int width, int height, BufferedImage imageBuffer){
        this.setSize(width, height);
        VisualizationPanel.imageBuffer = imageBuffer;
    }
    
    @Override
    protected void paintComponent(Graphics g){
        if(null != imageBuffer)
            g.drawImage(imageBuffer, 0, 0, null);
        else
            System.err.println("null image");
    }
    
}
