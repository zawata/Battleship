package com.SER216.BattleShip;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import static com.SER216.BattleShip.Util.boardWidth;
import static com.SER216.BattleShip.Util.resources;

public class MainGUI {

    // Main window
    private JFrame placeFrame = new JFrame("BattleShip");
    private final static int size_xL = 1010;
    private final static int size_yL = 700;

    // Left board components
    // Right board components


    private JPanel playerPanel = new JPanel();
    private JPanel cpuPanel = new JPanel();

    private MouseListener boardListener = new setupMouseListener();
    private MouseListener gameListener = new gameMouseListener();

    // Ship selection components
    private JList<String> shipList = new JList<>(Ship.getShipNames());
    private JList<String> directionList = new JList<>(Ship.getDirectionNames());
    private JButton doneButton;

    // Files
    private final static File file_1L = new File(resources + "lg_head.jpg");
    private final static File file_green = new File(resources + "green.gif");
    private final static File file_blue = new File(resources + "blue.gif");
    private final static File file_red = new File(resources + "red.gif");
    private final static File file_grey = new File(resources + "grey.gif");
    private final static File file_black = new File(resources + "black.gif");

    public enum TileColors {
        Blank(0, null),       // Nothing
        Green(1, file_green), // Ship
        Blue (2, file_blue ), //
        Red  (3, file_red  ), // Hit
        Grey (4, file_grey ), // Miss
        Black(5, file_black); // Sunk

        private final int value;
        private final File file;
        TileColors(final int value, final File file) { this.value = value; this.file = file; }
        public int getValue() { return value; }
        public File getFile() { return file; }
    }

    public static TileColors getColorbyValue(int value) {
        for (TileColors color : TileColors.values()) {
            if(color.getValue() == value)
                return color;
        }
        return null;
    }

    public Image icon = Toolkit.getDefaultToolkit().getImage(resources + "icon.gif");

    //Player
    Player player;
    CPU    cpu;

    boolean player1_turn = false;

    // Loads the GUI for the game
    public
    void init(Player player1, CPU cpu) throws IOException {

        this.player = player;
        this.cpu = cpu;
        // Loads the board image and sets size
        final JLabel board_img = (new JLabel(new ImageIcon(ImageIO.read(new File(resources + "board.gif")))));
        board_img.setBounds(0, 0, 500, 500);

        // Makes a second board
        final JLabel board_img1 = (new JLabel(new ImageIcon(ImageIO.read(new File(resources + "board.gif")))));
        board_img1.setBounds(0, 0, 500, 500);

        // Loads the board image and sets size
        JLabel img_1L = (new JLabel(new ImageIcon(ImageIO.read(file_1L))));
        img_1L.setBounds(0, 0, size_xL, 163);

        // Makes the CPU sunk ships appear
        cpuPanel.setBounds(0, 0, 500, 500);
        cpuPanel.setLayout(null);
        cpuPanel.setOpaque(false);

        // Makes the CPU shots appear
        cpuPanel.setBounds(0, 0, 500, 500);
        cpuPanel.setLayout(null);
        cpuPanel.setOpaque(false);

        // Panel to make ships appear on top
        playerPanel.setBounds(0, 0, 500, 500);
        playerPanel.setLayout(null);
        playerPanel.setOpaque(false);

        // Ship board setup
        //playerPanel.add(cpuSunkPanel);
        playerPanel.add(cpuPanel);
        playerPanel.add(board_img);
        playerPanel.setLayout(null);
        playerPanel.setOpaque(false);
        playerPanel.setBackground(Color.BLACK);
        playerPanel.setBounds(0, 173, 500, 500);
        playerPanel.addMouseListener(boardListener);

        // Directions text
        JTextArea directionsTxt = new JTextArea("Game directions\n\n");
        directionsTxt.append("1. Select a ship from the leftmost box below.\n");
        directionsTxt.append("2. Select a direction from the rightmost box below.\n");
        directionsTxt.append("3. Select a coordinate for the beginning position of your ship on the grid.\n");
        directionsTxt.append("4. Click \"Done\" once all 5 ships are placed.\n");
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
        /*
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

        }); */

        // Putting the ship menu next to the player ship board
        playerPanel.add(doneButton);
        playerPanel.add(directionsTxt);
        playerPanel.add(directionList);
        playerPanel.add(shipList);
        playerPanel.setLayout(null);
        playerPanel.setOpaque(false);
        playerPanel.setBackground(Color.BLACK);
        playerPanel.setBounds(505, 173, 500, 500);

        // Frame setup
        placeFrame.add(img_1L);
        placeFrame.add(playerPanel);
        placeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        placeFrame.setSize(size_xL, size_yL);
        placeFrame.getContentPane().setBackground(Color.BLACK);
        placeFrame.setLayout(null);
        placeFrame.setIconImage(icon);
        placeFrame.setResizable(false);
        placeFrame.setVisible(true);

    }
/*
    // Looks to see if you can put the ship down
    public boolean freeBoard(Player player, Ship ship, int x, int y){
        if (ship.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
            for (int i = 0; i < ship.getSize(); i++) {
                if(player.getBoardValue(x,(i + y )) == 1){
                    return false;
                }
            }
        }
        else
        {
            if (ship.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
                for (int i = 0; i < (10 - ship.getSize()); i++) {
                    if(player.getBoardValue(( i + x),y) == 1){
                        return false;
                    }
                }
            }
        }
        return true;
    }
*/
    public void redrawBoard(Player player) {
        for(int x = 1; x <= boardWidth; x++) {
            for(int y = 1; y <= boardWidth; y++) {
                TileColors color = getColorbyValue(player.getBoardValue(x, y));
                if(!color.equals(TileColors.Blank))
                {
                    drawPlayerTile(color, x, y);
                }
            }
        }
    }

