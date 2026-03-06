package gui;

import java.util.Random;

import javax.swing.JPanel;
import javax.swing.ImageIcon;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Image;

public class Canvas extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D)g;
        
        Color[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
        int randColorIndex = 0;
        
        for(int i = 0; i < 100 ; i++) {
            graphics.drawLine(0+(i+100),100+(i+100),400+(i+100),0+(i+100));
            graphics.drawLine(0+(i+90),100+(i+90),400+(i+90),0+(i+90));
            graphics.drawLine(0+(i+80),100+(i+80),400+(i+80),0+(i+80));
	    graphics.drawLine(0+(i+70),100+(i+70),400+(i+70),0+(i+70));
	    graphics.drawLine(0+(i+60),100+(i+60),400+(i+60),0+(i+60));
	    graphics.drawLine(0+(i+50),100+(i+50),400+(i+50),0+(i+50));
	    graphics.drawLine(0+(i+40),100+(i+40),400+(i+40),0+(i+40));
	    graphics.drawLine(0+(i+30),100+(i+30),400+(i+30),0+(i+30));
	    graphics.drawLine(0+(i+20),100+(i+20),400+(i+20),0+(i+20));
	    graphics.drawLine(0+(i+10),100+(i+10),400+(i+10),0+(i+10));
	    graphics.drawLine(0+(i-10),100+(i-10),400+(i-10),0+(i-10));
	    graphics.drawLine(0+(i-20),100+(i-20),400+(i-20),0+(i-20));
	    graphics.drawLine(0+(i-30),100+(i-30),400+(i-30),0+(i-30));
                
            randColorIndex = new Random().nextInt(colors.length);
            graphics.setColor(colors[randColorIndex]);
        }
        
        graphics.setColor(Color.BLACK);
        graphics.fill3DRect(140,185,100,20,true);
        graphics.setColor(Color.WHITE);
        graphics.drawString("Emilio Quinones", 140, 200);
			
        ImageIcon imageOG = new ImageIcon("./gui/icons/logo.png");
        Image image = imageOG.getImage();
        Image scaledImage = image.getScaledInstance(98,199,Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(scaledImage);
            
        graphics.drawImage(logo.getImage(),220,20,null);
    }
}
