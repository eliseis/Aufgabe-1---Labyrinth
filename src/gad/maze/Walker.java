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
	int x = 1;
	int y = 0;
	int dir = 0;


	public boolean walk() {
		result.addLocation(1,0);
		if (!maze[1][1]){
			result.addLocation(1,1);
		}
		else{
			return false;
		}
		while ((x != maze.length - 1) && (y != maze[0].length - 2) || (x != 1) && (y != 0) ) {
			switch (dir % 4) {
				case 0:
					rx = x - 1;
					ry = y;
					fx = x;
					fy = y + 1;
					break;
				case 1:
					rx = x;
					ry = y + 1;
					fx = x + 1;
					fy = y;
					break;
				case 2:
					rx = x + 1;
					ry = y;
					fx = x;
					fy = y - 1;
					break;
				case 3:
					rx = x;
					ry = y - 1;
					fx = x - 1;
					fy = y;
					break;
			}
			if (!maze[rx][ry]) {
				result.addLocation(rx, ry);
				x = rx;
				y = ry;
				dir--;
			} else {
				if (!maze[fx][fy]) {
					result.addLocation(fx, fy);
					x = fx;
					y = fy;
				} else {
					dir++;
				}
			}
		}
		if(x == 0){
			return false;
		}
		else {
			return true;
		}
		//return alg(1,1, 0);
	}

	/*public boolean alg (int a, int b, int dir){
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

	}*/

	public static void main(String[] args) {
		boolean[][] maze = Maze.generateStandardMaze(9999999, 100);
		StudentResult result = new StudentResult();
		Walker walker = new Walker(maze, result);
		System.out.println(walker.walk());

	}
}
