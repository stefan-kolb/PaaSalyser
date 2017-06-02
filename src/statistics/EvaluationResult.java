package statistics;

public class EvaluationResult {

	private final String name;
	private final long number;

	public EvaluationResult(String name, long number) {
		this.name = name;
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public long getNumber() {
		return number;
	}
}
