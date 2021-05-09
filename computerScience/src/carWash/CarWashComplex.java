package carWash;
/* Pinhas Ziv 315709139, Alex Chen 312286545 */
import java.util.concurrent.Semaphore;

public class CarWashComplex {

	private char complexName;
	private Semaphore numOfWashingMachines;
	private int numOfCarInComplex = 0;
	private int numOfExitCar = 0;
	private double averageCarWashTime;
	private int numOfCars;

	enum Message {
		complexEntery, washEntery, washFinish, carExit
	}

	public CarWashComplex(char name, int machinesNum, int numOfCars, double averageCarWashTime) {
		this.complexName = name;
		this.numOfWashingMachines = new Semaphore(machinesNum, true); // semaphore with fairness
		this.averageCarWashTime = averageCarWashTime;
		this.numOfCars = numOfCars; // the number of cars throughout the life of the program
	}

	public synchronized Semaphore getNumOfWashingMachines() {
		return numOfWashingMachines;
	}

	public synchronized int getNumOfCarInComplex() {
		return numOfCarInComplex;
	}

	public synchronized void setNumOfCarInComplex(int numOfCarInComplex) {
		this.numOfCarInComplex = numOfCarInComplex;
	}

	public synchronized char getComplexName() {
		return complexName;
	}

	public synchronized void setComplexName(char complexName) {
		this.complexName = complexName;
	}

	public synchronized int getNumOfExitCar() {
		return numOfExitCar;
	}

	public synchronized void setNumOfExitCar(int numOfExitCar) {
		this.numOfExitCar = numOfExitCar;
	}

	public synchronized int getNumOfCars() {
		return numOfCars;
	}

	public synchronized void setNumOfCars(int numOfCars) {
		this.numOfCars = numOfCars;
	}

	// car enter into complex
	public synchronized void carEntry(Car car, char complex) {
		setNumOfCarInComplex(getNumOfCarInComplex() + 1);
		car.setCurrComplex(complex);
		Car.printMessage(Message.complexEntery, car);
		if (this.getComplexName() == 'C') { // Ensuring that all cars left complex A
			if (car.canExitC()) {
				notifyAll();
				carExit(car);
			} else {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				carExit(car);
			}
		}
	}

	// car enter into washing machine
	public synchronized void washCar(Car car) {
		try {
			numOfWashingMachines.acquire();
			Car.printMessage(Message.washEntery, car);
			Thread.sleep(ManagementSystem.nextTime(averageCarWashTime));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Car.printMessage(Message.washFinish, car);
		numOfWashingMachines.release();
		carExit(car);
	}

	// car exit complex
	public void carExit(Car car) {
		setNumOfCarInComplex(getNumOfCarInComplex() - 1);
		setNumOfExitCar(getNumOfExitCar() + 1);
		if (this.getComplexName() == 'C') { // print message if car left last complex (C)
			Car.printMessage(Message.carExit, car);
			Thread.interrupted();
		}
	}
}
