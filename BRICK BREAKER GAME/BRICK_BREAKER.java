package project;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class BRICK_BREAKER extends JPanel implements KeyListener,ActionListener, Runnable 
{
	int ballx = 160;
	int bally = 218;
	int batx =  160;
	int baty =  345;
	int brickx = 100;
	int bricky = 40;
	int brickBreadth = 50;
	int brickHeight = 30;
	
	int movex = -1;
	int movey = -1;
	
	int count = 0;
	String status;

	boolean bricksOver = false;
	boolean ballFallDown = false;
	
	static boolean right = false;
	static boolean left = false;
			
	Rectangle[] Brick = new Rectangle[12];
	Rectangle Ball = new Rectangle(ballx, bally, 8, 8);
	Rectangle Bat = new Rectangle(batx, baty, 70, 5);
//-----------------------------------------------------------------------------------------------------------------
	public void paint(Graphics g)   // PAINT METHOD.
	{
		 g.setColor(Color.LIGHT_GRAY);
		 g.fillRect(0, 0, 550, 450);
		 
		 g.setColor(Color.blue);
		 g.fillOval(Ball.x, Ball.y, Ball.width, Ball.height);
		 
		 g.setColor(Color.green);
		 g.fill3DRect(Bat.x, Bat.y, Bat.width, Bat.height, true);
		 
		 g.setColor(Color.GRAY);
		 g.fillRect(0, 360, 550, 200);   
		 
		 g.setColor(Color.red);
		 g.drawRect(0, 0, 543, 360);
		 
         for (int i = 0; i < Brick.length; i++) 
         {
        	 if (Brick[i] != null) 
        	 {
        	    g.fill3DRect (Brick[i].x, Brick[i].y, Brick[i].width, Brick[i].height, true);
             }
         }

         if (ballFallDown == true || bricksOver == true) 
         {
        	 Font f = new Font("Arial", Font.BOLD, 20);
        	 g.setFont(f);
        	 g.drawString(status, 70, 120);
        	 ballFallDown = false;
        	 bricksOver = false;
         }
	}
//-------------------------------------------------------------------------------------------------------------
	
	  public void run()   // RUN METHOD.
	  {
			createBricks();
			while (true) 
			{
				for (int i = 0; i < Brick.length; i++) 
				{
					if (Brick[i] != null) 
					{
						if (Brick[i].intersects(Ball)) 
						{
							Brick[i] = null;
							movey = -movey;
							count++;
							
						} // end of 2nd if..
					} // end of 1st if..
				} // end of for loop..

				if (count == Brick.length)  // check if ball hits all bricks
				{
					bricksOver = true;
					status = "YOU WON THE GAME";
					repaint();
				}
				repaint();
				Ball.x += movex;
				Ball.y += movey;

				if (left == true) 
				{
					Bat.x -= 3;
					right = false;
				}
				
				if (right == true)
				{
					Bat.x += 3;
					left = false;
				}
				
				if (Bat.x <= 4) 
				{
					Bat.x = 4;
				} 
				else if (Bat.x >= 298)
				{
					Bat.x = 298;
				}
				
   
				if (Ball.intersects(Bat))     //--> Ball reverses when strikes the bat
				{
					movey = -movey;			  // if(Ball.y + Ball.width >=Bat.y)

				}

				if (Ball.x <= 0 || Ball.x + Ball.height >= 343) //--> Ball reverses when touches left and right boundary
				{
					movex = -movex;
				}
				
				if (Ball.y <= 0)    // ball.y + Ball.height >= 250
				{
					movey = -movey;
				}
				
				if (Ball.y >= 350) 
				{
					ballFallDown = true;
					status = "YOU LOST THE GAME";
					repaint();

				}
  
				try 
				{
					Thread.sleep(10);
				} 
				catch (Exception ex) {}

          } // WHILE LOOP ENDS HERE.
     } // RUN METHOD CLOSES HERE. 
//-------------------------------------------------------------------------------------------------------
	  public void keyPressed(KeyEvent e) 
	  {
		  	int keyCode = e.getKeyCode();
		  	if (keyCode == KeyEvent.VK_LEFT) 
		  	{
		  		left = true;

		  	}
		  
		  	if (keyCode == KeyEvent.VK_RIGHT)
		  	{
		  		right = true;

		  	}
	  }
//-----------------------------------------------------------------------------------------------------------
	  public void keyReleased(KeyEvent e) 
	  {
		  	int keyCode = e.getKeyCode();
		  	if (keyCode == KeyEvent.VK_LEFT) 
		  	{
		  		left = false;
		  	}
		  	if (keyCode == KeyEvent.VK_RIGHT) 
		  	{
		  		right = false;
		  	}
	  }
//-----------------------------------------------------------------------------------------------------------
	  
	  public void actionPerformed(ActionEvent e) 
	  {
		  String str = e.getActionCommand();
		  if (str.equals("restart")) {
			  this.restart();
		  }
	  }
	//----------------------------------------------------------------
	  public void restart() 
	  {
		  requestFocus(true);
		  initializeVariables();
		  createBricks();
		  repaint();
	  }
	  
	  public void initializeVariables()
	  {
		  ballx = 160;
		  bally = 218;
		  batx = 160;
		  baty = 245;
		  brickx = 70;
		  bricky = 50;
		  Ball = new Rectangle(ballx, bally, 5, 5);
		  Bat = new Rectangle(batx, baty, 40, 5);
		  Brick = new Rectangle[12];
		  movex = -1;
		  movey = -1;
		  ballFallDown = false;
		  bricksOver = false;
		  count = 0;
		  status = null;
      }
	//----------------------------------------------------------------
	  public void createBricks()
	  {
		  for (int i=0; i <Brick.length; i++) 
		  {
			  Brick[i] = new Rectangle(brickx, bricky, brickBreadth, brickHeight);
			  
			  if (i == 5) 
			  {
				  brickx = 70;
				  bricky = (bricky + brickHeight + 2);
			  }
			  
			  if (i == 9)
			  {
				  brickx = 100;
				  bricky = (bricky + brickHeight + 2);
			  }
			  brickx += (brickBreadth+1);
		  }
	 }
//----------------------------------------------------------------------------------------------------------
 	public void keyTyped(KeyEvent arg0) {}
//-------------------------------------------------------------------------------------------------------------

	 public static void main(String[] args)   // MAIN METHOD.
	 {
			JFrame frame = new JFrame();             // CREATING OBJECT FOR "JFRAME".
			BRICK_BREAKER game = new BRICK_BREAKER();                // CREATING OBJECT FOR "MAIN CLASS".
			JButton button = new JButton("restart"); // CREATING OBJECT FOR "BUTTON(RESTART)".

			frame.add(game);
			frame.setSize(550, 450);
			frame.add(button, BorderLayout.SOUTH);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			frame.setTitle("Brick Breaker");
			button.addActionListener(game);
			game.addKeyListener(game);
			game.setFocusable(true);
			Thread t = new Thread(game);
			t.start();
	 }
}
