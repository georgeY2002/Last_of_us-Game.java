package views;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import engine.Game;
import exceptions.InvalidTargetException;
import exceptions.MovementException;
import exceptions.NoAvailableResourcesException;
import exceptions.NotEnoughActionsException;
import model.characters.Direction;
import model.characters.Explorer;
import model.characters.Hero;
import model.characters.Medic;
import model.characters.Zombie;
import model.collectibles.Supply;
import model.collectibles.Vaccine;
import model.world.CharacterCell;
import model.world.CollectibleCell;
import model.world.TrapCell;

public class Graphics extends JFrame implements ActionListener {
	private JPanel choosingHero;
	private JLabel startImg;
	private JButton[] availableHeroes;
	private JButton playButton;
	private JButton startGame;
	private Hero myHero;
	private JPanel possibleActions;
	private JButton attack;
	private JButton cure;
	private JButton useSpecial;
	private JButton endTurn;
	private JPanel mapPanel;
	private JButton[][] map;
	private model.characters.Character myTarget;
	private JButton moveUp;
	private JButton moveDown;
	private JButton moveLeft;
	private JButton moveRight;
	private JLabel hero1;
	private JLabel hero2;
	private JLabel hero3;
	private JLabel hero4;
	private JLabel hero5;
	private JLabel hero0;
	private Hero[] gameHeroes;
	private JComboBox<Hero> comboBox;
	private JLabel rules;
	private Container heroesPanel;

