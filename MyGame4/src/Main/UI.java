package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class UI {
	GamePanel gp;
	Font arial_20,arial_80B;
	Graphics2D g2;
	public UI(GamePanel gp) {
		this.gp=gp;
		arial_20=new Font("Arial",Font.PLAIN ,20 );
		arial_80B=new Font("Arial",Font.BOLD,80);
	}
	public void draw(Graphics2D g2) {
		this.g2=g2;
		String text="";
		g2.setFont(arial_20);			
		g2.setColor(Color.white);
		text="Puan:"+gp.Puan;
		g2.drawString(text,500,40);
		drawLife();
		
	}
	public void drawLife() {
		
		int x=20;
		int y=20;
		int i=0;
		
		while (i<gp.MaxLife/2) {
			g2.drawImage(gp.Heart_Blank,x,y,50,50,null);
			i++;
			x+=50;
		}
		x=20;
		y=20;
		i=0;
		while(i<gp.life) {
			g2.drawImage(gp.heart_half,x,y,50,50,null);
			i++;
			if(i<gp.life) {
				g2.drawImage(gp.Heart_Full,x,y,50,50,null);
			}
			i++;
			x+=50;
		}
	}

}
