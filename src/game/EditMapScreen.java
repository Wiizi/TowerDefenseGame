package game;


import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class EditMapScreen extends BasicGameState {
	//test test
	Image SandTile;
	Image ExitButtonGraphic;
	Image CreateMapButtonGraphic;
	Rectangle ExitGameButton;
	Rectangle CreateMapButton;
	ArrayList<Rectangle> buttonList = new ArrayList<Rectangle>();

	TextField mapWidthTextField;
	TextField mapHeightTextField;

	Font font ;
	TrueTypeFont ttf;

	public final String WidthString = "Enter Map Width:";
	public final String HeightString = "Enter Map Height:";
	public static String statusString= "";
	
	public final int minimumMapDimension = 12;
	public final int maximumMapDimension = 20;

	public EditMapScreen (int state){

	}

	@Override
	public void init(GameContainer container, StateBasedGame arg1) throws SlickException {
		loadImages();


		font = new Font("Verdana", Font.PLAIN, 12);
		ttf = new TrueTypeFont(font, true);

		mapWidthTextField = new TextField(container, ttf , 40,60,60,20);
		mapHeightTextField = new TextField(container, ttf, 40 +ttf.getWidth(WidthString)+10, 60, 60, 20);
		CreateMapButton = new Rectangle(mapHeightTextField.getX() + ttf.getWidth(WidthString)+10, 40 + mapWidthTextField.getHeight()/2 ,CreateMapButtonGraphic.getWidth(), CreateMapButtonGraphic.getHeight());
		ExitGameButton = new Rectangle(container.getWidth()-ExitButtonGraphic.getWidth(), container.getHeight()-ExitButtonGraphic.getHeight()-2, ExitButtonGraphic.getWidth(),ExitButtonGraphic.getHeight());

	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta)
			throws SlickException {
		if(Mouse.isButtonDown(0)){
			mouseClicked(Mouse.getX(), container.getHeight() - Mouse.getY(), sbg, container);
		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		drawMapAndOverlay(container);

		ttf.drawString(40, 40, WidthString, Color.black);
		ttf.drawString(40 +ttf.getWidth(WidthString)+10, 40, HeightString, Color.black);
		ttf.drawString(mapWidthTextField.getX(), mapWidthTextField.getY()+mapWidthTextField.getHeight()+10, statusString, Color.black);
		CreateMapButtonGraphic.draw(mapHeightTextField.getX() + ttf.getWidth(WidthString)+10, 40 + mapWidthTextField.getHeight()/2 );
		g.setColor(Color.white);

		mapWidthTextField.render(container, g);
		mapHeightTextField.render(container, g);
	}





	public void drawMapAndOverlay(GameContainer container){
		for(int x = 0; x <container.getWidth(); x+=SandTile.getWidth()){
			for(int y = 0 ; y< container.getHeight(); y+=SandTile.getHeight()){
				SandTile.draw(x,y);
			}
		}

		ExitButtonGraphic.draw(container.getWidth()-ExitButtonGraphic.getWidth(), container.getHeight()-ExitButtonGraphic.getHeight()-2);


	}

	public void loadImages() throws SlickException{
		SandTile = new Image("graphics/SandTile.png");
		ExitButtonGraphic = new Image ("graphics/ExitButton.png");
		CreateMapButtonGraphic = new Image("graphics/CreateMapButtonGraphic.png");
	}

	public void mouseClicked(int x, int y, StateBasedGame sbg, GameContainer container){
		int mapWidth=0;
		int mapHeight=0;
		if(ExitGameButton.contains(x, y)){
			Mouse.getDY();
			sbg.enterState(Game.menuScreen);
		}

		if(CreateMapButton.contains(x, y)){
			statusString ="";
			try{
				mapWidth = Integer.parseInt(mapWidthTextField.getText());
				mapHeight = Integer.parseInt(mapHeightTextField.getText());
			}
			catch(NumberFormatException e){
				statusString = "Illegal input format";
			}
			if(mapWidth <minimumMapDimension || mapHeight <minimumMapDimension)
			{
				statusString = "Map to small. Minimum dimensions are "+minimumMapDimension+"x"+minimumMapDimension;
			}
			else if (mapWidth > maximumMapDimension || mapHeight > maximumMapDimension){
				statusString = "Map to large. Maximum dimensions are "+maximumMapDimension+"x"+maximumMapDimension;
			}
			else{
				generateMapGrid(mapWidth, mapHeight, container);
			}
			
		}

	}


	public void generateMapGrid(int mapWidth, int mapHeight, GameContainer container){
		int currentX = 40;
		int currentY = 100;
		for(int x = 0 ; x < mapWidth; x++ ){
			for(int y = 0 ; y < mapHeight; y++){
				
			}
			
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
