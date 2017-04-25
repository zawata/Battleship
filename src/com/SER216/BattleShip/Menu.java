package com.SER216.BattleShip;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import static com.SER216.BattleShip.Util.resources;

public class Menu {
	// Variables
	boolean multi = true;
	private JFrame mainFrame = new JFrame("BattleShip");
	private JFrame creditsFrame = new JFrame("BattleShip");
	private JPanel mainPanel = new JPanel();
	final static int size_x = 350;
	final static int size_y = 433;
	final static File file_1 = new File(resources + "title_menu.gif");
	final static File file_2 = new File(resources + "credits.jpg");
	public Image icon = Toolkit.getDefaultToolkit().getImage(resources + "icon.gif");

	// Creates the main menu
	Menu() throws IOException {
		windowGUI(mainFrame, mainPanel);
		mainButtons();
	}

	// Makes the window the Menu appears in
	private void windowGUI(JFrame inFrame, JPanel inPanel) throws IOException {
		String userDir = System.getProperty("user.dir");
		System.out.println(userDir);

		if (multi) {
			centreWindow(inFrame);
			inFrame.setSize(size_x, size_y);
			inFrame.getContentPane().setBackground(Color.BLACK);
			inFrame.setLayout(new GridLayout(2, 1));
			inFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			inPanel.setLayout(new GridLayout(3, 1));
			inPanel.setOpaque(true);
			inPanel.setBackground(Color.BLACK);
			inFrame.add((new JLabel(new ImageIcon(ImageIO.read(file_1)))), BorderLayout.NORTH);
			inFrame.add(inPanel, BorderLayout.SOUTH);
			inFrame.setResizable(false);
			Image icon = Toolkit.getDefaultToolkit().getImage(resources + "icon.gif");
			inFrame.setIconImage(icon);
			inFrame.setVisible(true);
		}
	}

	private void mainButtons() throws IOException {

		JButton playButton = new JButton("Single player");
		JButton exitButton = new JButton("Exit");
		JButton creditsButton = new JButton("Credits");

		// Button actions
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new SinglePlayerGame();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		creditsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					credits();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		mainPanel.add(playButton);
		mainPanel.add(creditsButton);
		mainPanel.add(exitButton);

		mainFrame.pack();
		mainFrame.setIconImage(icon);
		mainFrame.setVisible(true);
	}

	public void credits() throws IOException {
		creditsFrame = new JFrame("Credits");
		centreWindow(creditsFrame);
		creditsFrame.setSize(size_x, size_y);
		creditsFrame.setBackground(Color.BLACK);
		creditsFrame.setLayout(new GridLayout(1, 1));
		creditsFrame.add((new JLabel(new ImageIcon(ImageIO.read(file_2)))));
		creditsFrame.setResizable(false);
		creditsFrame.setIconImage(icon);
		creditsFrame.setVisible(true);
	}

	public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() / 2) - (size_x / 2));
		int y = (int) ((dimension.getHeight() / 2) - (size_y / 2));
		frame.setLocation(x, y);
	}

}