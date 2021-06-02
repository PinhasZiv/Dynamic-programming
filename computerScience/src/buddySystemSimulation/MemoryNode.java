package buddySystemSimulation;

public class MemoryNode {
	private final int MEMORY_SIZE;
	private int nodeSize;
	private int externalFreg;
	private int internalFreg;
	private int begginNodeIndex;
	private int endNodeIndex;
	private Process process;
	private int maxProcess;
	private MemoryNode leftChild;
	private MemoryNode rightChild;

	public MemoryNode(int mEMORY_SIZE, int nodeSize, int beggin, int end) {
		this.MEMORY_SIZE = mEMORY_SIZE;
		this.nodeSize = nodeSize;
		this.externalFreg = this.nodeSize;
		this.internalFreg = 0;
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

	public int getExternalFreg() {
		if (this.isLeaf()) {
			return this.externalFreg;
		}
		return this.leftChild.getExternalFreg() + this.rightChild.getExternalFreg();
	}

	public void setExternalFreg(int external) {
		this.externalFreg = external;
	}

	public int getInternalFreg() {
		if (this.isLeaf()) {
			return this.internalFreg;
		}
		return this.leftChild.getInternalFreg() + this.rightChild.getInternalFreg();

	}

	public void setInternalFreg(int Internal) {
		this.internalFreg = Internal;
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
			setExternalFreg(this.getNodeSize());
			setInternalFreg(0);
			setMaxProcess(this.getNodeSize());
		} else {
			setExternalFreg(0);
			setInternalFreg(this.getExternalFreg() - process.getSize());
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
				fixFreg(node);
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
				fixFreg(node);
				return true;
			}
		} else {
			if (chooseSideToAllocate(proc)) {
				return addProcessToNode(proc, node.getLeftChild());
			} else {
				return addProcessToNode(proc, node.rightChild);
			}
//			if (node.leftChild.getMaxProcess() >= proc.getSize()) {
//				return addProcessToNode(proc, node.getLeftChild());
//			}
//			if (node.rightChild.getMaxProcess() >= proc.getSize()) {
//				return addProcessToNode(proc, node.rightChild);
//			} else {
//				return false;
//			}
		}
	}

	// return true: if should allocate in leftSide, false: if should allocate in
	// rightSide.
	public boolean chooseSideToAllocate(Process proc) {
		if (this.leftChild.getMaxProcess() - proc.getSize() <= this.rightChild.getMaxProcess() - proc.getSize() && this.leftChild.getExternalFreg() > proc.getSize()) {
			return true;
		}
		return false;
	}

	public void addProcess(Process proc, MemoryNode node) {
		if (node.getNodeSize() < proc.getSize()) {

		}
	}

	private void fixFreg(MemoryNode changeNode) {
		if (!this.isLeaf()) {
			if (changeNode.endNodeIndex <= this.leftChild.endNodeIndex) {
				this.leftChild.fixFreg(changeNode);
				setExternalFreg(this.leftChild.getExternalFreg() + this.rightChild.getExternalFreg());
				setInternalFreg(this.leftChild.getInternalFreg() + this.rightChild.getInternalFreg());
				setMaxProcess(Math.max(this.leftChild.getExternalFreg(), this.rightChild.getExternalFreg()));
			} else {
				this.rightChild.fixFreg(changeNode);
				setExternalFreg(this.rightChild.getExternalFreg() + this.leftChild.getExternalFreg());
				setInternalFreg(rightChild.getInternalFreg() + this.leftChild.getInternalFreg());
				setMaxProcess(Math.max(this.leftChild.getExternalFreg(), this.rightChild.getExternalFreg()));
			}
		} else {
			if (this.process == null) {
				setExternalFreg(this.nodeSize);
				setInternalFreg(0);
			} else {
				setExternalFreg(0);
				setInternalFreg(this.nodeSize - this.process.getSize());
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
			if (node.leftChild.getExternalFreg() == node.leftChild.getNodeSize()
					&& node.rightChild.getExternalFreg() == node.rightChild.getNodeSize()) {
				mergeNode(node);
				return true;
			} else {
				boolean change = merge(node.leftChild, removeNode) || merge(node.rightChild, removeNode);
				node.fixFreg(removeNode);
				return change;
			}
		}
		return false;
	}

	private static void mergeNode(MemoryNode node) {
		node.setExternalFreg(node.getNodeSize());
		node.setInternalFreg(0);
		node.setLeftChild(null);
		node.setRightChild(null);
	}
}
