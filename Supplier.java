import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class Supplier extends Person {
	private static Supplier m_instance;

	private Supplier(Semaphore tobacco, Semaphore paper, Semaphore matches) {
		m_tobacco = new Semaphore(0, true);
		m_paper = new Semaphore(0, true);
		m_matches = new Semaphore(0, true);
	}

	//created a singleton of supplier seemed to make sense to only call a single supplier
	public static Supplier getInstance() {
		if (m_instance == null)
			m_instance = new Supplier(new Semaphore(0, true), new Semaphore(0, true), new Semaphore(0, true));
		return m_instance;
	}

	//releases a single permit for the argument passed
	synchronized public void placeProduct(String product) throws InterruptedException {
		System.out.println(product + "placed");
		Thread.sleep(3000);
		switch (product) {
		case "tobacco":
			m_tobacco.release();
			break;
		case "paper":
			m_paper.release();
			break;
		case "matches":
			m_matches.release();
			break;
		}

	}
	
	//clears all outstanding permits
	synchronized public void clearTable() {
		
		System.out.println("table cleared!");
		m_tobacco.drainPermits();
		m_matches.drainPermits();
		m_paper.drainPermits();
	}
	
	public Semaphore getTobacco() {

		return m_tobacco;
	}

	public Semaphore getPaper() {
		return m_paper;
	}

	public Semaphore getMatches() {
		return m_matches;
	}

	@Override
	public Boolean call() throws Exception {
		Random rand = new Random();
		System.out.println("supplier running");
		{
			try {
				while (true) {
					int wait = rand.nextInt(500) + 500;
					int product = rand.nextInt(3) + 1;
					Thread.sleep(wait);
					//Uses case statements and an RNG to determine what to place on the table
					switch (product) {
					case 1:
						placeProduct("tobacco");
						placeProduct("paper");
						
						break;
					case 2:
						placeProduct("paper");
						placeProduct("matches");
						break;
					case 3:
						placeProduct("matches");
						placeProduct("tobacco");
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}

}
