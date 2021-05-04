package carWash;

import carWash.CarWashComplex.Message;

public class Car extends Thread {

	private static int ID = 0;
	private int carID;
	private CarWashComplex complexA, complexB, complexC;
	private char currComplex;
	private long startTime;

	public Car(CarWashComplex A, CarWashComplex B, CarWashComplex C, long startTime) {
		ID++;
		this.carID = ID;
		this.complexA = A;
		this.complexB = B;
		this.complexC = C;
		this.startTime = startTime;
	}

	@Override
	public void run() {
		while(!interrupted()) {
			complexA.carEntry(this, 'A');
			complexA.washCar(this);
			complexB.carEntry(this, 'B');
			complexB.washCar(this);
			complexC.carEntry(this, 'C');
			break;
		}
	}

	public synchronized int getCarID() {
		return carID;
	}

	public synchronized void setCarID(int carID) {
		this.carID = carID;
	}

	public synchronized char getCurrComplex() {
		return currComplex;
	}

	public synchronized void setCurrComplex(char currComplex) {
		this.currComplex = currComplex;
	}

	public long getStartTime() {
		return startTime;
	}

	public boolean canExitC() {
		if(this.complexA.getNumOfExitCar() == this.complexA.getNumOfCars()) {
			return true;
		}
		return false;
	}
	
	public static synchronized void printMessage(Message message, Car car) {

		long currTime = System.currentTimeMillis();

		System.out.println("Time from beggining: " + (currTime - car.getStartTime()));
		switch (message) {
		case complexEntery:
			System.out.println("Car enter into complex " + car.currComplex);
			break;
		case washEntery:
			System.out.println("Car enter into wash machine");
			break;
		case washFinish:
			System.out.println("Car finished washing");
			break;
		case carExit:
			System.out.println("Car exit from the Car Washing Complex!");
			break;
		}
		System.out.println("Car ID: " + car.getCarID());
		System.out.println();
	}
	
}
