package report.models;

public class EvaluationResult {
	
	private String name;
	private long value;
	
	public EvaluationResult(String name, long value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public long getValue() {
		return value;
	}

}
