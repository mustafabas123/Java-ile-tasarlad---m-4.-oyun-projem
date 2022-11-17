package Main;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
	public GameFrame() {
		GamePanel gp=new GamePanel(); 
		this.add(gp);
		this.setTitle("Game of mustafa");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

}
