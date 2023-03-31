package gad.maze;

public class Walker {

	public Walker(boolean[][] maze, Result result) {

	}

	public boolean walk() {
		return false;
	}

	public static void main(String[] args) {
		boolean[][] maze = Maze.generateStandardMaze(10, 10);
		StudentResult result = new StudentResult();
		Walker walker = new Walker(maze, result);
		System.out.println(walker.walk());
		Maze.draw(maze, result);
	}
}
