/*
 * renderizacao.java
 *
 * Created on 9 de Agosto de 2010, 22:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package megasena2;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Jefferson
 */
public class renderizacao extends JPanel {

    /** Creates a new instance of renderizacao */
    public renderizacao() {
        img = null;
    }

    public void paintComponent(Graphics g) {
        //super.paintComponent(g);

        GraphicsConfiguration gc = img.createGraphics().getDeviceConfiguration();
        BufferedImage out = gc.createCompatibleImage(WIDTH, WIDTH, Transparency.BITMASK);

        if (img != null) {
            Graphics2D g2 = (Graphics2D)g;
            /*
            Graphics2D gtemp = out.createGraphics();
            gtemp.drawImage(img, null, this);
            gtemp.dispose();
            */
            //Graphics2D g2d1 = out.createGraphics();

            g2.setComposite(AlphaComposite.Src);
            g2.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
            g2.dispose();
        }
    }

    void setImage(BufferedImage img) {
        this.img = img;
        repaint();
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    void setColorTransparency(BufferedImage image, Color color) {
        BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(image, null, 0, 0);
        g.dispose();
        for (int i = 0; i < dimg.getHeight(); i++) {
            for (int j = 0; j < dimg.getWidth(); j++) {
                if (dimg.getRGB(j, i) == color.getRGB()) {
                    dimg.setRGB(j, i, 0x8F1C1C);
                }
            }
        }
    }
    
    
    
    
    private int x;
    private int y;
    private BufferedImage img;
}
