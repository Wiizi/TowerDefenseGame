package game;


import java.awt.Font;
import java.util.LinkedList;
import java.util.Queue;


import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Grid.*;
import Map.*;
import critters.Critter;
import critters.Critter.direction;
import critters.CritterGenerator;
import critters.CritterObserver;
import critters.ScoutCritter;

public class PlayScreen extends BasicGameState {


	private static Queue<Critter> critterList = new LinkedList<Critter>();
	private static Queue<Critter> activeCritterList = new LinkedList<Critter>();
	static long tickCount = 0;
	private SpriteSheet blackBeetleSpriteSheet;
	Animation blackBeetleAnimation;
	private SpriteSheet batSpriteSheet;
	Animation batAnimation;
	Image SandTile;
	Image GravelTile;
	Image BrickTile;
	Image BuyTowerTitle;
	Image TowerMenuOverlay;
	Image ExitButtonGraphic;
	Image TileSelectGraphic;
	Image CurrencyGraphic;
	Image WaveGraphic;
	Image NextWaveActiveGraphic;
	Image NextWaveNonActiveGraphic;
	Image HeartGraphic;
	Rectangle ExitButton;
	Rectangle NextWaveButton;
	//TODO add rectangle buttons for towers and sell tower

	private static Map currentMap;
	private final int sideMenuWidth = 192;
	private final int bottomMenuWidth = 128;
	private final int startingLevel = 1;
	private final int startingCredits = 0;
	private final int startingLives = 16;
	private final int critterSpawnDelay = 20;
	CritterObserver gruntObserver;
	CritterGenerator generator;
	private static int currentLevel;
	private static int playerCredits;
	private static int playerLives;
	private static boolean waveIsInProgress;

	Font font ;
	TrueTypeFont ttf;

	public PlayScreen (int state){
		
	}


	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {

		loadImages();
		loadAnimations();
		loadFonts();

		currentLevel = startingLevel;
		playerCredits = startingCredits;
		playerLives = startingLives;
		waveIsInProgress = false;
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

		if(waveIsInProgress){
			if(critterList.size()!=0){
				tickCount++;
				if(tickCount>critterSpawnDelay){
					activeCritterList.add(critterList.poll());
					tickCount=0;	
				}
			}
			boolean crittersAreStillVisible= false;
			//for each critter list, update their movement if they are alive
			for(Critter s : activeCritterList){
				//only living critters can move!
				if(s.isAlive())
					s.move();
				if(s.isVisible())
					crittersAreStillVisible=true;
			}

			if(!crittersAreStillVisible){
				waveIsInProgress = false;
				currentLevel++;
			}
		}


		if(Mouse.isButtonDown(0)){
			MouseClicked(Mouse.getX(), container.getHeight() - Mouse.getY(), sbg);
		}

		blackBeetleAnimation.update(delta);
		batAnimation.update(delta);

	}





	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {

		drawMapandOverlay(container, g);
		if(waveIsInProgress)
			drawCritters();

		
	}




	public void drawCritters(){
		for(Critter s : activeCritterList)
			//this method draws critters depending on if they are alive or not
			if(s.isVisible())
			{
				//which critter
				drawCritter(s);
			/*
				if(!(s instanceof ScoutCritter)){
					drawGrunt(s);
				}

				if(s instanceof ScoutCritter){
					drawScout(s);
				}
				*/
			}
			
	}

