package com.SER216.BattleShip;

import javafx.scene.transform.Rotate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import static com.SER216.BattleShip.Util.boardWidth;
import static com.SER216.BattleShip.Util.resources;

public class MainGUI {

    // Main window
    private final JFrame placeFrame = new JFrame("BattleShip");
    private final static int size_xL = 1010;
    private final static int size_yL = 700;

    private final JPanel placePanel = new JPanel();
    private final JPanel playerBoardPanel = new JPanel();
    private final JPanel cpuBoardPanel = new JPanel();

    private final MouseListener boardListener = new setupMouseListener();
    private final MouseListener gameListener = new gameMouseListener();

    // Ship selection components
    private JList<String> shipList = new JList<>(Ship.getShipNames());
    private JList<String> directionList = new JList<>(Ship.getDirectionNames());
    private JButton doneButton;
    private JTextArea directionsTxt;

    // Files
    private final JLabel playerBoard = new JLabel();
    private final JLabel cpuBoard = new JLabel();

    private final static Image headerIMG = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/main/lg_head.jpg"));
    private final static Image boardIMG = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("resources/main/board.gif"));

    public enum TileColors {
        Blank(0, null),        // Nothing
        Green(1, "resources/tiles/green.gif"), // Ship
        Blue (2, "resources/tiles/blue.gif"), // ??
        Red  (3, "resources/tiles/red.gif"), // Hit
        Grey (4, "resources/tiles/grey.gif"), // Miss
        Black(5, "resources/tiles/black.gif"); // Sunk

        private final int value;
        private final String file;
        TileColors(final int value, final String file) {
            this.value = value;
            this.file = file;
        }
        public int getValue() { return value; }
        public Image getTileImage() {
            return Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(file));
        }
    }

    public static TileColors getColorByValue(int value) {
        for (TileColors color : TileColors.values()) {
            if(color.getValue() == value)
                return color;
        }
        return null;
    }

    public Image icon = Toolkit.getDefaultToolkit().getImage(resources + "icon.gif");

    //Players
    private Human player;
    private CPU cpu;

    private boolean player1_turn = false;

    static boolean DEBUG = false;

    // Loads the GUI for the game
    public void init(Human player1, CPU cpu) throws IOException {

        this.player = player1;
        this.cpu = cpu;
        
        // player GameBoard
        playerBoard.setIcon(new ImageIcon(boardIMG));
        playerBoard.setBounds(0, 0, 500, 500);

        playerBoard.setIcon(new ImageIcon(boardIMG));
        playerBoard.setBounds(0, 0, 500, 500);

        // cpu GameBoard
        cpuBoard.setIcon(new ImageIcon(boardIMG));
        cpuBoard.setBounds(0, 0, 500, 500);

        // battleship header
        JLabel headerLabel = (new JLabel(new ImageIcon(headerIMG)));
        headerLabel.setBounds(0, 0, size_xL, 163);

        // place Controls
        placePanel.setLayout(null);
        placePanel.setOpaque(true);
        placePanel.setBackground(Color.BLACK);
        placePanel.setBounds(0, 0, 500, 500);

        playerBoardPanel.setLayout(null);
        playerBoardPanel.setOpaque(false);
        playerBoardPanel.add(playerBoard);
        playerBoardPanel.setBounds(0, 0, 500, 500);
        playerBoardPanel.addMouseListener(boardListener);

        cpuBoardPanel.setLayout(null);
        cpuBoardPanel.setOpaque(false);
        cpuBoardPanel.add(cpuBoard);
        cpuBoardPanel.setBounds(505, 173, 500, 500);

        // Directions text
        directionsTxt = new JTextArea("Game directions\n\n" +
                "1. Select a ship from the leftmost box below.\n" +
                "2. Select a direction from the rightmost box below.\n" +
                "3. Select a coordinate for the beginning position of your ship on the grid.\n" +
                "4. Click \"Done\" once all 5 ships are placed.\n");
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
        shipList.setListData(Ship.getShipNames());
        shipList.setSelectedIndex(0);

        // Direction of the ship menu
        directionList.setFont(new Font("Tahoma", Font.PLAIN, 17));
        directionList.setForeground(Color.WHITE);
        directionList.setBackground(Color.DARK_GRAY);
        directionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        directionList.setBounds(292, 192, 115, 115);
        directionList.setListData(Ship.getDirectionNames());
        directionList.setSelectedIndex(0);

        // Done Button
        doneButton = new JButton("Done");
        doneButton.setBounds(200, 400, 100, 50);
        doneButton.setEnabled(false);


        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            startGame();
            }

        });

        // Putting the ship menu next to the player ship board
        placePanel.add(doneButton);
        placePanel.add(directionsTxt);
        placePanel.add(directionList);
        placePanel.add(shipList);

        placePanel.setBounds(505, 173, 500, 500);
        playerBoardPanel.setBounds(5, 173, 500, 500);

        // Frame setup
        placeFrame.add(headerLabel);
        placeFrame.add(placePanel);
        placeFrame.add(playerBoardPanel);
        placeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        placeFrame.setSize(size_xL, size_yL);
        placeFrame.getContentPane().setBackground(Color.BLACK);
        placeFrame.setLayout(null);
        placeFrame.setIconImage(icon);
        placeFrame.setResizable(false);
        placeFrame.setVisible(true);
    }

    public void startGame() {
        //remove setup GUI elements
        placePanel.removeAll();
        placePanel.setLayout(null);
        placePanel.setOpaque(false);
        placePanel.setVisible(false);

        //add CPU game to the frame
        placeFrame.remove(placePanel);
        placeFrame.add(cpuBoardPanel);

        //modify mouse Listeners
        cpuBoardPanel.addMouseListener(gameListener);
        playerBoardPanel.removeMouseListener(boardListener);

        placeFrame.repaint();

        cpu.placeShips();
        player1_turn = true;
    }

    public boolean redrawBoard(Player player, JPanel panel, JLabel board, boolean drawShips) {
        panel.removeAll();
        if(drawShips) {
            for (Ship ship : player.shipFleet) {
                if (ship.isPlaced()) {

                    drawShip(panel, ship);
                }
            }
        }

        for(int x = 1; x <= boardWidth; x++) {
            for(int y = 1; y <= boardWidth; y++) {
                TileColors color = getColorByValue(player.getShipBoardValue(x, y));
                if(!color.equals(TileColors.Blank))
                {
                    if(color.equals(TileColors.Green) && !drawShips) {

                    } else {
                        if(!drawTile(panel, color, x, y)) {
                            return false;
                        }
                    }
                }
            }
        }

        // placing the board after the tiles means it will have a higher Z-index
        // thus it will be drawn first.
        panel.add(board);
        panel.repaint();
        return true;
    }

    /*
     * Adds a square to the board grid at the point given
     *
     * values must be given as coordinates
     * values are 1-indexed
     */
    @SuppressWarnings("Duplicates")
    private boolean drawTile(JPanel panel, TileColors color, int x, int y) {
        if (x <= 10 && y <= 10) {
            JLabel img = (new JLabel(new ImageIcon(color.getTileImage())));
            img.setBounds(x * 45, y * 45, 45, 45);
            panel.add(img);
            return true;
        }
        return false;
    }

    private boolean drawShip(JPanel panel, Ship ship) {
        ImageIcon imageicon = new ImageIcon(ship.getShipType().getImage());
        JLabel img = null;
        if (ship.isPlaced()) {
            if(ship.getDirectionOfShip().equals(Ship.Direction.Horizontal)) {
                imageicon = new ImageIcon(Util.rotate(ship.getShipType().getImage(), -90));
                img = new JLabel(imageicon);
                img.setBounds(ship.getX() * 45, ship.getY() * 45, ship.getSize()*45, 45);
            }
            else if(ship.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
                imageicon = new ImageIcon(ship.getShipType().getImage());
                img = new JLabel(imageicon);
                img.setBounds(ship.getX() * 45, ship.getY() * 45, 45, ship.getSize()*45);
            }

            panel.add(img);
            return true;
        }
        return false;
    }

    // Classes-----------------------------------------------------------------

    // Mouse Listener for Setup
    public class setupMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            int x = Util.getGrid(e.getX());
            int y = Util.getGrid(e.getY());

            Ship selectedShip = new Ship(shipList.getSelectedValue(), directionList.getSelectedValue());
            if(DEBUG) {
                //debug code
                player.addShip(new Ship(Ship.ShipType.AircraftCarrier.getName(), Ship.Direction.Horizontal.getName()), 2, 1);
                player.addShip(new Ship(Ship.ShipType.Battleship.getName(), Ship.Direction.Horizontal.getName()), 2, 3);
                player.addShip(new Ship(Ship.ShipType.Destroyer.getName(), Ship.Direction.Horizontal.getName()), 2, 5);
                player.addShip(new Ship(Ship.ShipType.Submarine.getName(), Ship.Direction.Horizontal.getName()), 2, 7);
                player.addShip(new Ship(Ship.ShipType.PatrolBoat.getName(), Ship.Direction.Horizontal.getName()), 2, 9);
                redrawBoard(player, playerBoardPanel, playerBoard, true);
                if (player.allShipsPlaced()) {
                    doneButton.setEnabled(true);
                }
            } else {
                if (x >= 1 && y >= 1) {
                    // the addShip Function performs all of the necessary board checks itself
                    if (player.addShip(selectedShip, x, y)) {
                        redrawBoard(player, playerBoardPanel, playerBoard, true);
                    }
                    if (player.allShipsPlaced()) {
                        doneButton.setEnabled(true);
                    }
                }
            }
        }

        public void mouseEntered(MouseEvent arg0) {}
        public void mouseExited(MouseEvent arg0) {}
        public void mousePressed(MouseEvent arg0) {}
        public void mouseReleased(MouseEvent arg0) {}
    }

    // Mouse Listener for Naval Warfare
    public class gameMouseListener implements MouseListener {

        private int last_X = 0;
        private int last_Y = 0;
        public void mouseClicked(MouseEvent e) {

            int x = Util.getGrid(e.getX());
            int y = Util.getGrid(e.getY());

            if (x >= 1 && y >= 1 && player1_turn) {
                if(!(last_X == x && last_Y == y)) { //click de-bouncing
                    last_X = x;
                    last_Y = y;
                    player1_turn = false;
                    try {
                        if (player.takeTurn(cpu, x, y)) {
                            redrawBoard(cpu, cpuBoardPanel, cpuBoard, DEBUG);
                            while (!cpu.fire(player)) ;
                            redrawBoard(player, playerBoardPanel, playerBoard, true);

                            if (player.allShipsHit() || cpu.allShipsHit()) {
                                redrawBoard(cpu, cpuBoardPanel, cpuBoard, true);
                                redrawBoard(player, playerBoardPanel, playerBoard, true);
                                if (player.allShipsHit() && cpu.allShipsHit()) {
                                    JOptionPane.showMessageDialog(null, "It's a Draw?!");
                                } else if (player.allShipsHit()) {
                                    JOptionPane.showMessageDialog(null, "Player " + cpu.getName() + " has Won!");
                                } else if (cpu.allShipsHit()) {
                                    JOptionPane.showMessageDialog(null, "Player " + player.getName() + " has Won!");
                                } else {
                                    //wat?
                                }
                                placeFrame.dispose();
                            } else {
                                player1_turn = true;
                            }
                        } else {
                            player1_turn = true;
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        }

        public void mouseEntered(MouseEvent arg0) {}
        public void mouseExited(MouseEvent arg0) {}
        public void mousePressed(MouseEvent arg0) {}
        public void mouseReleased(MouseEvent arg0) {}
    }
}
