package gad.maze;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class StudentResult implements Result {
	private List<Point> path = new ArrayList<>();

	@Override
	public void addLocation(int x, int y) {
		System.out.println("Koordinate (" + x + ", " + y + ") hinzugefügt");
		path.add(new Point(x, y));
	}

	public List<Point> getPath() {
		return path;
	}
}