	public void drawGrunt(Critter s){
		if( s.isAlive() ){
			if(s.getCritterDirection()==direction.RIGHT){
				blackBeetleAnimation.getCurrentFrame().setRotation(90*(3));
				blackBeetleAnimation.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
			}
			if(s.getCritterDirection()==direction.LEFT){
				blackBeetleAnimation.getCurrentFrame().setRotation(90*(1));
				blackBeetleAnimation.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());

			}
			if(s.getCritterDirection()==direction.DOWN){
				blackBeetleAnimation.getCurrentFrame().setRotation(90*(0));
				blackBeetleAnimation.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
			}
			if(s.getCritterDirection()==direction.UP){
				blackBeetleAnimation.getCurrentFrame().setRotation(90*(2));
				blackBeetleAnimation.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
			}
		}

	}
	
	public void drawCritter(Critter s){
		Animation a;
		switch(s.getType()){
			case GRUNT:
				a = blackBeetleAnimation;
				break;
			case SCOUT:
				a = batAnimation;
				break;
			case ARMORED:
				a = null;
				break;
			case TANK:
				a = null;
				break;
			case BOSS:
				a = null;
				break;
			default:
				a= blackBeetleAnimation;
				break;
			
		
		}
		
		if(s.getCritterDirection()==direction.RIGHT){
			a.getCurrentFrame().setRotation(90*(1));
			a.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		if(s.getCritterDirection()==direction.LEFT){
			a.getCurrentFrame().setRotation(90*(3));
			a.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		if(s.getCritterDirection()==direction.DOWN){
			a.getCurrentFrame().setRotation(90*(2));
			a.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		if(s.getCritterDirection()==direction.UP){
			a.getCurrentFrame().setRotation(90*(0));
			a.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		
	}

	public void drawScout(Critter s){
		if(s.getCritterDirection()==direction.RIGHT){
			batAnimation.getCurrentFrame().setRotation(90*(1));
			batAnimation.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		if(s.getCritterDirection()==direction.LEFT){
			batAnimation.getCurrentFrame().setRotation(90*(3));
			batAnimation.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		if(s.getCritterDirection()==direction.DOWN){
			batAnimation.getCurrentFrame().setRotation(90*(2));
			batAnimation.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		if(s.getCritterDirection()==direction.UP){
			batAnimation.getCurrentFrame().setRotation(90*(0));
			batAnimation.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
	}

	public void drawMapandOverlay(GameContainer container, Graphics g){
		//draw map and background
		for(int i = 0 ; i < container.getWidth()/32 ; i++){
			for(int j = 0 ; j < container.getHeight()/32 ; j++){
				if(i<currentMap.getWidthOfMap() &&j < currentMap.getHeightOfMap()){

					if (currentMap.getTile(i, j) instanceof PathTile){	
						GravelTile.draw(i * currentMap.getPixelSize(), j * currentMap.getPixelSize());
						continue;
					}
					if (currentMap.getTile(i, j) instanceof MapTile){		
						SandTile.draw(i * currentMap.getPixelSize(), j * currentMap.getPixelSize());
						continue;
					}

				}
				BrickTile.draw(i * currentMap.getPixelSize(), j * currentMap.getPixelSize());
			}
		}

		//draw the hearts
		for(int x = 0 ; x < playerLives ; x++){
			if(x<8)
				HeartGraphic.draw(x * (5 + HeartGraphic.getWidth()), currentMap.getHeightOfMap() * currentMap.getPixelSize() + 5);
			else{
				HeartGraphic.draw((x - 8) * (5 + HeartGraphic.getWidth()), currentMap.getHeightOfMap() * currentMap.getPixelSize() + 15 + HeartGraphic.getHeight());
			}
		}


		//drawing buttons and overlays
		TowerMenuOverlay.draw(currentMap.getWidthInPixel(), 0);
		ExitButtonGraphic.draw(container.getWidth() - ExitButtonGraphic.getWidth(), container.getHeight() - ExitButtonGraphic.getHeight() - 2);
		CurrencyGraphic.draw(1, container.getHeight() - CurrencyGraphic.getHeight());
		WaveGraphic.draw(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel());
		if(!waveIsInProgress)
			NextWaveActiveGraphic.draw(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel() + WaveGraphic.getHeight() + 10);
		else
			NextWaveNonActiveGraphic.draw(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel() + WaveGraphic.getHeight() + 10);

		// drawing/updating the currency and level
		ttf.drawString( CurrencyGraphic.getWidth() + 5, (container.getHeight() - 40), "" + playerCredits);
		ttf.drawString(currentMap.getWidthInPixel() - 48, currentMap.getHeightInPixel() + 15, currentLevel + "");

		//if the mouse is on the map, snap to map grid
		if(Mouse.getX()<(currentMap.getWidthInPixel()) && Mouse.getY()>(container.getHeight() - currentMap.getHeightInPixel() ))
			TileSelectGraphic.drawCentered(getClosestTileCenter(Mouse.getX()), container.getHeight() - getClosestTileCenter(Mouse.getY()));
	}


	public void loadImages() throws SlickException{
		//initialize all graphics/images from graphics folder
		SandTile = new Image("graphics/SandTile.png");
		GravelTile = new Image ("graphics/GravelTile.png");
		BrickTile = new Image ("graphics/BrickTile.png");
		ExitButtonGraphic = new Image ("graphics/ExitButton.png");
		CurrencyGraphic = new Image("graphics/CurrencyGraphic.png");
		TileSelectGraphic = new Image ("graphics/TileSelectGraphic.png");
		WaveGraphic = new Image ("graphics/WaveGraphic.png");
		NextWaveActiveGraphic = new Image("graphics/NextWaveActive.png");
		NextWaveNonActiveGraphic = new Image("graphics/NextWaveNonActive.png");
		HeartGraphic = new Image("graphics/Heart.png");
		TowerMenuOverlay = new Image("graphics/TowerMenuGraphic.png");



	}

	public void loadAnimations() throws SlickException{
		//create sprite sheets and load them into the animation objects
		batSpriteSheet = new SpriteSheet("graphics/batAnimationSheet.png",29,29,0);
		batAnimation = new Animation(batSpriteSheet,150);
		blackBeetleSpriteSheet = new SpriteSheet("graphics/beetleDownSheet.png", 28, 29,0);
		blackBeetleAnimation = new Animation(blackBeetleSpriteSheet, 100);
	}

	public void loadFonts(){
		//create a new font for the credit and level display
		font = new Font("Verdana", Font.PLAIN, 26);
		ttf = new TrueTypeFont(font, true);

	}

	public void createRectangleButtons(GameContainer container){
		//create the nextwave and exit rectangle buttons
		ExitButton = new Rectangle(container.getWidth() - ExitButtonGraphic.getWidth(), container.getHeight() - ExitButtonGraphic.getHeight() - 2, ExitButtonGraphic.getWidth(), ExitButtonGraphic.getHeight());
		NextWaveButton = new Rectangle(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel() + WaveGraphic.getHeight() + 10, NextWaveActiveGraphic.getWidth(), NextWaveActiveGraphic.getHeight());
	}


	public void setMap(Map pMap){
		currentMap = pMap;
	}

	public float getClosestTileCenter(float X){

		return (float) (Math.floor(X / currentMap.getPixelSize() * currentMap.getPixelSize()) + currentMap.getPixelSize() / 2);
	}

	public void createLevelCritterQueue(){
		int[][] locations = currentMap.getCornersList();


		generator = new CritterGenerator(locations,currentLevel);
		generator.createCritterQueue();
		generator.RandomizeCritterQueue();
		critterList = generator.getCritterQueue();
		activeCritterList = new LinkedList<Critter>();
		activeCritterList.add(critterList.poll());
	}


	private void MouseClicked(int x, int y, StateBasedGame sbg) {
		if(ExitButton.contains(x,y)){
			currentLevel = startingLevel;
			playerCredits = startingCredits;
			playerLives = startingLives;
			waveIsInProgress = false;
			sbg.enterState(0);
		}

		if(NextWaveButton.contains(x,y)&& !waveIsInProgress){
			waveIsInProgress = true;
			createLevelCritterQueue();
		}

	}


	@Override
	public int getID() {
		return Game.playScreen;
	}

}
