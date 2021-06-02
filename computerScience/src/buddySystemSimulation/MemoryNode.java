package buddySystemSimulation;

public class MemoryNode {
	private final int MEMORY_SIZE;
	private int nodeSize;
	private int externalfrag;
	private int internalfrag;
	private int begginNodeIndex;
	private int endNodeIndex;
	private Process process;
	private int maxProcess;
	private MemoryNode leftChild;
	private MemoryNode rightChild;

	public MemoryNode(int mEMORY_SIZE, int nodeSize, int beggin, int end) {
		this.MEMORY_SIZE = mEMORY_SIZE;
		this.nodeSize = nodeSize;
		this.externalfrag = this.nodeSize;
		this.internalfrag = 0;
		this.begginNodeIndex = beggin;
		this.endNodeIndex = end;
		this.process = null;
		this.maxProcess = this.nodeSize;
		this.leftChild = null;
		this.rightChild = null;
	}

	public int getMEMORY_SIZE() {
		return MEMORY_SIZE;
	}

	public int getNodeSize() {
		return nodeSize;
	}

	public void setNodeSize(int nodeSize) {
		this.nodeSize = nodeSize;
	}

	public int getExternalfrag() {
		if (this.isLeaf()) {
			return this.externalfrag;
		}
		return this.leftChild.getExternalfrag() + this.rightChild.getExternalfrag();
	}

	public void setExternalfrag(int external) {
		this.externalfrag = external;
	}

	public int getInternalfrag() {
		if (this.isLeaf()) {
			return this.internalfrag;
		}
		return this.leftChild.getInternalfrag() + this.rightChild.getInternalfrag();

	}

	public void setInternalfrag(int Internal) {
		this.internalfrag = Internal;
	}

	public MemoryNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(MemoryNode leftChild) {
		this.leftChild = leftChild;
	}

	public MemoryNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(MemoryNode rightChild) {
		this.rightChild = rightChild;
	}

	public int getBegginNodeIndex() {
		return begginNodeIndex;
	}

	public void setBegginNodeIndex(int begginNodeIndex) {
		this.begginNodeIndex = begginNodeIndex;
	}

	public int getEndNodeIndex() {
		return endNodeIndex;
	}

	public void setEndNodeIndex(int endNodeIndex) {
		this.endNodeIndex = endNodeIndex;
	}

	public Process getProcess() {
		return process;
	}

	public int getMaxProcess() {
		return this.maxProcess;
	}

	public void setMaxProcess(int maxProcess) {
		this.maxProcess = maxProcess;
	}

	public void setProcess(Process process) {
		this.process = process;
		if (this.process == null) {
			setExternalfrag(this.getNodeSize());
			setInternalfrag(0);
			setMaxProcess(this.getNodeSize());
		} else {
			setExternalfrag(0);
			setInternalfrag(this.getExternalfrag() - process.getSize());
			setMaxProcess(0);
		}
	}

	public boolean isLeaf() {
		return this.leftChild == null && this.rightChild == null;
	}

	public boolean addProcessToNode(Process proc, MemoryNode node) {
		if (proc.getSize() > node.getMaxProcess()) {
			return false;
		}
		if (node.isLeaf()) {
			if (node.getProcess() != null) {
				return false;
			}
			if (proc.getSize() > node.getNodeSize() / 2 && proc.getSize() < node.getNodeSize()) {
				node.process = proc;
				node.process.setBegginIndex(node.begginNodeIndex); // set endIndex of process inside setBegginIndex()
				fixfrag(node);
				return true;
			} else {
				boolean success = node.splitNode(proc, node); // return true if split successfully, otherwise: false
				if (!success) {
					return false;
				}
				while (!node.isLeaf()) {
					node = node.leftChild;
				}
				node.process = proc;
				node.process.setBegginIndex(node.begginNodeIndex); // set endIndex of process inside setBegginIndex()
				fixfrag(node);
				return true;
			}
		} else {
			if (chooseSideToAllocate(proc, node)) {
				return addProcessToNode(proc, node.leftChild);
			} else {
				return addProcessToNode(proc, node.rightChild);
			}
		}
	}

	// return true: if should allocate in leftSide, false: if should allocate in
	// rightSide.
	public static boolean chooseSideToAllocate(Process proc, MemoryNode node) {
		if(node.rightChild.getMaxProcess() < proc.getSize()) {
			return true;
		}
		if(node.leftChild.getMaxProcess() - proc.getSize() <= node.rightChild.getMaxProcess() - proc.getSize()
				&& node.leftChild.getMaxProcess() >= proc.getSize()) {
			return true;
		}
		return false;
	}

	public void addProcess(Process proc, MemoryNode node) {
		if (node.getNodeSize() < proc.getSize()) {

		}
	}

	private void fixfrag(MemoryNode changeNode) {
		if (!this.isLeaf()) {
			if (changeNode.endNodeIndex <= this.leftChild.endNodeIndex) {
				this.leftChild.fixfrag(changeNode);
				setExternalfrag(this.leftChild.getExternalfrag() + this.rightChild.getExternalfrag());
				setInternalfrag(this.leftChild.getInternalfrag() + this.rightChild.getInternalfrag());
				setMaxProcess(Math.max(this.leftChild.getExternalfrag(), this.rightChild.getExternalfrag()));
			} else {
				this.rightChild.fixfrag(changeNode);
				setExternalfrag(this.rightChild.getExternalfrag() + this.leftChild.getExternalfrag());
				setInternalfrag(rightChild.getInternalfrag() + this.leftChild.getInternalfrag());
				setMaxProcess(Math.max(this.leftChild.getExternalfrag(), this.rightChild.getExternalfrag()));
			}
		} else {
			if (this.process == null) {
				setExternalfrag(this.nodeSize);
				setInternalfrag(0);
			} else {
				setExternalfrag(0);
				setInternalfrag(this.nodeSize - this.process.getSize());
				setMaxProcess(0);
			}
		}
	}

	private boolean splitNode(Process proc, MemoryNode node) {
		if (node.getNodeSize() == 1) {
			return false;
		}
		if (node.getNodeSize() / 2 >= proc.getSize()) {
			int newSize = node.getNodeSize() / 2;
			node.setLeftChild(new MemoryNode(node.MEMORY_SIZE, newSize, node.getBegginNodeIndex(),
					node.getBegginNodeIndex() + newSize - 1));
			node.setRightChild(new MemoryNode(node.MEMORY_SIZE, newSize, node.getBegginNodeIndex() + newSize,
					node.getEndNodeIndex()));
			return splitNode(proc, node.leftChild);
		} else {
			return true;
		}
	}

	public static void recursiveMerge(MemoryNode beggin, MemoryNode removeNode) {
		boolean isChange = true;
		while (isChange) {
			isChange = merge(beggin, removeNode);
		}
	}

	public static boolean merge(MemoryNode node, MemoryNode removeNode) {
		if (!node.isLeaf()) {
			if (node.leftChild.getExternalfrag() == node.leftChild.getNodeSize()
					&& node.rightChild.getExternalfrag() == node.rightChild.getNodeSize()) {
				mergeNode(node);
				return true;
			} else {
				boolean change = merge(node.leftChild, removeNode) || merge(node.rightChild, removeNode);
				node.fixfrag(removeNode);
				return change;
			}
		}
		return false;
	}

	private static void mergeNode(MemoryNode node) {
		node.setExternalfrag(node.getNodeSize());
		node.setInternalfrag(0);
		node.setLeftChild(null);
		node.setRightChild(null);
	}
}
