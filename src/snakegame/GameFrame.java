package snakegame;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	//creating constructor of GameFrame
	@SuppressWarnings("rawtypes")
	GameFrame()
	{
		/*GamePanel panel = new GamePanel();
		*this.add(panel);
		*
		* in short form
		*/
		this.add(new GamePanel());
		this.setTitle("Snake Game ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}

}