    public void redrawBoard(CPU cpu) {
        for(int x = 1; x <= boardWidth; x++) {
            for(int y = 1; y <= boardWidth; y++) {
                TileColors color = getColorbyValue(cpu.getBoardValue(x, y));
                if(!color.equals(TileColors.Blank))
                {
                    drawCPUTile(color, x, y);
                }
            }
        }
    }

    /*
     * Adds a square to the board grid at the point given
     *
     * values must be given as coordinates
     * values are 1-indexed
     */

    public boolean drawPlayerTile(TileColors color, int x, int y) {
        // values must be given as coordinates
        try {
            if (x <= 10 && y <= 10) {
                JLabel img_bg = (new JLabel(new ImageIcon(ImageIO.read(color.getFile()))));
                img_bg.setBounds(x * 45, y * 45, 45, 45);
                playerPanel.add(img_bg);
                playerPanel.repaint(); // Updates the image so the boxes appear
                return true;
            }
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, ("Image File " + color.getFile().getPath() + " Not Found."), "Error", JOptionPane.ERROR);
            System.exit(1);
        }
        return false;
    }

    public boolean drawCPUTile(TileColors color, int x, int y) {
        // values must be given as coordinates
        try {
            if (x <= 10 && y <= 10) {
                JLabel img_bg = (new JLabel(new ImageIcon(ImageIO.read(color.getFile()))));
                img_bg.setBounds(x * 45, y * 45, 45, 45);
                cpuPanel.add(img_bg);
                cpuPanel.repaint(); // Updates the image so the boxes appear
                return true;
            }
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, ("Image File " + color.getFile().getPath() + " Not Found."), "Error", JOptionPane.ERROR);
            System.exit(1);
        }
        return false;
    }

    // find if the ship will be off the board
    public boolean validPlace(Ship ship, int x, int y) {
        if(ship.getDirectionOfShip().equals(Ship.Direction.Vertical)) {
            if(y <= ((boardWidth + 1) - ship.getSize())) {
                return true;
            }
        }
        else {
            if(ship.getDirectionOfShip().equals(Ship.Direction.Horizontal)) {
                if(x <= ((boardWidth + 1) - ship.getSize())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Classes-----------------------------------------------------------------

    // Takes values of mouse clicks for the game board.
    public class setupMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            int x = Util.getGrid(e.getX());
            int y = Util.getGrid(e.getY());

            Ship selectedShip = new Ship(shipList.getSelectedValue(), directionList.getSelectedValue());

            if (x >= 1 && y >= 1) {
                //if(freeBoard(player, selectedShip, x, y) && validPlace(selectedShip, x, y)){
                if(validPlace(selectedShip, x, y)){
                    player.addShip(selectedShip, x, y);
                    redrawBoard(player);
                }
                if (player.allShipsPlaced()) {
                    doneButton.setEnabled(true);
                }
            }

        }

        public void mouseEntered(MouseEvent arg0) {}
        public void mouseExited(MouseEvent arg0) {}
        public void mousePressed(MouseEvent arg0) {}
        public void mouseReleased(MouseEvent arg0) {}
    }

    // Takes values to pass to fire
    public class gameMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            int x = Util.getGrid(e.getX());
            int y = Util.getGrid(e.getY());
            if (x >= 1 && y >= 1 && player1_turn) {
                    if (player.validShot(x, y)) {
                        player1_turn = false;
                        //if (!checkWin()) {
                        //    while (!opponent.cpuFire()
                        //        checkWin();
                        //}
                    }
            }
        }

        public void mouseEntered(MouseEvent arg0) {}
        public void mouseExited(MouseEvent arg0) {}
        public void mousePressed(MouseEvent arg0) {}
        public void mouseReleased(MouseEvent arg0) {}
    }
}
