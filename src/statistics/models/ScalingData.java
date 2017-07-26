package statistics.models;

public class ScalingData {

	private long horizontal = 0;
	private long vertical = 0;
	private long auto = 0;

	public long getHorizontal() {
		return horizontal;
	}

	public void incrementHorizontal() {
		horizontal++;
	}

	public long getVertical() {
		return vertical;
	}

	public void incrementVertical() {
		vertical++;
	}

	public long getAuto() {
		return auto;
	}

	public void incrementAuto() {
		auto++;
	}

}
