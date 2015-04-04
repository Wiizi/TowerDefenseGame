package game;


import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;









import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import towers.Projectile;
import towers.Tower;
import grid.*;
import map.*;
import critters.Critter;
import critters.Critter.direction;
import critters.CritterGenerator;
import critters.CritterObserver;
import critters.ScoutCritter;

public class PlayScreen extends BasicGameState {


	private static Queue<Critter> critterList = new LinkedList<Critter>();
	private static Queue<Critter> activeCritterList = new LinkedList<Critter>();
	private static ArrayList<Tower> towerList = new ArrayList<Tower>();
	private static ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
	static long tickCount = 0;
	
	private final int mouseClickDelay = 200;
	private long lastClick=(-1*mouseClickDelay);
	
	private SpriteSheet blackBeetleSpriteSheet;
	Animation blackBeetleAnimation;
	private SpriteSheet batSpriteSheet;
	Animation batAnimation;
	Image SandTileGraphic;
	Image GravelTileGraphic;
	Image BrickTileGraphic;
	Image BuyTowerTitleGraphic;
	Image TowerMenuOverlayGraphic;
	Image ExitButtonGraphic;
	Image TileSelectGraphic;
	Image CurrencyGraphic;
	Image WaveGraphic;
	Image NextWaveActiveGraphic; 
	Image NextWaveNonActiveGraphic;
	Image HeartGraphic;
	Image TowerTileGraphic;
	Image TowerGraphic;
	Image ProjectileGraphic;
	Rectangle ExitButton;
	Rectangle NextWaveButton;
	//TODO add rectangle buttons for towers and sell tower

	private static Map currentMap;
	private final int sideMenuWidth = 192;
	private final int bottomMenuWidth = 128;
	
	private final int towerGraphicYStart = 58;
	private final int towerGraphicXStart = 20;
	private final int towerGraphicYOffset = 78;
	private final int towerGraphicXOffset = 83;
	
	//which tower player is placing, corresponds to position in towerList. -1 = no tower selected
	private static int selectedTower =-1;
	
	private ArrayList<Image> TowerGraphics;
	private ArrayList<Rectangle> TowerGraphicButtons;
	
	private final int startingLevel = 1;
	private final int critterSpawnDelay = 20;
	CritterObserver gruntObserver;
	CritterGenerator generator;
	private static int currentLevel;
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
		Player.reset();
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
			attackCritters();
			//for each projectile update their locations
			ArrayList<Projectile> pToRemove = new ArrayList<Projectile>();
			for(Projectile p: projectileList){
				if(!p.hasArrived())
					p.move();
				else
					pToRemove.add(p);
			}
			
			for(Projectile p: pToRemove){
				projectileList.remove(p);
			}
			
