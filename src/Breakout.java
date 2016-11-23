/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

    @SuppressWarnings({ "unused", "serial" })
    public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 500;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 1000;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;

	/**скорость мяча*/	
	private double vx, vy;
	
/**генератор случайных чисел vx*/	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
/**Задержка анимации или рауза времени между движениями мяча */	
	private static final int DELAY = 10;	
	
	
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		
		for(int i=0; i < NTURNS; i++) {
			setUpGame();
			playGame();
		if(brickCounter == 0) {
			ball.setVisible(false);
			printWinner();
			break;
	 }
		if(brickCounter > 0) {
			removeAll();
	 }
	 }
		if(brickCounter > 0) {
			printGameOver();
	 }
   }
	
	private void setUpGame() {
		    drawBricks( getWidth()/2, BRICK_Y_OFFSET);
		    drawPaddle();
		    drawBall();
	}
	
	//добавление   кирпича
	private GRect brick;
	
	// все кирпичи, необходимые для игры
	private void drawBricks(double cx, double cy) {				
		
		/*нужно иметь несколько столбцов в каждой строке
		 *  нужно иметь два цикла, 
		 * Один цикл для строк и один для цикла для столбцов.
		 */ 
		
		for( int row = 0; row < NBRICK_ROWS; row++ ) {
			
		for (int column = 0; column < NBRICKS_PER_ROW; column++) {
				
				/* получить координаты х в качестве исходной ширина:
				 * старт начинается в центре, 
				 * вычитание половины кирпича (ширины) в строке,  
				 * вычитание половины отделений (ширины) между кирпичами в строке,
				 * подожение,где должен быть первый кирпич
				 * поэтому для отправной точки следующих кирпичей в колонке необходимо:
				 * добавить ширину кирпича
				 * добавить ширину разделения
				 */
				
			double	x = cx - (NBRICKS_PER_ROW*BRICK_WIDTH)/2 - ((NBRICKS_PER_ROW-1)*BRICK_SEP)/2 + column*BRICK_WIDTH + column*BRICK_SEP;
				
				/* Чтобы получить исходную координату по высоте:
				 * начинают с данной длины от вершины к первой строке,
				 * затем добавить высоту кирпича и кирпичное разделение для каждого из следующих строк.
				 */
				
		double	y = cy + row*BRICK_HEIGHT + row*BRICK_SEP;
 
			brick = new GRect( x , y , BRICK_WIDTH , BRICK_HEIGHT );
			add (brick);
			brick.setFilled(true);
				
				//Настройка цвета в зависимости от ряда кирпичей 
				
		if (row < 2) {
			brick.setColor(Color.RED);
	 }
		if (row == 2 || row == 3) {
			brick.setColor(Color.ORANGE);
	 }
		if (row == 4 || row == 5) {
			brick.setColor(Color.YELLOW);
	 }
		if (row == 6 || row == 7) {
			brick.setColor(Color.GREEN);
	 }
		if (row == 8 || row == 9) {
			brick.setColor(Color.CYAN);
	 }
    }
   }
 }
	
	//добавления  ракетки
	private GRect paddle;
	
	//настройка ракетки
	private void drawPaddle() {
		//начинает ракетка в середине экрана
		double x = getWidth()/2 - PADDLE_WIDTH/2; 
		//высота ракетки остается постоянной в течение игры		
		double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect (x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add (paddle);
		addMouseListeners();
	}
	
	//отслеживание ракетки мышкой
	public void mouseMoved(MouseEvent e) {
		/* Мышь отслеживает среднюю точку ракетки.
		 * If the middle point of the paddle is between half paddle width of the screen
		 * and half a paddle width before the end of the screen, 
		 * the x location of the paddle is set at where the mouse is minus half a paddle's width, 
		 * А высота остается той же самой
		 */
		if ((e.getX() < getWidth() - PADDLE_WIDTH/2) && (e.getX() > PADDLE_WIDTH/2)) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH/2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	 }
		
   }
	
	//добавляем мячик
	private GOval ball;
	
	
	//настройка мячика
	private void drawBall() {
		double x = getWidth()/2 - BALL_RADIUS;
		double y = getHeight()/2 - BALL_RADIUS;
		ball = new GOval(x, y, BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}
	
	private void playGame() {
		    waitForClick();
		    getBallSpeed();
	  while (true) {
		    moveBall();
	     if (ball.getY() >= getHeight()) {
		     break;
	}
	     if(brickCounter == 0) {
		    break;
		  
	 }
   }
 }
	
	private void getBallSpeed() {
		     vy = 5.0;
		     vx = rgen.nextDouble(1.0, 3.0);
		  if (rgen.nextBoolean(0.5)) {
			 vx = -vx; 
	 }
		
   }
	
	private void moveBall() {
		      ball.move(vx, vy);
		   //проверка стен
		   // получить VX и VY в точке, ближайшей к 0 
		  if ((ball.getX() - vx <= 0 && vx < 0 )|| (ball.getX() + vx >= (getWidth() - BALL_RADIUS*2) && vx>0)) {
			vx = -vx;
	 }
		    //Нам не нужно, чтобы проверить на нижней стенке, так как мяч может упасть через стену в этой точке
		  if ((ball.getY() - vy <= 0 && vy < 0 )) {
			vy = -vy;
	 }
		
		   //проверка для других объектов
	GObject collider = getCollidingObject();
		  if (collider == paddle) {
			/* Мы должны удостовериться, что мяч отскакивает от верхней части ракетки
			 * А также, что он не "прилипнет" к ней, если две стороны мяч попал весло и быстро получить мяч "застрял" на весла.			
             * Но меньше, чем высота, где мяч попадает в весла минус 4.
			 */
		  if(ball.getY() >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2 && ball.getY() < getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2 + 4) {
				vy = -vy;	
	 }
   }
		    //так как мы сложили ряд кирпичей, в прошлом кирпича в кирпичной стене присваивается значение кирпич.
		    //поэтому мы сузили ее, сказав, что колье не равна ракетки или нулю,
		    //так что все, что осталось, это кирпич
		   else if (collider != null) {
			     remove(collider); 
			     brickCounter--;
			     vy = -vy;
	 }
		         pause (DELAY);
   }
	
		private GObject getCollidingObject() {
		
		    if((getElementAt(ball.getX(), ball.getY())) != null) {
	         return getElementAt(ball.getX(), ball.getY());
	 }
		 else if (getElementAt( (ball.getX() + BALL_RADIUS*2), ball.getY()) != null ){
	         return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY());
	 }
		 else if(getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS*2)) != null ){
	         return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS*2);
	 }
		 else if(getElementAt((ball.getX() + BALL_RADIUS*2), (ball.getY() + BALL_RADIUS*2)) != null ){
	         return getElementAt(ball.getX() + BALL_RADIUS*2, ball.getY() + BALL_RADIUS*2);
	 }
		//необходимо вернуть значение NULL, если нет присутствующих объектов
		else{
	         return null;
	 }
		
   }
	
	private void printGameOver() {
		   GLabel gameOver = new GLabel ("Game Over", getWidth()/2, getHeight()/2);
		   gameOver.move(-gameOver.getWidth()/2, -gameOver.getHeight());
		   gameOver.setColor(Color.RED);
		     add (gameOver);
	}
	
	private int brickCounter = 100;
	
	private void printWinner() {
		    GLabel Winner = new GLabel ("Winner!!", getWidth()/2, getHeight()/2);
		    Winner.move(-Winner.getWidth()/2, -Winner.getHeight());
		    Winner.setColor(Color.RED);
		      add (Winner);
	}
}	