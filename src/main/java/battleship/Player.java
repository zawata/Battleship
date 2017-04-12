package battleship;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Player {

	// Variables----------------------------------------------------------------------------
	// Values
	private int hits = 0;
	@SuppressWarnings("unused")
	private static int misses = 0;
	private int[][] board = new int[10][10];
	private int[][] gameBoard = new int[10][10];
	private boolean[] shipPlace = new boolean[5];
	private boolean turn = true;
	private String shipName = "";
	private Player opponent;
	
	// private Player currentPlayer;
	String currentPlayer1 = "";
	private static Component sunkFrame;
	
	// Player objects
	Ship ac = new Ship();
	Ship bs = new Ship();
	Ship ds = new Ship();
	Ship sb = new Ship();
	Ship pb = new Ship();

	// CPU values
	private final static int[] BATTLESHIP_SIZES = new int[] { 5, 4, 3, 3, 2 };
	private String cpuShipNames[] = new String[] { "Aircraft Carrier",
			"Battleship", "Destroyer", "Submarine", "Patrol Boat" };
	private Ship cpuShips[] = new Ship[5];
	private final boolean[] cpuCompass = new boolean[4];
	private final Random random = new Random();
	private int orientation = 0;
	private boolean hunting = false;
	private String lastShip = "";
	private int nextShip = 0;
	private int cpuStartHit_x;
	private int cpuStartHit_y;
	private int cpuLastHit_x;
	private int cpuLastHit_y;

	// Files
	final static File file_1L = new File("src/main/java/battleship/img/lg_head.jpg");
	final static File file_g = new File("src/main/java/battleship/img/green.gif");
	final static File file_blu = new File("src/main/java/battleship/img/blue.gif");
	final static File file_r = new File("src/main/java/battleship/img/red.gif");
	final static File file_gry = new File("src/main/java/battleship/img/grey.gif");
	final static File file_blk = new File("src/main/java/battleship/img/black.gif");
	public Image icon = Toolkit.getDefaultToolkit()
			.getImage("src/main/java/battleship/img/icon.gif");

	// Main window
	private JFrame placeFrame = new JFrame("BattleShip");
	final static int size_xL = 1010;
	final static int size_yL = 700;

	// Left board components
	private JPanel shipPanel = new JPanel();
	private JPanel boxPanel = new JPanel();
	private JPanel cpuPanel = new JPanel();
	private JPanel cpuSunkPanel = new JPanel();
	MouseListener boardListener = new setupMouseListener();

	// Right board components
	private JPanel fireBoxPanel = new JPanel();
	private JPanel sunkBoxPanel = new JPanel();
	MouseListener gameListener = new gameMouseListener();

	// Ship selection components
	private String shipDirection = "";
	private JPanel boardPanel = new JPanel();
	private String shipsListArray[] = { "Aircraft Carrier", "Battleship",
			"Destroyer", "Submarine", "Patrol Boat" };
	private JList<String> shipList = new JList<String>(shipsListArray);
	private String shipDirections[] = { "Horizontal", "Vertical" };
	private JList<String> directionList = new JList<String>(shipDirections);
	private JButton doneButton;

	// The main class
	// ---------------------------------------------------------------
	
	Player(String currentPlayer) throws IOException {
		this.currentPlayer1 = currentPlayer;
	}

	// Figures out if the ship is where you fired
        // int params are where the 
	public boolean isRange(Ship ship, int x, int y) {
		if (ship.getDirectionOfShip().equals("Vertical")) {
			if (ship.getX() == x) {
				if (ship.getY() <= y && y <= (ship.getY() + ship.getSize())) {
					return true;
				}
			}
		}
		if (ship.getDirectionOfShip().equals("Horizontal")) {
			if (ship.getY() == y) {
				if (ship.getX() <= x && x <= (ship.getX() + ship.getSize())) {
					return true;
				}
			}
		}
		return false;
	}

	// For replacing the ships it removes the old ship from the array
	public void removeOldShip() {
		
		if (shipName.equals("Aircraft Carrier")) {
			if (ac.getDirectionOfShip().equals("Vertical")) {
				for (int i = 0; i < 5; i++) {
					board[ac.getX()][i + ac.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 5; i++) {
					board[i + ac.getX()][ac.getY()] = 0;
				}
			}
		}

		if (shipName.equals("Battleship")) {
			if (bs.getDirectionOfShip().equals("Vertical")) {
				for (int i = 0; i < 4; i++) {
					board[bs.getX()][i + bs.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 4; i++) {
					board[i + bs.getX()][bs.getY()] = 0;
				}
			}
		}

		if (shipName.equals("Submarine")) {
			if (sb.getDirectionOfShip().equals("Vertical")) {
				for (int i = 0; i < 3; i++) {
					board[sb.getX()][i + sb.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 3; i++) {
					board[i + sb.getX()][sb.getY()] = 0;
				}
			}
		}

		if (shipName.equals("Destroyer")) {
			if (ds.getDirectionOfShip().equals("Vertical")) {
				for (int i = 0; i < 3; i++) {
					board[ds.getX()][i + ds.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 3; i++) {
					board[i + ds.getX()][ds.getY()] = 0;
				}
			}
		}

		if (shipName.equals("Patrol Boat")) {
			if (pb.getDirectionOfShip().equals("Vertical")) {
				for (int i = 0; i < 2; i++) {
					board[pb.getX()][i + pb.getY()] = 0;
				}
			} else {
				for (int i = 0; i < 2; i++) {
					board[i + pb.getX()][pb.getY()] = 0;
				}
			}
		}

	}

	// find if the ship will be off the board 
	public boolean validPlace(int x, int y) {
		if (shipName.equals("Aircraft Carrier")) {
			if (shipDirection.equals("Vertical") && y <= 6) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 6) {
				return true;
			}
		}

		if (shipName.equals("Battleship")) {
			if (shipDirection.equals("Vertical") && y <= 7) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 7) {
				return true;
			}
		}

		if (shipName.equals("Submarine")) {
			if (shipDirection.equals("Vertical") && y <= 8) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 8) {
				return true;
			}
		}

		if (shipName.equals("Destroyer")) {
			if (shipDirection.equals("Vertical") && y <= 8) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 8) {
				return true;
			}
		}

		if (shipName.equals("Patrol Boat")) {
			if (shipDirection.equals("Vertical") && y <= 9) {
				return true;
			}
			if (shipDirection.equals("Horizontal") && x <= 9) {
				return true;
			}
		}

		return false;

	}

	// Looks to see if you can put the ship down
	public boolean freeBoard(int x, int y){
		if (shipName.equals("Aircraft Carrier")) {
			if (shipDirection.equals("Vertical")) {
				for (int i = 0; i < 5; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection.equals("Horizontal")) {
				for (int i = 0; i < 5; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}
		
		if (shipName.equals("Battleship")) {
			if (shipDirection.equals("Vertical")) {
				for (int i = 0; i < 4; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection.equals("Horizontal")) {
				for (int i = 0; i < 4; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}

		if (shipName.equals("Submarine")) {
			if (shipDirection.equals("Vertical")) {
				for (int i = 0; i < 3; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection.equals("Horizontal")) {
				for (int i = 0; i < 3; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}

		if (shipName.equals("Destroyer")) {
			if (shipDirection.equals("Vertical")) {
				for (int i = 0; i < 3; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection.equals("Horizontal")) {
				for (int i = 0; i < 3; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}

		if (shipName.equals("Patrol Boat")) {
			if (shipDirection.equals("Vertical")) {
				for (int i = 0; i < 2; i++) {
					if(board[(x - 1)][i + y - 1] == 1){
						return false;
					}
				}
			}
			if (shipDirection.equals("Horizontal")) {
				for (int i = 0; i < 2; i++) {
					if(board[(i + x - 1)][y - 1] == 1){
						return false;
					}
				}
			}
		}
		
		return true;
	}

	// Replaces the ships that were placed
	public void redrawShips() throws IOException {
		boxPanel.removeAll();

		if (shipPlace[0]) {
			for (int i = 0; i < 5; i++) {
				if (ac.getDirectionOfShip().equals("Vertical")) {
					board[(ac.getX())][i + ac.getY()] = 1;
					addBoard(ac.getX() + 1, ac.getY() + i + 1);
				} else if (ac.getDirectionOfShip().equals("Horizontal")) {
					board[i + ac.getX()][ac.getY()] = 1;
					addBoard(ac.getX() + i + 1, ac.getY() + 1);
				}
			}

		}
		if (shipPlace[1]) {
			for (int i = 0; i < 4; i++) {
				if (bs.getDirectionOfShip().equals("Vertical")) {
					board[(bs.getX())][i + bs.getY()] = 1;
					addBoard(bs.getX() + 1, bs.getY() + i + 1);
				} else if (bs.getDirectionOfShip().equals("Horizontal")) {
					board[i + bs.getX()][bs.getY()] = 1;
					addBoard(bs.getX() + i + 1, bs.getY() + 1);
				}
			}

		}
		if (shipPlace[2]) {
			for (int i = 0; i < 3; i++) {
				if (sb.getDirectionOfShip().equals("Vertical")) {
					board[(sb.getX())][i + sb.getY()] = 1;
					addBoard(sb.getX() + 1, sb.getY() + i + 1);
				} else if (sb.getDirectionOfShip().equals("Horizontal")) {
					board[i + sb.getX()][sb.getY()] = 1;
					addBoard(sb.getX() + i + 1, sb.getY() + 1);
				}
			}

		}
		if (shipPlace[3]) {
			for (int i = 0; i < 3; i++) {
				if (ds.getDirectionOfShip().equals("Vertical")) {
					board[(ds.getX())][i + ds.getY()] = 1;
					addBoard(ds.getX() + 1, ds.getY() + i + 1);
				} else if (ds.getDirectionOfShip().equals("Horizontal")) {
					board[i + ds.getX()][ds.getY()] = 1;
					addBoard(ds.getX() + i + 1, ds.getY() + 1);
				}
			}
		}
		if (shipPlace[4]) {
			for (int i = 0; i < 2; i++) {
				if (pb.getDirectionOfShip().equals("Vertical")) {
					board[(pb.getX())][i + pb.getY()] = 1;
					addBoard(pb.getX() + 1, pb.getY() + i + 1);
				} else if (pb.getDirectionOfShip().equals("Horizontal")) {
					board[i + pb.getX()][pb.getY()] = 1;
					addBoard(pb.getX() + i + 1, pb.getY() + 1);
				}
			}
		}

	}

	// Puts the ship in a new spot in the board
	public void replaceShip(int x, int y) throws IOException {
		// Places the ship vertical
		if (shipDirection.equals("Vertical")) {
			// For each ship it adds spots on the game board and in the array if
			// it hasn't been done before
			if (shipName.equals("Aircraft Carrier")) {
				if (y <= 6) {
					ac = new Ship("Aircraft Carrier", "Vertical", 5, x - 1,
							y - 1);
					for (int i = 0; i < 5; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}

			if (shipName.equals("Battleship")) {
				if (y <= 7) {
					bs = new Ship("Battleship", "Vertical", 4, x - 1, y - 1);
					for (int i = 0; i < 4; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}

			if (shipName.equals("Submarine")) {
				if (y <= 8) {
					sb = new Ship("Submarine", "Vertical", 3, x - 1, y - 1);
					for (int i = 0; i < 3; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}

			if (shipName.equals("Destroyer")) {
				if (y <= 8) {
					ds = new Ship("Destroyer", "Vertical", 3, x - 1, y - 1);
					for (int i = 0; i < 3; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}

			if (shipName.equals("Patrol Boat")) {
				if (y <= 9) {
					pb = new Ship("Patrol Boat", "Vertical", 2, x - 1, y - 1);
					for (int i = 0; i < 2; i++) {
						board[(x - 1)][i + y - 1] = 1;
					}
				}
			}
		}

		// -----------------------------------------------------------

		if (shipDirection.equals("Horizontal")) {
			if (shipName.equals("Aircraft Carrier")) {
				if (x <= 6) {
					ac = new Ship("Aircraft Carrier", "Horizontal", 5, x - 1,
							y - 1);
					for (int i = 0; i < 5; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

			if (shipName.equals("Battleship")) {
				if (x <= 7) {
					bs = new Ship("Battleship", "Horizontal", 4, x - 1, y - 1);
					for (int i = 0; i < 4; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

			if (shipName.equals("Submarine")) {
				if (x <= 8) {
					sb = new Ship("Submarine", "Horizontal", 3, x - 1, y - 1);
					for (int i = 0; i < 3; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

			if (shipName.equals("Destroyer")) {
				if (x <= 8) {
					ds = new Ship("Destroyer", "Horizontal", 3, x - 1, y - 1);
					for (int i = 0; i < 3; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

			if (shipName.equals("Patrol Boat")) {
				if (x <= 9) {
					pb = new Ship("Patrol Boat", "Horizontal", 2, x - 1, y - 1);
					for (int i = 0; i < 2; i++) {
						board[i + x - 1][y - 1] = 1;
					}
				}
			}

		}
		return;

	}

	// Adds a square to the board grid at the point given
	public void addBoard(int x, int y) throws IOException {
		// For values given as pixels
		if (x >= 45 && y >= 45) {
			JLabel img_bg = (new JLabel(new ImageIcon(ImageIO.read(file_g))));
			img_bg.setBounds(x, y, 45, 45);
			boxPanel.add(img_bg);
			boxPanel.repaint(); // Updates the image so the boxes appear
		}
		// For values given as grid coordinates
		if (x <= 10 && y <= 10) {
			JLabel img_bg = (new JLabel(new ImageIcon(ImageIO.read(file_g))));
			img_bg.setBounds(x * 45, y * 45, 45, 45);
			boxPanel.add(img_bg);
			boxPanel.repaint(); // Updates the image so the boxes appear
		}
	}

	// Returns the value of the game coordinates given pixels of the click
	public int getGrid(int x) {
		if (x >= 45 && x < 90) {
			return 1;
		} else if (x >= 90 && x < 135) {
			return 2;
		} else if (x >= 135 && x < 180) {
			return 3;
		} else if (x >= 180 && x < 225) {
			return 4;
		} else if (x >= 225 && x < 270) {
			return 5;
		} else if (x >= 270 && x < 315) {
			return 6;
		} else if (x >= 315 && x < 360) {
			return 7;
		} else if (x >= 360 && x < 405) {
			return 8;
		} else if (x >= 405 && x < 450) {
			return 9;
		} else if (x >= 450 && x < 500) {
			return 10;
		} else
			return 0;
	}

	// Returns the value of the pixel coordinates given pixels of the click
	public int getGridPixel(int x) {
		if (x >= 45 && x < 90) {
			return 45;
		} else if (x >= 90 && x < 135) {
			return 90;
		} else if (x >= 135 && x < 180) {
			return 135;
		} else if (x >= 180 && x < 225) {
			return 180;
		} else if (x >= 225 && x < 270) {
			return 225;
		} else if (x >= 270 && x < 315) {
			return 270;
		} else if (x >= 315 && x < 360) {
			return 315;
		} else if (x >= 360 && x < 405) {
			return 360;
		} else if (x >= 405 && x < 450) {
			return 405;
		} else if (x >= 450 && x < 500) {
			return 450;
		} else
			return 0;
	}

	// Places the color on the board for the ship and adds values to the array
	public boolean putShipsInBoard(int x, int y) throws IOException {

		// Places the ship vertical
		if (shipDirection.equals("Vertical")) {
			// For each ship it adds spots on the game board and in the array if
			// it hasn't been done before
			if (shipName.equals("Aircraft Carrier")) {
				if (y <= 6 && shipPlace[0] != true) {
					ac = new Ship("Aircraft Carrier", "Vertical", 5, x - 1,
							y - 1);
					for (int i = 0; i < 5; i++) {
						board[(x - 1)][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[0] = true;
					return true;
				}
			}

			if (shipName.equals("Battleship")) {
				if (y <= 7 && shipPlace[1] != true) {
					for (int i = 0; i < 4; i++) {
						bs = new Ship("Battleship", "Vertical", 4, x - 1, y - 1);
						board[x - 1][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[1] = true;
					return true;
				}
			}

			if (shipName.equals("Submarine")) {
				if (y <= 8 && shipPlace[2] != true) {
					sb = new Ship("Submarine", "Vertical", 3, x - 1, y - 1);
					for (int i = 0; i < 3; i++) {
						board[x - 1][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[2] = true;
					return true;
				}
			}

			if (shipName.equals("Destroyer")) {
				if (y <= 8 && shipPlace[3] != true) {
					for (int i = 0; i < 3; i++) {
						ds = new Ship("Destroyer", "Vertical", 3, x - 1, y - 1);
						board[x - 1][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[3] = true;
					return true;
				}
			}

			if (shipName.equals("Patrol Boat")) {
				if (y <= 9 && shipPlace[4] != true) {
					for (int i = 0; i < 2; i++) {
						pb = new Ship("Patrol Boat", "Vertical", 2, x - 1,
								y - 1);
						board[x - 1][i + y - 1] = 1;
						addBoard(x, y + i);
					}
					shipPlace[4] = true;
					return true;
				}
			}
		}

		// -----------------------------------------------------------

		if (shipDirection.equals("Horizontal")) {
			if (shipName.equals("Aircraft Carrier")) {
				if (x <= 6 && shipPlace[0] != true) {
					ac = new Ship("Aircraft Carrier", "Horizontal", 5, x - 1,
							y - 1);
					for (int i = 0; i < 5; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[0] = true;
					return true;
				}
			}

			if (shipName.equals("Battleship")) {
				if (x <= 7 && shipPlace[1] != true) {
					bs = new Ship("Battleship", "Horizontal", 4, x - 1, y - 1);
					for (int i = 0; i < 4; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[1] = true;
					return true;
				}
			}

			if (shipName.equals("Submarine")) {
				if (x <= 8 && shipPlace[2] != true) {
					sb = new Ship("Submarine", "Horizontal", 3, x - 1, y - 1);
					for (int i = 0; i < 3; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[2] = true;
					return true;
				}
			}

			if (shipName.equals("Destroyer")) {
				if (x <= 8 && shipPlace[3] != true) {
					ds = new Ship("Destroyer", "Horizontal", 3, x - 1, y - 1);
					for (int i = 0; i < 3; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[3] = true;
					return true;
				}
			}

			if (shipName.equals("Patrol Boat")) {
				if (x <= 9 && shipPlace[4] != true) {
					pb = new Ship("Patrol Boat", "Horizontal", 2, x - 1, y - 1);
					for (int i = 0; i < 2; i++) {
						board[i + x - 1][y - 1] = 1;
						addBoard(x + i, y);
					}
					shipPlace[4] = true;
					return true;
				}
			}
		}

		return false;

	}

	// Loads the GUI for the game
	public void mainGUI() throws IOException {
		// Loads the board image and sets size
		final JLabel board_img = (new JLabel(new ImageIcon(
				ImageIO.read(new File("src/main/java/battleship/img/board.gif")))));
		board_img.setBounds(0, 0, 500, 500);
		// Makes a second board
		final JLabel board_img1 = (new JLabel(new ImageIcon(
				ImageIO.read(new File("src/main/java/battleship/img/board.gif")))));
		board_img1.setBounds(0, 0, 500, 500);

		// Loads the board image and sets size
		JLabel img_1L = (new JLabel(new ImageIcon(ImageIO.read(file_1L))));
		img_1L.setBounds(0, 0, size_xL, 163);

		// Makes the CPU sunk ships appear
		cpuSunkPanel.setBounds(0, 0, 500, 500);
		cpuSunkPanel.setLayout(null);
		cpuSunkPanel.setOpaque(false);

		// Makes the CPU shots appear
		cpuPanel.setBounds(0, 0, 500, 500);
		cpuPanel.setLayout(null);
		cpuPanel.setOpaque(false);

		// Panel to make ships appear on top
		boxPanel.setBounds(0, 0, 500, 500);
		boxPanel.setLayout(null);
		boxPanel.setOpaque(false);

		// Ship board setup
		boardPanel.add(cpuSunkPanel);
		boardPanel.add(cpuPanel);
		boardPanel.add(boxPanel);
		boardPanel.add(board_img);
		boardPanel.setLayout(null);
		boardPanel.setOpaque(false);
		boardPanel.setBackground(Color.BLACK);
		boardPanel.setBounds(0, 173, 500, 500);
		boardPanel.addMouseListener(boardListener);

		// Directions text
		JTextArea directionsTxt = new JTextArea("Game directions\n\n");
		directionsTxt.append("1. Select a ship from the leftmost box below.\n");
		directionsTxt
				.append("2. Select a direction from the rightmost box below.\n");
		directionsTxt
				.append("3. Select a coordinate for the beginning position of your ship on the grid.\n");
		directionsTxt
				.append("4. Click \"Done\" once all 5 ships are placed.\n");
		directionsTxt.setBounds(10, 10, 475, 100);
		directionsTxt.setFont(new Font("Tahoma", Font.PLAIN, 12));
		directionsTxt.setForeground(Color.WHITE);
		directionsTxt.setBackground(Color.DARK_GRAY);
		directionsTxt.setEditable(false);

		// Ship placement menu
		shipList.setFont(new Font("Tahoma", Font.PLAIN, 17));
		shipList.setForeground(Color.WHITE);
		shipList.setBackground(Color.DARK_GRAY);
		shipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		shipList.setBounds(93, 192, 115, 115);
		shipList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getSource() == shipList
						&& !event.getValueIsAdjusting()) {
					String stringValue = (String) shipList.getSelectedValue();
					if (stringValue != null)
						shipName = stringValue;
				}

			}

		});
		shipList.setListData(shipsListArray);
		shipList.setSelectedIndex(0);

		// Direction of the ship menu
		directionList.setFont(new Font("Tahoma", Font.PLAIN, 17));
		directionList.setForeground(Color.WHITE);
		directionList.setBackground(Color.DARK_GRAY);
		directionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		directionList.setBounds(292, 192, 115, 115);
		directionList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getSource() == directionList
						&& !event.getValueIsAdjusting()) {
					String stringValue = (String) directionList
							.getSelectedValue();
					if (stringValue != null)
						shipDirection = stringValue;
				}

			}

		});
		directionList.setListData(shipDirections);
		directionList.setSelectedIndex(0);

		// Done Button
		doneButton = new JButton("Done");
		doneButton.setBounds(200, 400, 100, 50);
		doneButton.setEnabled(false);
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sunkBoxPanel.setBounds(0, 0, 500, 500);
				sunkBoxPanel.setLayout(null);
				sunkBoxPanel.setOpaque(false);
				fireBoxPanel.setBounds(0, 0, 500, 500);
				fireBoxPanel.setLayout(null);
				fireBoxPanel.setOpaque(false);
				shipPanel.removeAll();
				shipPanel.add(sunkBoxPanel);
				shipPanel.add(fireBoxPanel);
				shipPanel.add(board_img1);
				shipPanel.addMouseListener(gameListener);
				shipPanel.repaint();
				boardPanel.removeMouseListener(boardListener);
			}

		});

		// Putting the ship menu next to the player ship board
		shipPanel.add(doneButton);
		shipPanel.add(directionsTxt);
		shipPanel.add(directionList);
		shipPanel.add(shipList);
		shipPanel.setLayout(null);
		shipPanel.setOpaque(false);
		shipPanel.setBackground(Color.BLACK);
		shipPanel.setBounds(505, 173, 500, 500);

		// Frame setup
		placeFrame.add(img_1L);
		placeFrame.add(boardPanel);
		placeFrame.add(shipPanel);
		placeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		placeFrame.setSize(size_xL, size_yL);
		placeFrame.getContentPane().setBackground(Color.BLACK);
		placeFrame.setLayout(null);
		placeFrame.setIconImage(icon);
		placeFrame.setResizable(false);
		placeFrame.setVisible(true);

	}

	// Adds shot to board
	public void placeShotLeft(String type, int x, int y) throws IOException {
		if (type.equals("miss")) {
			JLabel img_g = (new JLabel(new ImageIcon(ImageIO.read(file_gry))));
			img_g.setBounds(x * 45, y * 45, 45, 45);
			boxPanel.add(img_g);
			boxPanel.repaint(); // Updates the image so the boxes appear

		}
		if (type.equals("hit")) {
			JLabel img_r = (new JLabel(new ImageIcon(ImageIO.read(file_r))));
			img_r.setBounds(x * 45, y * 45, 45, 45);
			boxPanel.add(img_r);
			boxPanel.repaint(); // Updates the image so the boxes appear
		}

	}

	// fires shot at other player
	public boolean fire(int x, int y) throws IOException {
		if (gameBoard[x - 1][y - 1] == 0) {

			gameBoard[x - 1][y - 1] = 1;

			if (opponent.board[x - 1][y - 1] == 0) {
				// misses++;
				fireGUI("miss", x, y);
				return true;
			} else if (opponent.board[x - 1][y - 1] == 1) {
				hits++;
				fireGUI("hit", x, y);
				findShip(x - 1, y - 1);
				return true;
			}
		}
		// returning false if you already fired there
		return false;
	}

	// Finds what ship is being hit
	private void findShip(int x, int y) throws IOException {

		if (isRange(opponent.cpuShips[0], x, y)) {
			opponent.cpuShips[0].addHits();
			if (opponent.cpuShips[0].sunk()) {
				fillSunk(opponent.cpuShips[0]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Aircraft Carrier");
			}
		} else if (isRange(opponent.cpuShips[1], x, y)) {
			opponent.cpuShips[1].addHits();
			if (opponent.cpuShips[1].sunk()) {
				fillSunk(opponent.cpuShips[1]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Battleship");
			}
		} else if (isRange(opponent.cpuShips[2], x, y)) {
			opponent.cpuShips[2].addHits();
			if (opponent.cpuShips[2].sunk()) {
				fillSunk(opponent.cpuShips[2]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Destroyer");
			}
		} else if (isRange(opponent.cpuShips[3], x, y)) {
			opponent.cpuShips[3].addHits();
			if (opponent.cpuShips[3].sunk()) {
				fillSunk(opponent.cpuShips[3]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Submarine");
			}
		} else if (isRange(opponent.cpuShips[4], x, y)) {
			opponent.cpuShips[4].addHits();
			if (opponent.cpuShips[4].sunk()) {
				fillSunk(opponent.cpuShips[4]);
				JOptionPane.showMessageDialog(sunkFrame,
						"You sunk the Patrol Boat");
			}
		}
	}

	// When a ship is sunk this adds the black over ship
	private void fillSunk(Ship ship) throws IOException {
		if (ship.getDirectionOfShip().equals("Vertical")) {
			for (int i = 0; i < ship.getSize(); i++) {
				fireGUI("sunk", (ship.getX() + 1), (ship.getY() + i + 1));
			}
		} else if (ship.getDirectionOfShip().equals("Horizontal")) {
			for (int i = 0; i < ship.getSize(); i++) {
				fireGUI("sunk", (ship.getX() + i + 1), (ship.getY() + 1));
			}
		}
	}

	// Adds the color to the board 
	private void fireGUI(String type, int x, int y) throws IOException {
		if (type.equals("miss")) {
			JLabel img_g = (new JLabel(new ImageIcon(ImageIO.read(file_gry))));
			img_g.setBounds(x * 45, y * 45, 45, 45);
			fireBoxPanel.add(img_g);
			fireBoxPanel.repaint(); // Updates the image so the boxes appear

		}
		if (type.equals("hit")) {
			JLabel img_r = (new JLabel(new ImageIcon(ImageIO.read(file_r))));
			img_r.setBounds(x * 45, y * 45, 45, 45);
			fireBoxPanel.add(img_r);
			fireBoxPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("sunk")) {
			JLabel img_blk = (new JLabel(new ImageIcon(ImageIO.read(file_blk))));
			img_blk.setBounds(x * 45, y * 45, 45, 45);
			sunkBoxPanel.add(img_blk);
			sunkBoxPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("cpuShow")) {
			JLabel img_g = (new JLabel(new ImageIcon(ImageIO.read(file_g))));
			img_g.setBounds(x * 45, y * 45, 45, 45);
			sunkBoxPanel.add(img_g);
			sunkBoxPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("cpuHit")) {
			JLabel img_r = (new JLabel(new ImageIcon(ImageIO.read(file_r))));
			img_r.setBounds(x * 45, y * 45, 45, 45);
			cpuPanel.add(img_r);
			cpuPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("cpuMiss")) {
			JLabel img_g = (new JLabel(new ImageIcon(ImageIO.read(file_gry))));
			img_g.setBounds(x * 45, y * 45, 45, 45);
			cpuPanel.add(img_g);
			cpuPanel.repaint(); // Updates the image so the boxes appear
		}
		if (type.equals("cpuSunk")) {
			JLabel img_blk = (new JLabel(new ImageIcon(ImageIO.read(file_blk))));
			img_blk.setBounds(x * 45, y * 45, 45, 45);
			cpuSunkPanel.add(img_blk);
			cpuSunkPanel.repaint(); // Updates the image so the boxes appear
		}
	}

	// Makes an instance of the other player to get their board
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	//Checks to see if a player has won the game
	public boolean checkWin() throws IOException {
		if (opponent.hits == 17 || hits == 17) {
			if (opponent.hits == 17) {
				showComputerShips();
				JOptionPane.showMessageDialog(sunkFrame, "You lost");
				placeFrame.dispose();
			}
			if (hits == 17) {
				JOptionPane.showMessageDialog(sunkFrame, "You Win!");
				placeFrame.dispose();

			}
			return true;
		}
		return false;

	}

	//If you lose the game the cpu ships are shown in green on the board
	private void showComputerShips() throws IOException {
		for (int j = 0; j < 5; j++) {
			if (opponent.cpuShips[j].getHits() != opponent.cpuShips[j]
					.getSize()) {
				if (opponent.cpuShips[j].getDirectionOfShip()
						.equals("Vertical")) {
					for (int i = 0; i < opponent.cpuShips[j].getSize(); i++) {
						fireGUI("cpuShow", (opponent.cpuShips[j].getX() + 1),
								(opponent.cpuShips[j].getY() + i + 1));
					}
				} else if (opponent.cpuShips[j].getDirectionOfShip().equals(
						"Horizontal")) {
					for (int i = 0; i < opponent.cpuShips[j].getSize(); i++) {
						fireGUI("cpuShow",
								(opponent.cpuShips[j].getX() + i + 1),
								(opponent.cpuShips[j].getY() + 1));
					}
				}
			}
		}
	}

	// Ship placement for CPU
	public void cpuShips() {
		int count = 0;
		for (int size : BATTLESHIP_SIZES) {
			while (!computeraddShip(size, count))
				;
			count++;
		}
	}

	// Puts the cpu values back to 0 when it is done sinking a ship
	public void resetCpu() {
		orientation = 0;
		hunting = false;
		nextShip = 0;
		setCompass();
		lastShip = "";
		cpuLastHit_y = 0;
		cpuLastHit_x = 0;
		cpuStartHit_y = 0;
		cpuStartHit_x = 0;

	}

	// Adding ships to the board
	public boolean computeraddShip(int length, int count) {

		// Generate two random coordinates between 1 and 10, one limited by
		// battleship length
		int x = random.nextInt(10 - length);
		int y = random.nextInt(10);
		int d = random.nextInt(2);
		// Randomly choose horizontal or vertical
		if (d == 1) {
			if (x + length < 10) {

				for (int i = 0; i < length; i++) {
					if (board[x + i][y] == 1) {
						return false;
					}
				}
				cpuShips[count] = new Ship(cpuShipNames[count], "Horizontal",
						length, x, y);
				for (int i = 0; i < length; i++) {
					board[x + i][y] = 1;
				}
				return true;
			}
		}

		else if (d == 0) {
			if (y + length < 10) {
				for (int i = 0; i < length; i++) {
					if (board[x][y + i] == 1) {
						return false;
					}
				}
				cpuShips[count] = new Ship(cpuShipNames[count], "Vertical",
						length, x, y);
				for (int i = 0; i < length; i++) {
					board[x][y + i] = 1;
				}
				return true;
			}
		}
		return false;
	}

	// Makes a count of the number of hits each ship has
	private boolean sameShip(int x, int y) throws IOException {
		if (isRange(opponent.ac, x, y)) {
			opponent.ac.addHits();
			if (opponent.ac.sunk()) {
				cpuFillSunk(opponent.ac);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Aircraft Carrier was sunk");
			}
			if (lastShip == "ac") {
				return true;
			}
			lastShip = "ac";
			return false;
		} else if (isRange(opponent.bs, x, y)) {
			opponent.bs.addHits();
			if (opponent.bs.sunk()) {
				cpuFillSunk(opponent.bs);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Battleship was sunk");
			}
			if (lastShip == "bs") {
				return true;
			}
			lastShip = "bs";
			return false;
		} else if (isRange(opponent.ds, x, y)) {
			opponent.ds.addHits();
			if (opponent.ds.sunk()) {
				cpuFillSunk(opponent.ds);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Destroyer was sunk");
			}
			if (lastShip == "ds") {
				return true;
			}
			lastShip = "ds";
			return false;
		} else if (isRange(opponent.sb, x, y)) {
			opponent.sb.addHits();
			if (opponent.sb.sunk()) {
				cpuFillSunk(opponent.sb);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Submarine was sunk");
			}
			if (lastShip == "sb") {
				return true;
			}
			lastShip = "sb";
			return false;
		} else if (isRange(opponent.pb, x, y)) {
			opponent.pb.addHits();
			if (opponent.pb.sunk()) {
				cpuFillSunk(opponent.pb);
				resetCpu();
				JOptionPane.showMessageDialog(sunkFrame,
						"Your Patrol Boat was sunk");
			}
			if (lastShip == "pb") {
				return true;
			}
			lastShip = "pb";
			return false;
		}
		return false;
	}

	// Fills a ship with black when the cpu sinks it
	private void cpuFillSunk(Ship ship) throws IOException {
		if (ship.getDirectionOfShip().equals("Vertical")) {
			for (int i = 0; i < ship.getSize(); i++) {
				opponent.fireGUI("cpuSunk", (ship.getX() + 1),
						(ship.getY() + i + 1));
			}
		} else if (ship.getDirectionOfShip().equals("Horizontal")) {
			for (int i = 0; i < ship.getSize(); i++) {
				opponent.fireGUI("cpuSunk", (ship.getX() + i + 1),
						(ship.getY() + 1));
			}
		}
	}

	// Sets the initial values of the direction array and resets
	public void setCompass() {

		for (int i = 0; i < cpuCompass.length; i++)
			cpuCompass[i] = false;
	}

	// Takes a shot for the cpu and adds the shot to the board 
	public boolean cpuTakeShot(int x, int y) throws IOException {
		gameBoard[x][y] = 1;
		if (opponent.board[x][y] == 0) {
			// Player.misses++;
			opponent.fireGUI("cpuMiss", x + 1, y + 1);
			opponent.turn = true;
			return false;
		} else if (opponent.board[x][y] == 1) {
			hits++;
			opponent.fireGUI("cpuHit", x + 1, y + 1);
			opponent.turn = true;
			return true;
		}
		return false;
	}

	// The logic for the cpu taking shots
	public boolean cpuFire() throws IOException {
		// Finds a random number in the range
		int x = random.nextInt(10);
		int y = random.nextInt(10);

		// If the last shot was a miss then the current shot is fired randomly 
		if (gameBoard[x][y] == 0 && !hunting && nextShip == 0) {
			// If the shot is a hit then it sets the next shot to be searching
			if (cpuTakeShot(x, y)) {
				cpuStartHit_x = x;
				cpuStartHit_y = y;
				sameShip(x, y);
				hunting = true;
			}
			return true;
		}

		else if (hunting) {
			// Finding the direction of the ship starting with up and going clockwise  
			if (orientation <= 0) {
				for (int i = 0; i < cpuCompass.length; i++) {
					if (!cpuCompass[i]) {
						cpuCompass[i] = true;

						if (i == 0) {
							if (cpuStartHit_y - 1 > -1
									&& gameBoard[cpuStartHit_x][cpuStartHit_y - 1] == 0) {
								//checks to see if the shot is on the board and not already shot
								if (cpuTakeShot(cpuStartHit_x,
										cpuStartHit_y - 1)) {
									// If the shot is on the same ship the direction is found
									if (sameShip(cpuStartHit_x,
											cpuStartHit_y - 1)) {
										orientation = 1;
										cpuLastHit_x = cpuStartHit_x;
										cpuLastHit_y = cpuStartHit_y - 1;
									}
								}
								return true;
							}

						} else if (i == 1) {
							if (cpuStartHit_x + 1 < 10
									&& gameBoard[cpuStartHit_x + 1][cpuStartHit_y] == 0) {
								if (cpuTakeShot(cpuStartHit_x + 1,
										cpuStartHit_y)) {
									if (sameShip(cpuStartHit_x + 1,
											cpuStartHit_y)) {
										orientation = 2;
										cpuLastHit_x = cpuStartHit_x + 1;
										cpuLastHit_y = cpuStartHit_y;
									}
								}
								return true;

							}
						} else if (i == 2) {
							if (cpuStartHit_y + 1 < 10
									&& gameBoard[cpuStartHit_x][cpuStartHit_y + 1] == 0) {
								if (cpuTakeShot(cpuStartHit_x,
										cpuStartHit_y + 1)) {
									if (sameShip(cpuStartHit_x,
											cpuStartHit_y + 1)) {
										orientation = 3;
										cpuLastHit_x = cpuStartHit_x;
										cpuLastHit_y = cpuStartHit_y + 1;
									}
								}
								return true;
							}

						} else if (i == 3) {
							if (cpuStartHit_x - 1 > -1
									&& gameBoard[cpuStartHit_x - 1][cpuStartHit_y] == 0) {
								if (cpuTakeShot(cpuStartHit_x - 1,
										cpuStartHit_y)) {
									if (sameShip(cpuStartHit_x - 1,
											cpuStartHit_y)) {
										orientation = 4;
										cpuLastHit_x = cpuStartHit_x - 1;
										cpuLastHit_y = cpuStartHit_y;
									}

								}
								return true;
							}
						}
					}
					
				}
			

			} else if (orientation > 0) {
				// When the direction of the ship is known The boat shot until it sinks
				if (orientation == 1) {
					if (cpuLastHit_y - 1 > -1
							&& gameBoard[cpuLastHit_x][cpuLastHit_y - 1] == 0) {
						if (cpuTakeShot(cpuLastHit_x, cpuLastHit_y - 1)) {
							if (sameShip(cpuLastHit_x, cpuLastHit_y - 1)) {
								cpuLastHit_y = cpuLastHit_y - 1;
								return true;
							}
						} else { // If the shot was a miss or off the board the direction is changed
							orientation = 3;
							cpuLastHit_y = cpuStartHit_y;
							return true;
						}
					} else {
						orientation = 3;
						cpuLastHit_y = cpuStartHit_y;
						return false;
					}
				} else if (orientation == 2) {
					if (cpuLastHit_x + 1 < 10
							&& gameBoard[cpuLastHit_x + 1][cpuLastHit_y] == 0) {
						if (cpuTakeShot(cpuLastHit_x + 1, cpuLastHit_y)) {
							if (sameShip(cpuLastHit_x + 1, cpuLastHit_y)) {
								cpuLastHit_x = cpuLastHit_x + 1;
								return true;
							}
						} else {
							orientation = 4;
							cpuLastHit_x = cpuStartHit_x;
							return true;
						}
					} else {
						orientation = 4;
						cpuLastHit_x = cpuStartHit_x;
						return false;
					}
				} else if (orientation == 3) {
					if (cpuLastHit_y + 1 < 10
							&& gameBoard[cpuLastHit_x][cpuLastHit_y + 1] == 0) {
						if (cpuTakeShot(cpuLastHit_x, cpuLastHit_y + 1)) {
							if (sameShip(cpuLastHit_x, cpuLastHit_y + 1)) {
								cpuLastHit_y = cpuLastHit_y + 1;
								return true;
							}
						} else {
							orientation = 1;
							cpuLastHit_y = cpuStartHit_y;
							return true;
						}
					} else {
						orientation = 1;
						cpuLastHit_y = cpuStartHit_y;
						return false;
					}
				} else if (orientation == 4) {
					if (cpuLastHit_x - 1 > -1
							&& gameBoard[cpuLastHit_x - 1][cpuLastHit_y] == 0) {
						if (cpuTakeShot(cpuLastHit_x - 1, cpuLastHit_y)) {
							if (sameShip(cpuLastHit_x - 1, cpuLastHit_y)) {
								cpuLastHit_x = cpuLastHit_x - 1;
								return true;
							}
						} else {
							orientation = 2;
							cpuLastHit_x = cpuStartHit_x;
							return true;
						}
					} else {
						orientation = 2;
						cpuLastHit_x = cpuStartHit_x;
						return false;
					}
				}
				return false;
			}

		}

		return false;

	}

	// Classes-----------------------------------------------------------------

	// Holds the values for the ships
	public class Ship {
		String name, directionOfShip;
		int x, y, size, hits;

		Ship(String name, String directionOfShip, int size, int x, int y) {
			this.name = name;
			this.directionOfShip = directionOfShip;
			this.size = size;
			this.x = x;
			this.y = y;
			this.hits = 0;
		}

		public void shipReset() {
			x = 0;
			y = 0;
			directionOfShip = "";

		}

		public Ship() {

		}

		public void printShips() {

			System.out.println(name);
			System.out.println(directionOfShip);
			System.out.println(x);
			System.out.println(y);
			System.out.println(size);
			System.out.println(hits);
		}

                //If hits on this ship equal its size, its sunk
		public boolean sunk() {
			if (this.hits == this.size) {
				return true;
			} else {
				return false;
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDirectionOfShip() {
			return directionOfShip;
		}

		public void setDirectionOfShip(String directionOfShip) {
			this.directionOfShip = directionOfShip;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getHits() {
			return hits;
		}

		public void setHits(int hits) {
			this.hits = hits;
		}

		public void addHits() {
			this.hits++;
		}

	}

	// Takes values of mouse clicks for the game board.
	public class setupMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			int x = getGrid(e.getX());
			int y = getGrid(e.getY());
			try {
				if (x >= 1 && y >= 1 && freeBoard(x, y)) {
					if (!putShipsInBoard(x, y) && validPlace(x, y)) {
						removeOldShip();
						replaceShip(x, y);
						redrawShips();
					}
					if (shipPlace[0] == true && shipPlace[1] == true
							&& shipPlace[2] == true && shipPlace[3] == true
							&& shipPlace[4] == true) {
						doneButton.setEnabled(true);
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}
	}

	// Takes values to pass to fire
	public class gameMouseListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			int x = getGrid(e.getX());
			int y = getGrid(e.getY());
			if (x >= 1 && y >= 1 && turn) {
				try {
					if (fire(x, y)) {
						turn = false;
						opponent.turn = true;
						if (!checkWin()) {
							while (!opponent.cpuFire())
								
							checkWin();
						}

					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}

	}
        
        public int[][] getBoard(){
            return board;
        }
        
        public void setShipName(String s){
            shipName = s;
        }

}