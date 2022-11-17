package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.text.StyledEditorKit.ForegroundAction;


public class GamePanel extends JPanel implements Runnable{
	BufferedImage image1,image2;
	public BufferedImage Heart_Full,Heart_Blank,heart_half;
	public ArrayList<Uzayli>Uzaylilar=new ArrayList<>();
	public ArrayList<Ates>atesler=new ArrayList<>();
	static final int  Screen_Width=600;
	static final int  Screen_Height=600;
	static final int  Unit_Size=25;
	static final int Game_units=(Screen_Width*Screen_Height)/Unit_Size;
	static int  CisimX;
	public final int UzayliDirY=1;
	int UzayGemisiDir=20;
	int counter=0;
	public int atesDirY=2;
	
	public int Puan=0;
	public int MaxLife=6;
	public int life=MaxLife;
	public int uzayGemisiX;
	
	boolean running=false;
	Random random;
	Thread gameThread;
	int FPS=60;
	KeyHandler keyHandler=new KeyHandler(this);
	UI ui=new UI(this);
	sound SoundEffec=new sound();
    
	
	public GamePanel() {
		
		try {
			image1=ImageIO.read(getClass().getResourceAsStream("/res/spaceShip12.png"));
			image2=ImageIO.read(getClass().getResourceAsStream("/res/uzayli.png"));
			Heart_Full=ImageIO.read(getClass().getResourceAsStream("/res/heart_full.png"));
			heart_half=ImageIO.read(getClass().getResourceAsStream("/res/heart_half.png"));
			Heart_Full=ImageIO.read(getClass().getResourceAsStream("/res/heart_full.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		random=new Random();
		this.setPreferredSize(new Dimension(Screen_Width, Screen_Height));	
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(keyHandler);
		startGame();
	}
	public void startGame() {
		newCisim();
		running=true;
    	gameThread=new Thread(this);
    	gameThread.start();
		
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2=(Graphics2D) g;
		super.paintComponent(g);
		if(running==true) {
			g2.drawImage(image1,uzayGemisiX,520,image1.getWidth()/3,image1.getHeight()/3,this);
			//g2.drawImage(image2,CisimX,0,Unit_Size*3,Unit_Size*3,this);
			counter++;
			if(counter==120) {
				Uzaylilar.add(new Uzayli(CisimX,0));
				newCisim();
				counter=0;
			}
			for (Uzayli uzayli:Uzaylilar) {
				g2.drawImage(image2,uzayli.getUzayliX(),uzayli.getUzayliY(),Unit_Size*3,Unit_Size*3,this);
			}
			g2.setColor(Color.red);
			for(Ates ates:atesler) {
				g2.fillRect(ates.getAtesX(),ates.getAtesY(),10,20);
			}
			for(Uzayli uzayli:Uzaylilar) {
				if(uzayli.getUzayliY()>600) {
					Uzaylilar.remove(uzayli);
					life-=2;
				}
			}
			for(Ates ates:atesler) {
				if(ates.getAtesY()<0) {
					atesler.remove(ates);
				}
			}
			ui.draw(g2);
			kontrolEt();
			if(life==0) {
				running=false;
			}
			
		}
		else {
			gameOver(g2);
		}
		g2.dispose();
	}
	public void draw(Graphics g) {

		
	}
	public void CheckCollision() {
		
	}
	public void gameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Game Over",(Screen_Width-metrics.stringWidth("Game over "))/2,Screen_Height/2);
		
		g.setColor(Color.white);
//		g.setFont(new Font("Arial",Font.BOLD,50));
		g.drawString("Puan:"+Puan,200,450);
	}
	public void update() {

		if(running) {
			UzayGemisiMove();
			
		    for(Uzayli uzayli:Uzaylilar) {
				uzayli.setUzayliY(uzayli.getUzayliY()+UzayliDirY);
			}
		    for(Ates ates:atesler) {
		       ates.setAtesY(ates.getAtesY()-atesDirY);
		      }			
		}

		
	}
	public void UzayGemisiMove() {
		if(keyHandler.leftPressed==true || keyHandler.rightPressed==true) {
			if(keyHandler.rightPressed) {
				if(uzayGemisiX>=550) {
					uzayGemisiX=550;
				}
				else {
					uzayGemisiX+=UzayGemisiDir;
				}
				
			}
			else if(keyHandler.leftPressed) {
				if(keyHandler.leftPressed) {
					if(uzayGemisiX<=0) {
						uzayGemisiX=0;
					}
					else {
						uzayGemisiX-=UzayGemisiDir;
					}
					
				}
				
			}
		}

	}
	@Override
	public void run() {
		double drawInterval=1000000000/FPS;
		double delta=0;
		long lastTime=System.nanoTime();
		long currentTime;
		long timer=0;
		int drawCount=0;
		while(gameThread!=null) {
			currentTime=System.nanoTime();
			delta+=(currentTime-lastTime)/drawInterval;
			timer+=(currentTime-lastTime);
			lastTime=currentTime;
			if(delta>=1) {
				update();			
			    repaint();
			    delta--;
			    drawCount++;
			}
		
	}
		
	}
	public void newCisim() {
		CisimX=random.nextInt((int)Screen_Width/Unit_Size)*Unit_Size;
	}
	public void kontrolEt() {
		for(Ates ates :atesler) {
			for(Uzayli uzayli:Uzaylilar) {
				if(new Rectangle(ates.getAtesX(),ates.getAtesY(),10,20).intersects(new Rectangle(uzayli.getUzayliX(),uzayli.getUzayliY(),Unit_Size*3,Unit_Size*3))) {
				   playSoundEffect(0);
				   atesler.remove(ates);
				   Uzaylilar.remove(uzayli);
				   Puan+=5;
				   
				}
			}
		}
		
	}
	public void playMusic(int i) {
//		Sound.setFile(i);
//		Sound.play();
//		Sound.loop();
	}
	public void stopMusic() {
//		Sound.stop();
	}
	public void playSoundEffect(int i) {
		SoundEffec.setFile(i);
		SoundEffec.play();
	}

}
