import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public class Observable {
	private static ArrayList<Observer> observers = new ArrayList<Observer>();

	public Vector<Object> obs;

	private boolean changed = false;
	public static Observer obr;
	private static ReentrantLock lockObs = new ReentrantLock();

	public Observable(Vector<Object> obs) {
		super();
		this.obs = obs;
	}

	public Observable() {
		super();
	}

	public void addObserver(Observer o) {
		lockObs.lock();
		try {
			observers.add(o);
			hasChanged();
		} finally {
			lockObs.unlock();
		}

	}

	public void deleteObserver(Observer o) {
		lockObs.lock();
		try {
			observers.remove(o);
			hasChanged();
		} finally {
			lockObs.unlock();
		}
	}

	protected void setChanged() {
		lockObs.lock();
		try {
			changed = true;
		} finally {
			lockObs.unlock();
		}

	}

	protected void clearChanged() {
		lockObs.lock();
		try {
			changed = false;
		} finally {
			lockObs.unlock();
		}

	}

	public boolean hasChanged() {
		return true;

	}

	public void notifyObservers(Object arg) {
		Object[] arrLocal;
		lockObs.lock();
		try {
			if (!changed)
				return; // balking
			arrLocal = obs.toArray(); // observers copied to arrLocal
			changed = false;
		} finally {
			lockObs.unlock();
		}
		for (int i = arrLocal.length - 1; i >= 0; i--) {
			((Observer) arrLocal[i]).update(this, arg);
		} // OPEN CALL

		System.out.println("The Winner is...");

	}

}
