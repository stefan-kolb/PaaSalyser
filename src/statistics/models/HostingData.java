package statistics.models;

public class HostingData {

	private long _private;
	private long _public;
	private long virtual_private;

	public long getPrivate() {
		return _private;
	}

	public void incrementPrivate() {
		_private++;
	}

	public long getPublic() {
		return _public;
	}

	public void incrementPublic() {
		_public++;
	}

	public long getVirtualPrivate() {
		return virtual_private;
	}

	public void incrementVirtualPrivate() {
		virtual_private++;
	}

}
