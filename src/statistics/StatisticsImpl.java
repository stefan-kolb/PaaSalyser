package statistics;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class StatisticsImpl implements Statistics {

	@Override
	public Map<String, String> evalRevision(Map<String, Double> preprocessedData) {
		Map<String, String> results = new HashMap<String, String>();
		List<Double> values = new LinkedList<Double>();

		preprocessedData.keySet().forEach(key -> {
			if (key.startsWith("revision")) {
				values.add(preprocessedData.get(key));
			}
		});

		return results;
	}

	@Override
	public Map<String, String> evalStatus(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalStatusSince(Map<String, Double> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalType(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalQos(Map<String, Double> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalPlatform(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalHosting(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalPricing(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalScaling(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalRuntimes(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalMiddleware(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalFrameworks(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalServices(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalExtensible(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> evalInfrastructures(Map<String, Long> preprocessedData) {
		// TODO Auto-generated method stub
		return null;
	}

}