			if(!crittersAreStillVisible){
				waveIsInProgress = false;
				currentLevel++;
			}
		}


		if(Mouse.isButtonDown(0)){
			MouseClicked(Mouse.getX(), container.getHeight() - Mouse.getY(), sbg, container);
		}

		blackBeetleAnimation.update(delta);
		batAnimation.update(delta);

	}



	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {

		drawMapandOverlay(container, g);
		drawTowers();

		if(waveIsInProgress){
			drawCritters(); 
			drawProjectiles();
		}		
	}


	private void drawTowers(){
		for(Tower t: towerList){
			TowerGraphic.drawCentered(t.getX(), t.getY());
		}
	}
	private void drawProjectiles(){
		for(Projectile p: projectileList){
			ProjectileGraphic.drawCentered((float)p.getX(),(float)p.getY());
		}
	}
	public void drawCritters(){
		for(Critter s : activeCritterList)
			//this method draws critters depending on if they are alive or not
			if(s.isVisible()&&s.isAlive())
			{
				drawCritter(s);

			}
			
	}

	
	public void drawCritter(Critter s){
		Animation a;
		int orientationOffset = 0;
		switch(s.getType()){
			case GRUNT:
				a = blackBeetleAnimation;
				orientationOffset =0;
				break;
			case SCOUT:
				a = batAnimation;
				orientationOffset = 2;
				break;
			case ARMORED:
				a = blackBeetleAnimation;
				break;
			case TANK:
				a = blackBeetleAnimation;
				break;
			case BOSS:
				a = blackBeetleAnimation;
				break;
			default:
				a= blackBeetleAnimation;
				break;
			
		
		}
		
		if(s.getCritterDirection()==direction.RIGHT){
			a.getCurrentFrame().setRotation(90*((3+orientationOffset)%4));
			a.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		if(s.getCritterDirection()==direction.LEFT){
			a.getCurrentFrame().setRotation(90*((1+orientationOffset)%4));
			a.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());

		}
		if(s.getCritterDirection()==direction.DOWN){
			a.getCurrentFrame().setRotation(90*((0+orientationOffset)%4));
			a.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		
		if(s.getCritterDirection()==direction.UP){
			a.getCurrentFrame().setRotation(90*((2+orientationOffset)%4));
			a.getCurrentFrame().drawCentered(s.getXLoc(), s.getYLoc());
		}
		
	}


	public void drawMapandOverlay(GameContainer container, Graphics g){
		//draw map and background
		for(int i = 0 ; i < container.getWidth()/32 ; i++){
			for(int j = 0 ; j < container.getHeight()/32 ; j++){
				if(i<currentMap.getWidthOfMap() &&j < currentMap.getHeightOfMap()){

					if (currentMap.getTile(i, j) instanceof PathTile){	
						GravelTileGraphic.draw(i * currentMap.getPixelSize(), j * currentMap.getPixelSize());
						continue;
					}
					if (currentMap.getTile(i, j) instanceof MapTile){		
						SandTileGraphic.draw(i * currentMap.getPixelSize(), j * currentMap.getPixelSize());
						continue;
					}

				}
				BrickTileGraphic.draw(i * currentMap.getPixelSize(), j * currentMap.getPixelSize());
			}
		}

		//draw the hearts
		for(int x = 0 ; x < Player.getLives() ; x++){
			if(x<8)
				HeartGraphic.draw(x * (5 + HeartGraphic.getWidth()), currentMap.getHeightOfMap() * currentMap.getPixelSize() + 5);
			else{
				HeartGraphic.draw((x - 8) * (5 + HeartGraphic.getWidth()), currentMap.getHeightOfMap() * currentMap.getPixelSize() + 15 + HeartGraphic.getHeight());
			}
		}


		//drawing buttons and overlays
		TowerMenuOverlayGraphic.draw(currentMap.getWidthInPixel(), 0);
		ExitButtonGraphic.draw(container.getWidth() - ExitButtonGraphic.getWidth(), container.getHeight() - ExitButtonGraphic.getHeight() - 2);
		CurrencyGraphic.draw(1, container.getHeight() - CurrencyGraphic.getHeight());
		WaveGraphic.draw(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel());
		if(!waveIsInProgress)
			NextWaveActiveGraphic.draw(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel() + WaveGraphic.getHeight() + 10);
		else
			NextWaveNonActiveGraphic.draw(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel() + WaveGraphic.getHeight() + 10);

		
		//draw tower graphics
		for (int i =0;i<6;i++){
			int xCorner = currentMap.getWidthInPixel() +towerGraphicXStart + ((i)%2)*towerGraphicXOffset;
			int yCorner = towerGraphicYStart + (i/2)*towerGraphicYOffset;
			TowerGraphics.get(i).draw(xCorner,yCorner);
			
		}
		// drawing/updating the currency and level
		ttf.drawString( CurrencyGraphic.getWidth() + 5, (container.getHeight() - 40), "" + Player.getCredits());
		ttf.drawString(currentMap.getWidthInPixel() - 48, currentMap.getHeightInPixel() + 15, currentLevel + "");

		//if the mouse is on the map, snap to map grid
		if(mouseOnMap(Mouse.getX(),container.getHeight()-Mouse.getY())){
			if(selectedTower<0)
				TileSelectGraphic.drawCentered(getClosestTileCenter(Mouse.getX()), container.getHeight() - getClosestTileCenter(Mouse.getY()));
			else
				TowerGraphic.drawCentered(getClosestTileCenter(Mouse.getX()), container.getHeight() - getClosestTileCenter(Mouse.getY()));
		}
	}

	public boolean mouseOnMap(int x, int y){
		if(x<(currentMap.getWidthInPixel())&& y< currentMap.getHeightInPixel()){
			return true;
		}
		else
			return false;
	}

	//for every tower in the towerlist, determine if it should attack critter
	private void attackCritters(){
		for(Tower t: towerList){
			if(t.getTimeOfLastAttack() + t.getRateofFire() < System.currentTimeMillis()){
				for(Critter c: activeCritterList){
					if(c.isAlive()&&c.isVisible()){
						//calculate distance
						int xDist= Math.abs((int)c.getXLoc() - t.getX());
						int yDist= Math.abs((int)c.getYLoc() -  t.getY());
						double dist = Math.sqrt((xDist*xDist)+(yDist*yDist));
						if(dist<t.getRange()){
							towerAttack(c,t);
							t.setTimeOfLastAttack(System.currentTimeMillis());
							break;
						}
					}
				}
			}
		}
	}

	
	public void loadImages() throws SlickException{
		//initialize all graphics/images from graphics folder
		SandTileGraphic = new Image("graphics/SandTile.png");
		GravelTileGraphic = new Image ("graphics/GravelTile.png");
		BrickTileGraphic = new Image ("graphics/BrickTile.png");
		ExitButtonGraphic = new Image ("graphics/ExitButton.png");
		CurrencyGraphic = new Image("graphics/CurrencyGraphic.png");
		TileSelectGraphic = new Image ("graphics/TileSelectGraphic.png");
		WaveGraphic = new Image ("graphics/WaveGraphic.png");
		NextWaveActiveGraphic = new Image("graphics/NextWaveActive.png");
		NextWaveNonActiveGraphic = new Image("graphics/NextWaveNonActive.png");
		HeartGraphic = new Image("graphics/Heart.png");
		TowerMenuOverlayGraphic = new Image("graphics/TowerMenuGraphic.png");
		
		TowerGraphic = new Image("graphics/BasicTowerGraphic.png");
		TowerGraphics = new ArrayList<Image>();
		for(int i =0;i<6;i++){
			TowerGraphics.add(new Image("graphics/BasicTowerGraphic.png"));
		}
		ProjectileGraphic = new Image("graphics/Projectile.png");



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
	
		//create tower buttons
		TowerGraphicButtons = new ArrayList<Rectangle>();
		for(int i =0;i<6;i++){
			
			int xCorner = currentMap.getWidthInPixel() +towerGraphicXStart + ((i)%2)*towerGraphicXOffset;
			int yCorner = towerGraphicYStart + (i/2)*towerGraphicYOffset;
			TowerGraphicButtons.add(new Rectangle(xCorner, yCorner, TowerGraphics.get(i).getHeight(), TowerGraphics.get(i).getWidth()));
			
		}
	}


	public void setMap(Map pMap){
		currentMap = pMap;
	}

	public float getClosestTileCenter(float X){

		return (float) (Math.floor(X / currentMap.getPixelSize()) * currentMap.getPixelSize() + currentMap.getPixelSize() / 2);
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


	private void towerAttack(Critter target, Tower source){
		Projectile attack = new Projectile((double)source.getX(),(double) source.getY(), 
				(double)target.getXLoc(),(double) target.getYLoc(), source.getPower(), source.isFreezeTower());
		
		projectileList.add(attack);
		double xDist = source.getX() - target.getXLoc();
		double yDist = source.getY() - target.getYLoc();
		long dist = (long)Math.sqrt(xDist*xDist + yDist*yDist);
		//convert to ms
		long delay = dist/attack.getSpeed()*1000;
		target.hitCritter(source.getPower(), delay);
	}
	
	
	private void MouseClicked(int x, int y, StateBasedGame sbg, GameContainer container) throws SlickException {
		if(lastClick + mouseClickDelay > System.currentTimeMillis())
			return;
		lastClick = System.currentTimeMillis();
		
		if(ExitButton.contains(x,y)){
			currentLevel = startingLevel;
			Player.reset();
			waveIsInProgress = false;
			AppGameContainer gameContainer = (AppGameContainer) container;
			gameContainer.setDisplayMode(640, 480, false);
			Mouse.getDX();
			sbg.enterState(Game.menuScreen);
		}
		
		//no towers selected
		if (selectedTower < 0){
			
			if(NextWaveButton.contains(x,y)&& !waveIsInProgress){
				waveIsInProgress = true;
				createLevelCritterQueue();
			}
			
			for(int i=0;i<6;i++){
				if(TowerGraphicButtons.get(i).contains(x,y)){
					selectedTower = i;
					
				}
			}
		}
		//tower selected
		else {
			if(mouseOnMap(x,y)){
				Tower newTower = new Tower((int)getClosestTileCenter(x),(int)getClosestTileCenter(y));
				towerList.add(newTower);
				Player.addCredits((-1)*newTower.getBuyingCost());
				
				if (Player.getCredits()<0){
					//deny tower building due to insufficient funds
					Player.addCredits(newTower.getBuyingCost());
					towerList.remove(newTower);
				}
			}
			selectedTower=-1;
		}
	}


	@Override
	public int getID() {
		return Game.playScreen;
	}

}
