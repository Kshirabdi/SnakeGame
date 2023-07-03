package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel<FontMetrices> extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 20;//dimention of unit pixel
	static final int GAME_UNITS = (SCREEN_HEIGHT * SCREEN_WIDTH)/UNIT_SIZE; 
	static final int DELAY =90; //time to display new target
	static int HIGH_SCORE=0;
	//creating 2 arrays which contains the x and y co- ordinates
	static int x[] = new int [GAME_UNITS];//array size is equal to the AREA of the board devide by unit size
	static int y[] = new int [GAME_UNITS];
	int bodyParts = 2 ; //body part of the snake in the beginning;
	int targetCaptured;
	int targetX; //visibility of target in x coordinate randomly 
	int targetY; //visibility of target in y coordinate randomly
	
	/** snake direction of mooving in the beginning
	 * 
	 * -> U => for uward move
	 * -> L => left
	 * -> R => right
	 * -> D => down
	 * 
	 */
	char direction = 'R';
	boolean running=false;//at the begin its not moving
	
	Timer timer;//timer class
	Random random; //random class
	
	
	//creating constructor of GamePanel
	GamePanel()
	{
		random=new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	public void startGame()
	{
		newTarget();
		running = true;
		timer = new Timer(DELAY,this);//this is to access actionListener interface
		timer.start();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g)
	{
		if(running)
		{
			//for grids
			/*
			 * for(int i = 0 ; i<SCREEN_HEIGHT / UNIT_SIZE;i++)
			 
			{
				g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
				g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
			}*/
			
			if(targetCaptured %10 == 0 && targetCaptured >0)
			{
				g.setColor(Color.BLUE);
				g.fillOval(targetX, targetY, 25, 25);
				
			}
			else
			{
				g.setColor(Color.yellow);
				g.fillOval(targetX, targetY, UNIT_SIZE, UNIT_SIZE);
			}
			for(int i=0;i< bodyParts;i++)
			{
				if(i==0)
				{
					g.setColor(Color.orange);
					
					g.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else
				{
					g.setColor(Color.gray);
					//g.setColor(new Color (random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillOval(x[i], y[i], 18, 18);
				}
			}
			//Show score
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free",Font.BOLD,30));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("Score: "+ targetCaptured,(SCREEN_WIDTH - metrics1.stringWidth("Schore: "+ targetCaptured))/2,g.getFont().getSize());
		}
		else
		{
			gameOver(g);
		}
		
	}
	public void newTarget()
	{
		targetX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))* UNIT_SIZE;
		targetY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))* UNIT_SIZE;
	}
	public void move()
	{
		for (int i=bodyParts;i>0;i--)
		{
			x[i]=x[i-1];//shifting array co ordinations
			y[i]=y[i-1];// "
		}
		switch (direction)
		{
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		
		}
		
	}
	public void checkTarget()
	{
		if((x[0]==targetX) && (y[0] == targetY))
		{
			bodyParts++;
			targetCaptured++;
			if(targetCaptured %10 == 0)
				targetCaptured +=5;
				
			newTarget();
		}
		
	}
	public void checkCollisions()
	{
		//checks if head collides with body
		for(int i=bodyParts;i>0;i--)
		{
			if(x[0] == x[i] && y[0] == y[i])
			{
				running =false;
			}
		}
		//check  if head touches left border
		if(x[0] < 0)
		{
			running =false;
		}
		//check  if head touches right border
		if(x[0] == SCREEN_WIDTH)
		{
			running = false;
		}
		//check if head touches top border
		if(y[0] < 0)
		{
			running = false;
		}
		//check if head touches bottom border
		if(y[0] == SCREEN_HEIGHT)
		{
			running = false;
		}
		if(!running)
		{
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g)
	{
		//Show score
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free",Font.BOLD,30));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+ targetCaptured,(SCREEN_WIDTH - metrics1.stringWidth("Score: "+ targetCaptured))/2,g.getFont().getSize());
		//Show Highest score
		if(targetCaptured >HIGH_SCORE)
			HIGH_SCORE=targetCaptured;
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free",Font.BOLD,30));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Highest Score: "+ HIGH_SCORE,(SCREEN_WIDTH - metrics2.stringWidth("Highest Score: "+ HIGH_SCORE))/2,SCREEN_HEIGHT/4);
		
		//Game over text
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over...",(SCREEN_WIDTH - metrics.stringWidth("Game Over..."))/2,SCREEN_HEIGHT/2);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running)
		{
			move();
			checkTarget();
			checkCollisions();
		}
		repaint();
		
	}
	public class MyKeyAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case  KeyEvent.VK_LEFT :
				if (direction != 'R')
				{
					direction ='L';
				}
				break;
			case  KeyEvent.VK_RIGHT :
				if (direction != 'L')
				{
					direction ='R';
				}
				break;
			case  KeyEvent.VK_UP :
				if (direction != 'D')
				{
					direction ='U';
				}
				break;
			case  KeyEvent.VK_DOWN:
				if (direction != 'U')
				{
					direction ='D';
				}
				break;
			}
			
		}
		
	}

}
