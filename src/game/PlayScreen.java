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

import towers.*;
import towers.Projectile.projectileType;
import grid.*;
import map.*;
import critters.Critter;
import critters.Critter.direction;
import critters.CritterGenerator;
import critters.CritterObserver;

public class PlayScreen extends BasicGameState {


	private static Queue<Critter> critterQueue = new LinkedList<Critter>();
	private static Queue<Critter> activeCritterQueue = new LinkedList<Critter>();
	private static ArrayList<Tower> towerList = new ArrayList<Tower>();
	private static ArrayList<Projectile> projectileList = new ArrayList<Projectile>();
	ArrayList<Projectile> pToRemove;
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
	Image UpgradeButtonGraphic;
	Image SellButtonGraphic;
	Image TileSelectGraphic;
	Image UpgradeSelectGraphic;
	Image SellSelectGraphic;
	Image CurrencyGraphic;
	Image WaveGraphic;
	Image NextWaveActiveGraphic; 
	Image NextWaveNonActiveGraphic;
	Image HeartGraphic;
	
	Image TowerTileGraphic;
	Image BasicTowerGraphic;
	Image BasicTowerProjectileGraphic;
	Image FreezeTowerGraphic;
	Image FreezeTowerProjectileGraphic;
	Image SniperTowerGraphic;
	Image SniperTowerProjectileGraphic;
	
	
	
	Rectangle ExitButton;
	Rectangle NextWaveButton;
	Rectangle SellButton;
	Rectangle UpgradeButton;


	private static Map currentMap;
	private final int sideMenuWidth = 192;
	private final int bottomMenuWidth = 128;

	private final int towerGraphicYStart = 58;
	private final int towerGraphicXStart = 20;
	private final int towerGraphicYOffset = 79;
	private final int towerGraphicXOffset = 83;
	private final int towerButtonWidth = 66;
	private final int towerButtonHeight = 66;
	private final int maximumNumberTowers = 3; //no more than 4

	//which tower player is placing, corresponds to position in towerList. -1 = no tower selected
	private static int selectedTower =-1;

	private ArrayList<Image> TowerGraphics;
	private ArrayList<Rectangle> TowerGraphicButtonsList;

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

		restartGame(container, sbg);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {

		if(waveIsInProgress){
			if(critterQueue.size()!=0){
				addCrittersToActiveCritterQueue();
			}
			
			updateProjectiles();			
			updateCritters();
			targetCritters();
			attackCritters();
			
		}


		if(Mouse.isButtonDown(0)){
			mouseClicked(Mouse.getX(), container.getHeight() - Mouse.getY(), sbg, container);
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
			
			Image img = BasicTowerGraphic;
			switch(t.getType()){
			case FREEZE:
				img = FreezeTowerGraphic;
				break;
			case GENERIC:
				//do nothing
				break;
			case SNIPER:
				img = SniperTowerGraphic;
				break;
			default:
				//do nothing
				break;
			
			}
		
			img.setRotation( (float) t.getRotationAngleInDegrees());
			img.drawCentered( (float) t.getXLoc(), (float) t.getYLoc());
		}
	}
	private void drawProjectiles(){
		for(Projectile p: projectileList){
			Image img;
			switch(p.getType()){
			case GENERIC:
				img = BasicTowerProjectileGraphic;
				break;
			case FREEZE:
				img = FreezeTowerProjectileGraphic;
				break;
			case SNIPER:
				img = SniperTowerProjectileGraphic;
				break;
			default:
				img = BasicTowerProjectileGraphic;
				break;
			}
			img.setRotation( (float) p.angleOfProjectileInDegrees());
			img.drawCentered((float)p.getX(),(float)p.getY());
		}
	}
	public void drawCritters(){
		for(Critter s : activeCritterQueue)
			//this method draws critters depending on if they are alive or not
			if(s.isVisible()&&s.isAlive())
			{
				drawCritter(s);

			}

	}