	public Graphics() {
		
		
		try {
			Game.loadHeroes("Heroes.csv");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Sorry, there are no heroes, check the csv file", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		map = new JButton[15][15];
		for(int i=0;i<15;i++)
			for(int j=0;j<15;j++)
				map[i][j] = new JButton();
		
		
		ImageIcon startup = new ImageIcon("starting.jpg");
		
		
		startImg = new JLabel();
		startImg.setIcon(startup);
		startImg.setBackground(Color.BLACK);
		startImg.setOpaque(true);
		startImg.setVerticalAlignment(JLabel.CENTER);
		startImg.setHorizontalAlignment(JLabel.CENTER);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		
		playButton = new JButton();
		playButton.setIcon(new ImageIcon("play.jpg"));
		playButton.setBounds(1100, 500, 350, 210);
		playButton.addActionListener(this);
		
		startImg.add(playButton);
 
		this.add(startImg);
		this.setSize(1000,600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==playButton) {
			JOptionPane.showMessageDialog(this,"TO START A GAME, CHOOSE A HERO THEN PRESS ON THE START BUTTON","TAKE CARE!", JOptionPane.INFORMATION_MESSAGE);
			startImg.setVisible(false);
			choosingHero = new JPanel();
			choosingHero.setLayout(new GridLayout(3,3));
			availableHeroes = new JButton[Game.availableHeroes.size()];
			for (int i = 0; i < Game.availableHeroes.size(); i++) {
				availableHeroes[i] = new JButton();
				availableHeroes[i].setBackground(Color.BLACK);
				availableHeroes[i].addActionListener(this);
				availableHeroes[i].setIcon(new ImageIcon("hero"+(i+1)+".jpg"));
				availableHeroes[i].setText(details(Game.availableHeroes.get(i)));
				availableHeroes[i].setForeground(Color.RED);
				choosingHero.add(availableHeroes[i]);
			}
			
			startGame = new JButton();
			startGame.setIcon(new ImageIcon("playButton.jpg"));
			startGame.setBackground(Color.BLACK);
			startGame.addActionListener(this);
			choosingHero.add(startGame);
			this.add(choosingHero);
			
			return;
		}
		
		

		for (int i = 0; i < availableHeroes.length; i++) 
			if (e.getSource() == availableHeroes[i]) {
				myHero = Game.availableHeroes.get(i);
				return;
			}
		


		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				if (e.getSource() == map[i][j]) {
					if (Game.map[i][j] instanceof CharacterCell && (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Hero)) {			
						myHero=(Hero) ((CharacterCell) Game.map[i][j]).getCharacter();
						JOptionPane.showMessageDialog(this,details(myHero), "THE HERO YOU ARE CONTROLLING", JOptionPane.INFORMATION_MESSAGE);					 
					}
					if (Game.map[i][j] instanceof CharacterCell && (((CharacterCell) Game.map[i][j]).getCharacter() instanceof Zombie)) {	
						myTarget=((CharacterCell) Game.map[i][j]).getCharacter();
						myHero.setTarget(myTarget);
					}
				}
			}
		}

		 
		if (e.getSource() == startGame) {
			
			Game.startGame(myHero);
			
			choosingHero.setVisible(false);
			
			this.setLayout(null);
			this.setSize(1500, 600);
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	
			
			possibleActions = new JPanel();
			possibleActions.setLayout(new GridLayout(1, 10));
			possibleActions.setBounds(0, 0, 900, 50);
			this.add(possibleActions);
			
			moveUp = new JButton("Move Up");
			moveUp.addActionListener(this);
			possibleActions.add(moveUp);
			
			moveLeft = new JButton("Move Left");
			moveLeft.addActionListener(this);
			possibleActions.add(moveLeft);
			
			moveRight = new JButton("Move Right");
			moveRight.addActionListener(this);
			possibleActions.add(moveRight);
			
			moveDown = new JButton("Move Down");
			moveDown.addActionListener(this);
			possibleActions.add(moveDown);
			
			attack = new JButton("Attack");
			attack.addActionListener(this);
			possibleActions.add(attack);
			
			useSpecial = new JButton("Use Special");
			useSpecial.addActionListener(this);
			possibleActions.add(useSpecial);
			
			cure = new JButton("Cure");
			cure.addActionListener(this);
			possibleActions.add(cure);	
			
			endTurn = new JButton("End Turn");
			endTurn.addActionListener(this);
			possibleActions.add(endTurn);
			
			
			
			gameHeroes= Game.heroes.toArray(new Hero[1]);
			comboBox = new JComboBox<Hero>(gameHeroes);
			comboBox.addActionListener(this);
			comboBox.setBounds(800, 50, 300, 50);
			this.add(comboBox);
			
			
			rules=new JLabel();
			rules.setLayout(null);
			rules.setBounds(800, 40, 300, 300);
			rules.setBackground(Color.red);
			this.add(rules);
			rules.setText("<html> if you want your target to be a hero,<br> "
					+ "choose it from the menu above <br>"
					+ "<br>"
					+ "if you want your target to be a zombie,<br> "
					+ "click on the zombie from the map<br>"
					+ "<br>"
					+ "if you want to control a different hero,<br>"
					+ "click on it from the map"
					+ "<html>" );
			rules.setForeground(Color.red);
			
			mapPanel = new JPanel();
			mapPanel.setLayout(new GridLayout(15, 15));
			mapPanel.setBounds(0, 50, 800, 500);
			this.add(mapPanel);

			
			map = new JButton[15][15];
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map.length; j++) {
					map[i][j] = new JButton();
					map[i][j].addActionListener(this);
					mapPanel.add(map[i][j]);
				}
			}
			
			updates();
			
			return;
		}

		
		if (e.getSource() == attack) {
			try {
				myHero.attack();
				updates();
			} catch (NotEnoughActionsException | InvalidTargetException error) {
				JOptionPane.showMessageDialog(this, error.getMessage());
			}
		}

		if (e.getSource() == moveUp) {
			try {
				if(Game.map[(myHero.getLocation().x)-1][myHero.getLocation().y] instanceof TrapCell)
					JOptionPane.showMessageDialog(this,"Oops, you are entering a Trap Cell", "WARNING!", JOptionPane.INFORMATION_MESSAGE);
				myHero.move(Direction.DOWN);
				updates();
			} catch (MovementException | NotEnoughActionsException error) {
				JOptionPane.showMessageDialog(this, error.getMessage());
			}
		}
		if (e.getSource() == moveLeft) {
			try {
				if(Game.map[(myHero.getLocation().x)][(myHero.getLocation().y)-1] instanceof TrapCell)
					JOptionPane.showMessageDialog(this,"Oops, you are entering a Trap Cell", "WARNING!", JOptionPane.INFORMATION_MESSAGE);
				myHero.move(Direction.LEFT);
				updates();
			} catch (MovementException | NotEnoughActionsException error) {
				JOptionPane.showMessageDialog(this, error.getMessage());
			}
		}
		if (e.getSource() == moveRight) {
			try {
				if(Game.map[(myHero.getLocation().x)][(myHero.getLocation().y)+1] instanceof TrapCell)
					JOptionPane.showMessageDialog(this,"Oops, you are entering a Trap Cell", "WARNING!", JOptionPane.INFORMATION_MESSAGE);
				myHero.move(Direction.RIGHT);
				updates();
			} catch (MovementException | NotEnoughActionsException error) {
				JOptionPane.showMessageDialog(this, error.getMessage());
			}
		}
		if (e.getSource() == moveDown) {
			try {
				if(Game.map[(myHero.getLocation().x)+1][(myHero.getLocation().y)] instanceof TrapCell)
					JOptionPane.showMessageDialog(this,"Oops, you are entering a Trap Cell", "WARNING!", JOptionPane.INFORMATION_MESSAGE);
				myHero.move(Direction.UP);
				updates();
			} catch (MovementException | NotEnoughActionsException error) {
				JOptionPane.showMessageDialog(this, error.getMessage());
			}
		}
		
		if (e.getSource() == cure) {
			try {
				myHero.cure();
				updates();
			} catch (NoAvailableResourcesException | InvalidTargetException | NotEnoughActionsException error) {
				JOptionPane.showMessageDialog(this, error.getMessage());
			}
		}

		if (e.getSource() == useSpecial) {
			try {
				myHero.useSpecial();
				updates();
			} catch (NoAvailableResourcesException | InvalidTargetException error) {
				JOptionPane.showMessageDialog(this, error.getMessage());
			}
		}
		if (e.getSource() == endTurn) {
			try {
				Game.endTurn();
				updates();
			} catch (NotEnoughActionsException | InvalidTargetException error) {
				JOptionPane.showMessageDialog(this, error.getMessage());
			}
		}
		if(e.getSource()==comboBox) {
			Hero tmp=(Hero) comboBox.getSelectedItem();
			myHero.setTarget(tmp);
		}
		
		if (Game.checkWin()) {
			JOptionPane.showMessageDialog(this, "You won");
		} 
		else 
			if (Game.checkGameOver()) {
				JOptionPane.showMessageDialog(this, "You lost");
		}
	}

	public String details(model.characters.Character c) {
		String tmp="";
		if(c instanceof Hero) {
			Hero h = (Hero) c;
			String type;
			if (h instanceof Medic)
				type = "Medic";
			else if (h instanceof Explorer)
				type = "Explorer";
			else
				type = "Fighter";
			
			tmp = 
			"Name : " + h.getName() + "<br>"
			+ "Type : " + type + "<br>"
			+ "Health = " + h.getCurrentHp()+ "<br>"
			+ "Attack Damage = " + h.getAttackDmg() + "<br>"
			+ "Actions Available = "+h.getActionsAvailable()+ "<br>"
			+ "Vaccines = " + h.getVaccineInventory().size() + "<br>"
			+ "Supplies = " + h.getSupplyInventory().size();
		}
		else {
				Zombie z = (Zombie)c;
			 	tmp = 
					"Type : Zombie <br>"
					+ "Name : " + z.getName()+ "<br>" 
					+ "Health = "+ z.getCurrentHp() + "<br>"
					+ "Attack Damage = " + z.getAttackDmg()+ "<br></html>";
		}
		
		return "<html>" + tmp + "</html>";
	}

	
	public static boolean isHeroInComboBox(Hero hero, JComboBox comboBox) {
	    for (int i = 0; i < comboBox.getItemCount(); i++) {
	        Object item = comboBox.getItemAt(i);
	        if (item instanceof Hero && ((Hero) item).equals(hero)) {
	            return true;
	        }
	    }
	    return false;
	}




	public void updates() {
		

		for(int i=0;i<Game.heroes.size();i++) {
			if(isHeroInComboBox(Game.heroes.get(i), comboBox)==false)
				comboBox.addItem(Game.heroes.get(i));
		}
		

		
		heroesPanel=new JPanel();
		heroesPanel.setLayout(new GridLayout(1,6));
		heroesPanel.setBounds(0, 550, 1500, 230);
		
		hero0=new JLabel();
		hero1=new JLabel();
		hero2=new JLabel();
		hero3=new JLabel();
		hero4=new JLabel();
		hero5=new JLabel();

		hero0.setForeground(Color.red);
		hero1.setForeground(Color.red);
		hero2.setForeground(Color.red);
		hero3.setForeground(Color.red);
		hero4.setForeground(Color.red);
		hero5.setForeground(Color.red);

		
		
		
		heroesPanel.add(hero0);
		heroesPanel.add(hero1);
		heroesPanel.add(hero2);
		heroesPanel.add(hero3);
		heroesPanel.add(hero4);
		heroesPanel.add(hero5);

		
		if(Game.heroes.size()>=1) 
			hero0.setText(details(Game.heroes.get(0)));
		if(Game.heroes.size()>=2)
			hero1.setText(details(Game.heroes.get(1)));
		if(Game.heroes.size()>=3)
			hero2.setText(details(Game.heroes.get(2)));
		if(Game.heroes.size()>=4)
			hero3.setText(details(Game.heroes.get(3)));
		if(Game.heroes.size()>=5)
			hero4.setText(details(Game.heroes.get(4)));
		if(Game.heroes.size()>=6)
			hero5.setText(details(Game.heroes.get(5)));

		this.add(heroesPanel);

		
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				map[i][j].setIcon(null);
				
				if (Game.map[i][j] instanceof CharacterCell) {
					CharacterCell tmp = (CharacterCell) Game.map[i][j];
					if (tmp.getCharacter() == null) {
						map[i][j].setIcon(new ImageIcon("emptyCell.jpg"));
					} 
					if (tmp.getCharacter() instanceof Zombie) {
						map[i][j].setIcon(new ImageIcon("zombieIcon.jpg"));
					}
					if (tmp.getCharacter() instanceof Hero) {
						map[i][j].setIcon(new ImageIcon("heroIcon.jpg"));
					}
				}
				
				if (Game.map[i][j] instanceof CollectibleCell) {
					CollectibleCell tmp2 = (CollectibleCell) Game.map[i][j];
					if (tmp2.getCollectible() instanceof Supply) {
						map[i][j].setIcon(new ImageIcon("supply.jpg"));
					} 
					if (tmp2.getCollectible() instanceof Vaccine){
						map[i][j].setIcon(new ImageIcon("vaccine.jpg"));
					}
				}
				
				if (Game.map[i][j] instanceof TrapCell) {
					map[i][j].setIcon(new ImageIcon("emptyCell.jpg"));
				}
			
				if (Game.map[i][j].isVisible() == false) {				
					map[i][j].setIcon(new ImageIcon("blur.jpg"));
				} 
             }
		}
	}
	public static void main(String[] args) {
		Graphics v = new Graphics();
	}
	
}