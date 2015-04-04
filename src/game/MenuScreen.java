package game;



import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import critters.Critter;
public class MenuScreen extends BasicGameState{

	Image SandTileGraphic;
	Image StartGameButtonGraphic;
	Image EditMapButtonGraphic;
	Image TowerDefenseTitleGraphic;
	Image ExitButtonGraphic;
	private final String authors = "A Java Game by Callum May, Wei Wang,\nCharles Liu and Robert Zhao";
	
	
	public MenuScreen (int state){
		
	}
	
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {

		SandTileGraphic = new Image("graphics/SandTile.png");
		StartGameButtonGraphic = new Image("graphics/StartGameButton.png");
		EditMapButtonGraphic = new Image("graphics/EditMapButton.png");
		TowerDefenseTitleGraphic = new Image("graphics/TowerDefenseTitle.png");
		ExitButtonGraphic = new Image ("graphics/ExitButton.png");
		
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

		//check start game button
		if( Mouse.getX() > container.getWidth()/2 -StartGameButtonGraphic.getWidth()/2 && Mouse.getX() < container.getWidth()/2 +StartGameButtonGraphic.getWidth()/2)
		{

			if( Mouse.getY() > 2*container.getHeight()/3 -StartGameButtonGraphic.getHeight()/2 && Mouse.getY() < 2*container.getHeight()/3 +StartGameButtonGraphic.getHeight()/2 )
			{
	
				if(Mouse.isButtonDown(0)){
					sbg.enterState(Game.mapSelectScreen);
				}
			}
		}
		//check edit map button
		if( Mouse.getX() > container.getWidth()/2 -EditMapButtonGraphic.getWidth()/2 && Mouse.getX() < container.getWidth()/2 +EditMapButtonGraphic.getWidth()/2)
		{

			if( Mouse.getY() > container.getHeight() -(2*container.getHeight()/4 +EditMapButtonGraphic.getHeight()/2) && Mouse.getY() < container.getHeight() -(2*container.getHeight()/4 -EditMapButtonGraphic.getHeight()/2) )
			{
	
				if(Mouse.isButtonDown(0)){
					sbg.enterState(Game.editMapScreen);
				}
			}
		}
		
		//check exit button
		if( (Mouse.getX() > container.getWidth() -ExitButtonGraphic.getWidth()) &&( Mouse.getX() < container.getWidth()))
		{

			if( Mouse.getY() > 0 && Mouse.getY() <ExitButtonGraphic.getHeight()  )
			{
				if(Mouse.isButtonDown(0)){
					System.out.println("Exiting");
					System.exit(0);
					
				}
			}
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		
		drawMapAndOverlay(container);
		g.setColor(Color.black);
		g.drawString(authors, 5, container.getHeight()-40);
	}
	
	
	public void drawMapAndOverlay(GameContainer container){
		for(int x = 0; x <container.getWidth(); x+=SandTileGraphic.getWidth()){
			for(int y = 0 ; y< container.getHeight(); y+=SandTileGraphic.getHeight()){
				SandTileGraphic.draw(x,y);
			}
		}

		StartGameButtonGraphic.draw(container.getWidth()/2 -StartGameButtonGraphic.getWidth()/2, container.getHeight()/3 -StartGameButtonGraphic.getHeight()/2);
		EditMapButtonGraphic.draw(container.getWidth()/2 -EditMapButtonGraphic.getWidth()/2, container.getHeight()/2 -EditMapButtonGraphic.getHeight()/2);
		TowerDefenseTitleGraphic.draw(container.getWidth()/2 - TowerDefenseTitleGraphic.getWidth()/2, TowerDefenseTitleGraphic.getHeight()/2);
		ExitButtonGraphic.draw(container.getWidth()-ExitButtonGraphic.getWidth(), container.getHeight()-ExitButtonGraphic.getHeight()-2);
		
	}

	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
