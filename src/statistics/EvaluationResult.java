package statistics;

public class EvaluationResult {

	private final String name;
	private final int number;

	public EvaluationResult(String name, int number) {
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}
}
