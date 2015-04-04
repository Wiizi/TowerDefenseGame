package game;

public class Player {
	private static int credits = 0;
	private static int lives = 16;
	private static final int STARTINGCREDITS = 500;
	private static final int STARTINGLIVES = 16;
	
	public static void reset(){
		lives = STARTINGLIVES;
		credits = STARTINGCREDITS;
	}
	
	public static void addCredits(int amount){
		credits += amount;
	}
	
	public static void addLife(){
		lives++;
	}
	public static void decreaseLife(){
		lives--;
	}
	
	public static int getLives(){
		return lives;
	}
	
	public static int getCredits(){
		return credits;
	}
}
