package statistics.models;

public class SimpleResult {

	private String key;
	private long value;

	public SimpleResult(String key, long value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public long getValue() {
		return value;
	}

	public void incrementValue() {
		value++;
	}

}
