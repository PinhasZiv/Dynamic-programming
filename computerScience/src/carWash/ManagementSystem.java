package carWash;
/* Pinhas Ziv 315709139, Alex Chen 312286545 */
public class ManagementSystem {

	public static void main(String[] args) {
		int numOfCars = 10;
		int numOfMachinesA = 1;
		int numOfMachinesB = 2;
		final double averageTimeBetweenArrivalOfCars = 1.5;
		final double averageCarWashTime = 3;
		CarWashComplex complexA = new CarWashComplex('A', numOfMachinesA, numOfCars, averageCarWashTime);
		CarWashComplex complexB = new CarWashComplex('B', numOfMachinesB, numOfCars, averageCarWashTime);
		CarWashComplex complexC = new CarWashComplex('C', 0, numOfCars, 0);

		final long startTime = System.currentTimeMillis();

		while (numOfCars > 0) {
			new Car(complexA, complexB, complexC, startTime).start();
			try {
				Thread.sleep(nextTime(averageTimeBetweenArrivalOfCars));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			numOfCars--;
		}
		while (complexC.getNumOfExitCar() < numOfCars) {
			try {
				Thread.currentThread().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// calculate next time using lambda
	public static long nextTime(double lambda) {
		double rnd = Math.random();
		double timeInSeconds = -(Math.log((rnd) / lambda));
		return (long) (1000 * timeInSeconds);
	}
}
