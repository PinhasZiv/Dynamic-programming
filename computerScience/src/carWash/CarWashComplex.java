package carWash;

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
		this.numOfWashingMachines = new Semaphore(machinesNum, true);
		this.averageCarWashTime = averageCarWashTime;
		this.numOfCars = numOfCars;
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

	public synchronized void carEntry(Car car, char complex) {
		setNumOfCarInComplex(getNumOfCarInComplex() + 1);
		car.setCurrComplex(complex);
		Car.printMessage(Message.complexEntery, car);
		if (this.getComplexName() == 'C') {
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

	public void carExit(Car car) {
		setNumOfCarInComplex(getNumOfCarInComplex() - 1);
		setNumOfExitCar(getNumOfExitCar() + 1);
		if (this.getComplexName() == 'C') {
			Car.printMessage(Message.carExit, car);
			Thread.interrupted();
		}
	}
}
