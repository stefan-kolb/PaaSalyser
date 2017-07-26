package statistics.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuntimeData {

	private List<Long> profileRuntimesAmount;
	private Map<String, Long> runtimes = new HashMap<String, Long>();

	public List<Long> getNumberPerProfile() {
		return profileRuntimesAmount;
	}

	public void addRuntimeAmount(long amount) {
		profileRuntimesAmount.add(amount);
	}

	public Map<String, Long> getRuntimes() {
		return runtimes;
	}

	public void addRuntime(String runtime) {
		if (runtimes.putIfAbsent(runtime, (long) 1) != null) {
			runtimes.replace(runtime, runtimes.get(runtime) + 1);
		}
	}

}
