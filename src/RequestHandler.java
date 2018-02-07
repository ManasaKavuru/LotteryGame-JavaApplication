
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RequestHandler implements Runnable {
	private static AccessCounter accessCounter = new AccessCounter();
	volatile boolean done = false;
	private static int count1;
	static Object filePath;

	public Object select() {

		List<Object> results = new ArrayList<Object>();
		Observer obj1 = new Player1();
		Observer obj2 = new Player2();
		Observer obj3 = new Player3();
		Observer obj4 = new Player4();

		results.add(obj1);
		results.add(obj2);
		results.add(obj3);
		results.add(obj4);

		Random rand = new Random();

		System.out.println(
				"Welcome to the Lottery game..Enter number of players..(Minimum of 2 and maximum of 4 Players are allowed ) ");

		Scanner sc = new Scanner(System.in);
		while (!sc.hasNextInt()) {
			sc.next();
			System.out.println("Please enter Integer(Range 2-4) value only");
		}
		int p = sc.nextInt();

		// System.out.println(p);
		if (p < 0 || p != 2 && p != 3 && p != 4) {
			System.out.println("Invalid entry!!");
			System.exit(0);
			return null;
		} else {
			int r = rand.nextInt(p);

			System.out.println("Spinning the Wheel !!!");

			return results.get(r);
		}
	}

	public void setDone() {

		done = true;
	}

	@Override
	public void run() {
		if (done == true) {
			return;
		}

		filePath = select();

		accessCounter.increment(filePath);
		count1 = accessCounter.getCount(filePath);

		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		accessCounter.setMap();
		new Thread(new RequestHandler()).start();

	}
}