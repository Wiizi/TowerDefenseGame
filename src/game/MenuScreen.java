package game;



import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import critters.Critter;
public class MenuScreen extends BasicGameState{

	Image SandTile;
	Image StartGameButton;
	Image EditMapButton;
	Image TowerDefenseTitle;
	Image ExitButton;
	private final String authors = "A Java Game by Callum May, Wei Wang,\nCharles Liu and Robert Zhao";
	
	
	public MenuScreen (int state){
		
	}
	
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {

		SandTile = new Image("graphics/SandTile.png");
		StartGameButton = new Image("graphics/StartGameButton.png");
		EditMapButton = new Image("graphics/EditMapButton.png");
		TowerDefenseTitle = new Image("graphics/TowerDefenseTitle.png");
		ExitButton = new Image ("graphics/ExitButton.png");
		
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

		//check start game button
		if( Mouse.getX() > container.getWidth()/2 -StartGameButton.getWidth()/2 && Mouse.getX() < container.getWidth()/2 +StartGameButton.getWidth()/2)
		{

			if( Mouse.getY() > 2*container.getHeight()/3 -StartGameButton.getHeight()/2 && Mouse.getY() < 2*container.getHeight()/3 +StartGameButton.getHeight()/2 )
			{
	
				if(Mouse.isButtonDown(0)){
					sbg.enterState(Game.mapSelectScreen);
				}
			}
		}
		//check edit map button
		if( Mouse.getX() > container.getWidth()/2 -EditMapButton.getWidth()/2 && Mouse.getX() < container.getWidth()/2 +EditMapButton.getWidth()/2)
		{

			if( Mouse.getY() > container.getHeight() -(2*container.getHeight()/4 +EditMapButton.getHeight()/2) && Mouse.getY() < container.getHeight() -(2*container.getHeight()/4 -EditMapButton.getHeight()/2) )
			{
	
				if(Mouse.isButtonDown(0)){
					sbg.enterState(Game.editMapScreen);
				}
			}
		}
		
		//check exit button
		if( (Mouse.getX() > container.getWidth() -ExitButton.getWidth()) &&( Mouse.getX() < container.getWidth()))
		{

			if( Mouse.getY() > 0 && Mouse.getY() <ExitButton.getHeight()  )
			{
				if(Mouse.isButtonDown(0)){
					System.exit(0);
					
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		for(int x = 0; x <container.getWidth(); x+=SandTile.getWidth()){
			for(int y = 0 ; y< container.getHeight(); y+=SandTile.getHeight()){
				SandTile.draw(x,y);
			}
		}

		StartGameButton.draw(container.getWidth()/2 -StartGameButton.getWidth()/2, container.getHeight()/3 -StartGameButton.getHeight()/2);
		EditMapButton.draw(container.getWidth()/2 -EditMapButton.getWidth()/2, container.getHeight()/2 -EditMapButton.getHeight()/2);
		TowerDefenseTitle.draw(container.getWidth()/2 - TowerDefenseTitle.getWidth()/2, TowerDefenseTitle.getHeight()/2);
		ExitButton.draw(container.getWidth()-ExitButton.getWidth(), container.getHeight()-ExitButton.getHeight()-2);
		
		g.setColor(Color.black);
		g.drawString(authors, 5, container.getHeight()-40);
	}

	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
