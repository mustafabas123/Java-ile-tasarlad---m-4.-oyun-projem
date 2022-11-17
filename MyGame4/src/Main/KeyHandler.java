package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	GamePanel gp;
	public boolean leftPressed,rightPressed;
	
	public KeyHandler(GamePanel gp) {
		this.gp=gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code=e.getKeyCode();
		if(code==KeyEvent.VK_A) {
			leftPressed=true;
		}
		if(code==KeyEvent.VK_D) {
			rightPressed=true;
		}
		if(code==KeyEvent.VK_SPACE) {
			gp.atesler.add(new Ates(500,gp.uzayGemisiX+28));
			gp.playSoundEffect(1);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code=e.getKeyCode();
		if(code==KeyEvent.VK_A) {
			leftPressed=false;
		}
		if(code==KeyEvent.VK_D) {
			rightPressed=false;
		}
		
	}
	

}
