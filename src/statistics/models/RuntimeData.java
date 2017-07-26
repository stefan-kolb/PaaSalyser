package statistics.models;

import java.util.HashMap;
import java.util.Map;

public class RuntimeData {

    private Map<String, Long> profileRuntimesAmount;
    private Map<String, Long> runtimes;

    public RuntimeData() {
	super();
	profileRuntimesAmount = new HashMap<String, Long>();
	runtimes = new HashMap<String, Long>();
    }

    public Map<String, Long> getNumberPerProfile() {
	return profileRuntimesAmount;
    }

    public void addProfileRuntimeAmount(String name, long amount) {
	profileRuntimesAmount.put(name, amount);
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