	private void drawCritter(Critter s){
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


	private void drawMapandOverlay(GameContainer container, Graphics g){
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
		WaveGraphic.draw(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel());
		//change wavebutton graphic
		if(!waveIsInProgress)
			NextWaveActiveGraphic.draw(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel() + WaveGraphic.getHeight() + 10);
		else
			NextWaveNonActiveGraphic.draw(currentMap.getWidthInPixel() - WaveGraphic.getWidth(), currentMap.getHeightInPixel() + WaveGraphic.getHeight() + 10);


		//draw tower graphics
		for (int i =0;i<maximumNumberTowers;i++){
			int xCorner = currentMap.getWidthInPixel() +towerGraphicXStart + ((i)%2)*towerGraphicXOffset;
			int yCorner = towerGraphicYStart + (i/2)*towerGraphicYOffset;
			Image img;
			switch(i){
			case 0:
				img = BasicTowerGraphic;
				break;
			case 1:
				img = FreezeTowerGraphic;
				break;
			case 2:
				img = SniperTowerGraphic;
				break;
			default:
				img = BasicTowerGraphic;
				break;
			}
			img.setRotation(0);
			img.drawCentered(xCorner +towerButtonWidth/2,yCorner +towerButtonHeight/2);
			
			

		}
		//draw sell and upgrade buttons
		int xCorner = currentMap.getWidthInPixel() +towerGraphicXStart + ((4)%2)*towerGraphicXOffset;
		int yCorner = towerGraphicYStart + (4/2)*towerGraphicYOffset;
		SellButtonGraphic.drawCentered(xCorner +towerButtonWidth/2,yCorner +towerButtonHeight/2);
		
		xCorner = currentMap.getWidthInPixel() +towerGraphicXStart + ((5)%2)*towerGraphicXOffset;
		yCorner = towerGraphicYStart + (5/2)*towerGraphicYOffset;
		UpgradeButtonGraphic.drawCentered(xCorner +towerButtonWidth/2,yCorner +towerButtonHeight/2);

		// drawing/updating the currency and level
		ttf.drawString( CurrencyGraphic.getWidth() + 5, (container.getHeight() - 40), "" + Player.getCredits());
		ttf.drawString(currentMap.getWidthInPixel() - 48, currentMap.getHeightInPixel() + 15, currentLevel + "");

		//if the mouse is on the map, snap to map grid
		if(mouseOnMap(Mouse.getX(),container.getHeight()-Mouse.getY())){
				Image img;
				switch(selectedTower){
				case -3:
					img = SellSelectGraphic;
					break;
				case -2:
					img = UpgradeSelectGraphic;
					break;
				case -1:
					img = TileSelectGraphic;
					break; 
				case 0:
					img = BasicTowerGraphic;
					break;
				case 1:
					img =  FreezeTowerGraphic;
					break;
				case 2:
					img = SniperTowerGraphic;
					break;
				default:
					img = TileSelectGraphic;
					break;
				}
				img.drawCentered(getClosestTileCenter(Mouse.getX()), container.getHeight() - getClosestTileCenter(Mouse.getY()));
		}
	}

	//for each tower in the tower list, find its closest target
	public void targetCritters(){
		for(Tower t : towerList){
			for(Critter c: activeCritterQueue){
				if(c.isAlive()&&c.isVisible()){
					//calculate distance
					double xDist= Math.abs(c.getXLoc() - t.getXLoc());
					double yDist= Math.abs(c.getYLoc() -  t.getYLoc());
					double dist = Math.sqrt((xDist*xDist)+(yDist*yDist));
					if(dist<t.getRange()){
						t.setTargetCritter(c);
						break;
					}
				}
				t.setTargetCritter(null);
			}
		}
	}

	//for each tower, attack the critter its suppose to attack, if it is able to
	public void attackCritters(){
		for(Tower t: towerList){
			if(t.getTargetCritter()!= null &&t.canAttack()){
				attackCritter(t);
				t.setTimeOfLastAttack(System.currentTimeMillis());
			}
		}
	}

	
	public void attackCritter(Tower source){
		projectileType projType = projectileType.GENERIC;
		switch(source.getType()){
		case GENERIC:
			projType = projectileType.GENERIC;
			break;
		case SNIPER:
			projType = projectileType.SNIPER;
			break;
		case FREEZE:
			projType = projectileType.FREEZE;
			break;
		default:
			break;
		}
		Projectile projectile = new Projectile(source.getXLoc(), source.getYLoc(), (double)source.getTargetCritter().getXLoc(), 
				(double)source.getTargetCritter().getYLoc(), source.getPower(), source.getTargetCritter(), projType);
		projectileList.add(projectile);
	}

	public void loadImages() throws SlickException{
		//initialize all graphics/images from graphics folder
		SandTileGraphic = new Image("graphics/SandTile.png");
		GravelTileGraphic = new Image ("graphics/GravelTile.png");
		BrickTileGraphic = new Image ("graphics/BrickTile.png");
		ExitButtonGraphic = new Image ("graphics/ExitButton.png");
		UpgradeButtonGraphic = new Image ("graphics/UpgradeButtonGraphic.png");
		SellButtonGraphic = new Image ("graphics/SellButtonGraphic.png");	
		UpgradeSelectGraphic = new Image("graphics/UpgradeSelectGraphic.png");
		SellSelectGraphic = new Image("graphics/SellSelectGraphic.png");
		CurrencyGraphic = new Image("graphics/CurrencyGraphic.png");
		TileSelectGraphic = new Image ("graphics/TileSelectGraphic.png");
		UpgradeSelectGraphic = new Image("graphics/UpgradeSelectGraphic.png");
		SellSelectGraphic = new Image("graphics/SellSelectGraphic.png");
		WaveGraphic = new Image ("graphics/WaveGraphic.png");
		NextWaveActiveGraphic = new Image("graphics/NextWaveActive.png");
		NextWaveNonActiveGraphic = new Image("graphics/NextWaveNonActive.png");
		HeartGraphic = new Image("graphics/Heart.png");
		TowerMenuOverlayGraphic = new Image("graphics/TowerMenuGraphic.png");
		

		BasicTowerGraphic = new Image("graphics/BasicTowerGraphic.png");
		BasicTowerProjectileGraphic = new Image("graphics/BasicTowerProjectileGraphic.png");
		
		FreezeTowerGraphic = new Image("graphics/FreezeTowerGraphic.png");
		FreezeTowerProjectileGraphic = new Image("graphics/FreezeTowerProjectileGraphic.png");

		SniperTowerGraphic = new Image ("graphics/SniperTowerGraphic.png");
		SniperTowerProjectileGraphic = new Image ("graphics/SniperTowerProjectileGraphic.png");
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
		SellButton = new Rectangle(currentMap.getWidthInPixel() +10, TowerMenuOverlayGraphic.getHeight() +  10, SellButtonGraphic.getWidth(), SellButtonGraphic.getHeight());
		UpgradeButton = new Rectangle(currentMap.getWidthInPixel() +10, TowerMenuOverlayGraphic.getHeight() + UpgradeButtonGraphic.getHeight()+ 20, NextWaveActiveGraphic.getWidth(), NextWaveActiveGraphic.getHeight());
		
		//create tower buttons
		TowerGraphicButtonsList = new ArrayList<Rectangle>();
		for(int i =0;i<maximumNumberTowers;i++){
			int xCorner = currentMap.getWidthInPixel() +towerGraphicXStart + ((i)%2)*towerGraphicXOffset;
			int yCorner = towerGraphicYStart + (i/2)*towerGraphicYOffset;
			TowerGraphicButtonsList.add(new Rectangle(xCorner, yCorner, towerButtonWidth, towerButtonHeight));

		}
		//sell and upgrade button
		int i = 4;
		int xCorner = currentMap.getWidthInPixel() +towerGraphicXStart + ((i)%2)*towerGraphicXOffset;
		int yCorner = towerGraphicYStart + (i/2)*towerGraphicYOffset;
		SellButton = new Rectangle(xCorner,yCorner, towerButtonWidth, towerButtonHeight);
		
		i = 5;
		xCorner = currentMap.getWidthInPixel() +towerGraphicXStart + ((i)%2)*towerGraphicXOffset;
		yCorner = towerGraphicYStart + (i/2)*towerGraphicYOffset;
		UpgradeButton = new Rectangle(xCorner,yCorner, towerButtonWidth, towerButtonHeight);
	}


	
	public void createCritterQueueforLevel(){
		int[][] locations = currentMap.getCornersList();


		generator = new CritterGenerator(locations,currentLevel);
		generator.createCritterQueue();
		generator.RandomizeCritterQueue();
		critterQueue = generator.getCritterQueue();
		activeCritterQueue = new LinkedList<Critter>();
		activeCritterQueue.add(critterQueue.poll());
	}

	public void addCrittersToActiveCritterQueue(){
		tickCount++;
		if(tickCount>critterSpawnDelay){
			activeCritterQueue.add(critterQueue.poll());
			tickCount=0;	
		}
	}
	
	public void updateCritters(){
		boolean crittersAreStillVisible= false;
		ArrayList<Critter> crittersToRemove = new ArrayList<Critter>();
		//for each critter list, update their movement if they are alive
		for(Critter s : activeCritterQueue){
			//only living critters can move!
			if(s.isAlive())
				s.move();
			else{
				Player.addCredits(s.getReward());
				crittersToRemove.add(s);
			}
			if(s.isVisible())
				crittersAreStillVisible=true;
			if(s.isAtEndPoint()){
				Player.decreaseLife();
				crittersToRemove.add(s);
			}
		}
		
		//remove all the dead critters and the critters that have arrived at the exit
		for(Critter s : crittersToRemove){
			activeCritterQueue.remove(s);
		}
		
		
		if(!crittersAreStillVisible){
			waveIsInProgress = false;
			currentLevel++;
		}
	}
	
	private void updateProjectiles(){
		//for each projectile update their locations
		pToRemove = new ArrayList<Projectile>();
		for(Projectile p: projectileList){
			if(!p.hasArrived())
				p.move();
			else
				pToRemove.add(p);
		}

		for(Projectile p: pToRemove){
			projectileList.remove(p);
		}
	}

	private void mouseClicked(int x, int y, StateBasedGame sbg, GameContainer container) throws SlickException {

		//protection against multiple click registration
		if(lastClick + mouseClickDelay > System.currentTimeMillis())
			return;
		lastClick = System.currentTimeMillis();

		if(ExitButton.contains(x,y)){
			restartGame(container, sbg);
		}

		//no towers selected
		if (selectedTower ==-1){

			if(NextWaveButton.contains(x,y)&& !waveIsInProgress){
				waveIsInProgress = true;
				createCritterQueueforLevel();
			}
			if(UpgradeButton.contains(x,y)){
				selectedTower = -2;
			}
			if(SellButton.contains(x,y)){
				selectedTower = -3;
			}
			
			for(int i=0;i<maximumNumberTowers;i++){
				if(TowerGraphicButtonsList.get(i).contains(x,y)){
					selectedTower = i;

				}
			}
			return;
		}
		//tower selected
		else if (selectedTower >=0){
			if(mouseOnMap(x,y)){
				Tower newTower = new BasicTower(getClosestTileCenter(x),getClosestTileCenter(y));
				switch(selectedTower){
				case 0:
					//do nothing
					break;
				case 1:
					newTower= new FreezeTower(getClosestTileCenter(x),getClosestTileCenter(y));
					break;
				case 2:
					newTower= new SniperTower(getClosestTileCenter(x),getClosestTileCenter(y));
					break;
		
				}
				
				towerList.add(newTower);
				Player.addCredits((-1)*newTower.getBuyingCost());

				if (Player.getCredits()<0){
					//deny tower building due to insufficient funds
					Player.addCredits(newTower.getBuyingCost());
					towerList.remove(newTower);
				}
			}
		}
		else if(selectedTower == -2){
			if(mouseOnMap(x,y)){
				Tower t = getNearestTower(x,y);
				if(t!=null)
					t.upgrade();
			}
		}
		else if(selectedTower == -3){
			if(mouseOnMap(x,y)){
				Tower t = getNearestTower(x,y);
				if(t!=null){
					t.refundTower();			
					towerList.remove(t);
				}
			}
			
		}
		selectedTower=-1;
	}
	
	public boolean mouseOnMap(int x, int y){
		if(x<(currentMap.getWidthInPixel())&& y< currentMap.getHeightInPixel()){
			return true;
		}
		else
			return false;
	}

	public Tower getNearestTower(int x, int y){
		double distanceApproximation=100;
		Tower nearestTower=null;
		for(Tower t: towerList){
			if(Math.abs(t.getXLoc()-x)+Math.abs(t.getYLoc()-y)<distanceApproximation){
				nearestTower = t;
				distanceApproximation =Math.abs(t.getXLoc()-x)+Math.abs(t.getYLoc()-y);
			}
			
		}
		return nearestTower;
	}
	public void restartGame(GameContainer container, StateBasedGame sbg) throws SlickException{
		currentLevel = startingLevel;
		Player.reset();
		waveIsInProgress = false;
		critterQueue = new LinkedList<Critter>();
		activeCritterQueue = new LinkedList<Critter>();
		towerList =  new ArrayList<Tower>();
		AppGameContainer gameContainer = (AppGameContainer) container;
		gameContainer.setDisplayMode(640, 480, false);
		sbg.enterState(Game.menuScreen);
	}

	public void setMap(Map pMap){
		currentMap = pMap;
	}

	public float getClosestTileCenter(float X){

		return (float) (Math.floor(X / currentMap.getPixelSize()) * currentMap.getPixelSize() + currentMap.getPixelSize() / 2);
	}

	
	@Override
	public int getID() {
		return Game.playScreen;
	}

}
