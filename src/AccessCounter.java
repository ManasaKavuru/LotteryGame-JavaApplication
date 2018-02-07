import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import java.util.concurrent.locks.ReentrantLock;

public class AccessCounter {
	public static ConcurrentHashMap<Class, Integer> counter = new ConcurrentHashMap<Class, Integer>();
	private ReentrantLock lock = new ReentrantLock();
	Object obj;
	private RequestHandler requestHandler = new RequestHandler();
	Vector<Object> vobj = new Vector<Object>();
	Observable observable = new Observable(vobj);

	Observer obj1 = new Player1();
	Observer obj2 = new Player2();
	Observer obj3 = new Player3();
	Observer obj4 = new Player4();

	public void setMap() {
		counter.put(obj1.getClass(), 0);
		counter.put(obj2.getClass(), 0);
		counter.put(obj3.getClass(), 0);
		counter.put(obj4.getClass(), 0);
	}

	public void increment(Object filePath) {

		Object k = filePath;
		lock.lock();

		requestHandler.setDone();

		try {
			if (counter.keySet().contains(k.getClass())) {

				for (Class key : counter.keySet()) {

					String key1 = key.toString();
					if ((key1).equals(k.getClass().toString())) {

						// System.out.println("EQUALS");
						Integer count = counter.get(key);
						count++;
						counter.put(key, count);

						// System.out.println("Notifying...");
						vobj.add(obj1);
						vobj.add(obj2);
						vobj.add(obj3);
						vobj.add(obj4);
						observable = new Observable(vobj);

						observable.addObserver(obj1);
						observable.addObserver(obj2);
						observable.addObserver(obj3);
						observable.addObserver(obj4);
						observable.setChanged();

						observable.notifyObservers(key1);
						System.out.println("'" + k.getClass().toString() + "' Congratulations!! \n You won the game.. ");

					}
				}
			} else {
				// System.out.println("NOT EQUALS");
				System.out.println("obj.getClass():" + obj.getClass());
				counter.put(obj.getClass(), 1);
			}
		} finally {
			lock.unlock();

		}

	}

	public int getCount(Object filePath) {
		Object k = filePath.getClass();
		lock.lock();

		try {
			if (counter.keySet().contains(k)) {

				Integer count = counter.get(k);
				return count;
			} else {
				return 0;
			}

		} finally {
			lock.unlock();

		}
	}
}