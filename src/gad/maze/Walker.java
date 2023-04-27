package gad.maze;

public class Walker {
	private boolean[][] maze;
	private Result result;

	public Walker(boolean[][] maze, Result result) {
		this.maze = maze;
		this.result = result;

	}

	int rx = 0;
	int ry = 0;
	int fx = 0;
	int fy = 0;


	public boolean walk() {
		result.addLocation(1,0);
		result.addLocation(1,1);

		return alg(1,1, 0);
	}

	public boolean alg (int a, int b, int dir){
		if (dir < 0 ){
			dir = dir + 4;
		}
		if ((a == maze.length - 1) && (b == maze[0].length - 2)) {
			return true;

		} else if ((a == 1) && (b == 0)) {
			return false;

		}
		switch (dir % 4){
			case 0:
				rx = a - 1;
				ry = b;
				fx = a;
				fy = b + 1;
				break;
			case 1:
				rx = a;
				ry = b + 1;
				fx = a + 1;
				fy = b;
				break;
			case 2:
				rx = a + 1;
				ry = b;
				fx = a;
				fy = b - 1;
				break;
			case 3:
				rx = a;
				ry = b - 1;
				fx = a - 1;
				fy = b;
				break;
		}
		if (!maze[rx][ry]){
			result.addLocation(rx,ry);
			return alg(rx,ry, dir - 1);
		} else{
			if (!maze[fx][fy]){
				result.addLocation(fx,fy);
				return alg(fx,fy, dir);
			} else{
				return alg(a, b, dir + 1);
			}
		}

	}

	public static void main(String[] args) {
		boolean[][] maze = Maze.generateStandardMaze(1000, 10000);
		StudentResult result = new StudentResult();
		Walker walker = new Walker(maze, result);
		System.out.println(walker.walk());
	}
}
