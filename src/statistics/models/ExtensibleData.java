package statistics.models;

public class ExtensibleData {

    private long yes = 0;
    private long no = 0;

    public long getYes() {
	return yes;
    }

    public void incrementYes() {
	yes++;
    }

    public long getNo() {
	return no;
    }

    public void incrementNo() {
	no++;
    }

}
