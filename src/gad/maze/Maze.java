package gad.maze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public final class Maze {
	private JFrame frame;
	private Tile[][] maze;
  
    /**
	 * Generates a simple maze
	 */
	public static boolean[][] generateStandardMaze() {
		return generateStandardMaze(11, 11);
	}
  
    /**
	 * Generates a simple maze with the given size
     * 
	 * @param width number of columns in the maze
     * @param height number of rows in the maze
	 */
	public static boolean[][] generateStandardMaze(int width, int height) {
		return generateMaze(width, height, 0);
	}

    /**
	 * Generates a maze with the given size. The seed is used to always create the same maze with the same seed.
	 * 
	 * @param width number of columns in the maze
     * @param height number of rows in the maze
	 * @param seed sets the random number generator to always create the same maze with the same seed
	 */
	public static boolean[][] generateMaze(int width, int height, int seed) {
		Random random = new Random(seed);
		if (width < 3) {
			width = 3;
		}
		if (height < 3) {
			height = 3;
		}
		boolean[][] mazeArray = new boolean[width][height];

		// borders
		for (int x = 0; x < width; x++) {
			mazeArray[x][0] = true;
			mazeArray[x][height - 1] = true;
		}
		for (int y = 0; y < height; y++) {
			mazeArray[0][y] = true;
			mazeArray[width - 1][y] = true;
		}

		// create random obstacles
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (random.nextInt(4) == 0) {
					mazeArray[x][y] = true;
				}
			}
		}

		// entrance and exit
		mazeArray[1][0] = false;
		mazeArray[1][1] = false;
		mazeArray[width - 1][height - 2] = false;
		mazeArray[width - 2][height - 2] = false;

		return mazeArray;
	}

	/**
	 * This method creates and shows the maze GUI
	 * 
	 * @param feld the maze
	 * @param path the path taken
	 */
	public static void draw(boolean[][] feld, StudentResult path) {
		Maze mazeN = new Maze(feld);

		// plot path
		mazeN.plotPath(path);

		// draw
		mazeN.drawField();

		// display
		mazeN.frame.setVisible(true);
		mazeN.frame.setSize(800, 800);
		mazeN.frame.setLocationRelativeTo(null);
	}

	/**
	 * Create the application.
	 * 
	 * @param feld
	 */
	private Maze(boolean[][] feld) {
		initialize();

		translateMazeToField(feld);
		drawField();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Spielfeld");
		frame.setBounds(100, 100, 760, 523);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
	}

	/**
	 * Draws the Field into the JFrame
	 */
	private void drawField() {
		// check to avoid NullPointerException
		if (maze == null) {
			System.err.println("No internal maze found, please call translateMazeToField() first");
			return;
		}

		// too lazy to remove auto-generated code above, it's easier to clean up
		frame.getContentPane().removeAll();
		frame.getContentPane().setLayout(new GridLayout(maze[0].length, maze.length, 0, 0));

		// fill the gridlayout with the tiles
		for (int y = 0; y < maze[0].length; y++) {
			for (int x = 0; x < maze.length; x++) {
				JComponent comp = maze[x][y].getIcon();
				comp.setToolTipText(maze[x][y].toString() + ", " + x + "," + y);
				frame.getContentPane().add(comp);
			}
		}

		// pack and move to center of screen
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Because the internal representation of the maze does not use booleans we need
	 * to translate the boolean array first
	 * 
	 * @param booleanMaze the maze in boolean representation
	 */
	private void translateMazeToField(boolean[][] booleanMaze) {
		this.maze = new Tile[booleanMaze.length][booleanMaze[0].length];
		for (int i = 0; i < booleanMaze.length; i++) {
			for (int j = 0; j < booleanMaze[0].length; j++) {
				this.maze[i][j] = booleanMaze[i][j] ? Tile.WALL : Tile.FREE;
			}
		}
	}

	/**
	 * Plots the path of the walker inside the maze
	 * 
	 * @param path the path taken by the walker
	 */
	private void plotPath(StudentResult path) {
		// Path too short, possible IndexOutOfBoundsException ahead! Abort! Abort!!
		if (path.getPath().isEmpty()) {
			return;
		}

		// check to avoid NullPointerException
		if (maze == null) {
			System.err.println("No internal maze found, please call translateMazeToField() first");
			return;
		}

		for (Point p : path.getPath()) {
			maze[(int) p.getX()][(int) p.getY()] = Tile.PATH;
		}

		// Makes the last point into a Player tile instead of a Path tile
		Point last = path.getPath().get(path.getPath().size() - 1);
		maze[(int) last.getX()][(int) last.getY()] = Tile.PLAYER;

	}

	private enum Tile {
		FREE, WALL, PLAYER, PATH;

		/**
		 * Returns a displayable representation of this enum
		 * 
		 * @return a JComponent
		 */
		public JComponent getIcon() {
			JPanel p = new JPanel();
			int size = 75;
			p.setPreferredSize(new Dimension(size, size));
			switch (this) {
			case FREE -> p.setBackground(Color.WHITE);
			case WALL -> p.setBackground(Color.DARK_GRAY);
			case PLAYER -> p.setBackground(Color.RED);
			case PATH -> p.setBackground(Color.YELLOW);
			default -> {
			}
			}

			return p;
		}

		/**
		 * Better String representation of the Enum, use this instead of enum.name
		 */
		@Override
		public String toString() {
			return switch (this) {
			case FREE -> "Frei";
			case WALL -> "Wand";
			case PLAYER -> "Spieler";
			case PATH -> "Pfad";
			default -> "";
			};
		}
	}
}
