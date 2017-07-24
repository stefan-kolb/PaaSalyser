package statistics.models;

import java.util.HashMap;
import java.util.Map;

public class RevisionData {
	private Map<String, Long> revision = new HashMap<String, Long>();

	public Map<String, Long> getRevisions() {
		return revision;
	}

	public void addRevision(String key, long value) {
		revision.put(key, value);
	}
}
