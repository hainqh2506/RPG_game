package enviroment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
	
	GamePanel gp;
	BufferedImage darkness;
	
	public Lighting(GamePanel gp, int size) {
		darkness = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D)darkness.getGraphics();
		
		Area screenArea = new Area(new Rectangle2D.Double(0,0,gp.screenWidth,gp.screenHeight));
		
		int centerX = 768;
		int centerY = 432;
		System.out.println(centerY);
		
		double x = 418;
		double y = 82;
		
		Shape circleShape = new Ellipse2D.Double(x,y,size,size);
		
		Area lightArea = new Area(circleShape);
		
		screenArea.subtract(lightArea);
		
		//Gradient
		Color color[] = new Color[5];
		float fraction[] = new float[5];
		
		color[0] = new Color(0,0,0,0f);
		color[1] = new Color(0,0,0,0.25f);
		color[2] = new Color(0,0,0,0.5f);
		color[3] = new Color(0,0,0,0.75f);
		color[4] = new Color(0,0,0,0.98f);
		
		fraction[0] = 0f;
		fraction[1] = 0.25f;
		fraction[2] = 0.5f;
		fraction[3] = 0.75f;
		fraction[4] = 1f;
		
		RadialGradientPaint gradient = new RadialGradientPaint(centerX,centerY,350,fraction,color);
		
		g2.setPaint(gradient);
		g2.fill(lightArea);
		
		g2.fill(screenArea);
		
		g2.dispose();
	}
	public void draw(Graphics2D g2) {
		g2.drawImage(darkness,0,0,null);
	}

}
