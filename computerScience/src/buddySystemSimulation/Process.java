package buddySystemSimulation;

public class Process {
	
	private final int name;
	private int size;
	private int begginIndex;
	private int endIndex;
	
	public Process(int size, int processNumber) {
		this.name = processNumber;
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getName() {
		return name;
	}

	public int getBegginIndex() {
		return begginIndex;
	}

	public void setBegginIndex(int begginIndex) {
		this.begginIndex = begginIndex;
		setEndIndex(begginIndex);
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int beggin) {
		this.endIndex = this.begginIndex + this.size - 1;
	}

	@Override
	public int hashCode() {
		return this.name;
	}
	
}
