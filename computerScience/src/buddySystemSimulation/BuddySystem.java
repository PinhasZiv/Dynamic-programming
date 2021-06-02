package buddySystemSimulation;

import java.util.HashMap;
import java.util.Scanner;

public class BuddySystem {

	private static Scanner sc = new Scanner(System.in);
	private int size;
	private MemoryNode memory;
	private HashMap<Integer, Integer> processesMap; // from processName to begginIndex.

	public static void main(String[] args) {
		BuddySystem bs = new BuddySystem();
		System.out.println();
		int userChoice;
		do {
			do {
				printMenu();
				userChoice = sc.nextInt();
			} while (userChoice > 4 || userChoice < 1);

			switch (userChoice) {
			case 1:
				bs.case1();
				break;
			case 2:
				bs.case2();
				break;
			case 3:
				bs.case3();
				break;
			}
		} while (userChoice != 4);
		sc.close();
	}

	public BuddySystem() {
		getMemorySize();
		this.memory = new MemoryNode(this.size, this.size, 0, this.size - 1);
		this.processesMap = new HashMap<>();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public MemoryNode getMemory() {
		return memory;
	}

	public void getMemorySize() {
		int size = 0;
		do {
			System.out.println(
					"Please enter the size of memory, in bytes:\n" + "(The size should be an Integer of power 2)");
			size = sc.nextInt();
		} while (!isPowerOfTwo(size));
		this.size = size;
	}

	public static boolean isPowerOfTwo(int n) {
		return ((n & n - 1) == 0 && n != 0);
	}

	public static void printMenu() {
		System.out.println(
				"1. Enter process\n" + "2. Exit process\n" + "3. Print status\n" + "4. Exit\n" + "Enter your choice:\n");
	}

	public boolean addProcess(Process proc) {
		return this.memory.addProcessToNode(proc, this.memory);
	}

	public void case1() {
		boolean validInput = false;
		int id = 0;
		do {
			System.out.println("Enter process ID: (has to be positive number) ");
			id = sc.nextInt();
			if(processesMap.containsKey(id)) {
				System.out.println("There is a process in the system that contains the same id");
			} else if(id < 1) {
				System.out.println("ID nust be a positive number");
			} else {
				validInput = true;
			}
		} while (!validInput);
		System.out.println("Enter size of process: ");
		int size = sc.nextInt();
		Process proc = new Process(size, id);
		boolean success = addProcess(proc);
		if (success) {
			this.processesMap.put(proc.getName(), proc.getBegginIndex());
			System.out.println("Memory allocated for the process " + proc.getName());
		} else {
			System.out.println("can't allocate process. external fragmentation: " + this.memory.getExternalfrag());
		}
	}

	public void case2() {
		int beggin = 0;
		System.out.println("Enter process number to remove: ");
		int remove = sc.nextInt();
		try {
			beggin = this.processesMap.remove(remove);
		} catch (NullPointerException e) {
			System.out.println("The process is not in system memory");
			return;
		}
		MemoryNode processNode = findProcessNode(beggin, this.memory);
		processNode.setProcess(null); // also update external & internal Frag.
		MemoryNode.recursiveMerge(this.memory, processNode);
		System.out.println("The process was successfully removed from memory");
	}

	public void case3() {
		System.out.println("Processes in running: ");
		printRunningProcesses(this.memory);
		System.out.println();
		System.out.println("Available blocks: ");
		printEmptyNodes(this.memory);
		System.out.println();
		System.out.println("Internal Fragmantation: " + this.memory.getInternalfrag());
		System.out.println();
	}

	public MemoryNode findProcessNode(int beggin, MemoryNode node) {
		if (node.isLeaf()) {
			return node;
		}
		if (beggin <= node.getLeftChild().getEndNodeIndex()) {
			return findProcessNode(beggin, node.getLeftChild());
		} else {
			return findProcessNode(beggin, node.getRightChild());
		}
	}

	public void printRunningProcesses(MemoryNode node) {
		if (node.isLeaf()) {
			if (node.getProcess() != null) {
				System.out.println("Process " + node.getProcess().getName() + " is stored in block "
						+ node.getBegginNodeIndex() + "-" + node.getEndNodeIndex());
			}
		} else {
			printRunningProcesses(node.getLeftChild());
			printRunningProcesses(node.getRightChild());
		}
	}

	public void printEmptyNodes(MemoryNode node) {
		if (node.isLeaf()) {
			if (node.getProcess() == null) {
				System.out.println("block: " + node.getBegginNodeIndex() + "-" + node.getEndNodeIndex());
			}
		} else {
			printEmptyNodes(node.getLeftChild());
			printEmptyNodes(node.getRightChild());
		}
	}

}
