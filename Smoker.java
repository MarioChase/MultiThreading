import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class Smoker extends Person {
	public Product owned;

	//constructor that takes the semaphores
	public Smoker(Product product, Semaphore tobacco, Semaphore paper, Semaphore matches) {
		m_tobacco = tobacco;
		m_paper = paper;
		m_matches = matches;
		owned = product;
	}
	
	//easy way to release the current permits it holds
	synchronized public void finished(Semaphore a, Semaphore b) {
		a.release();
		b.release();
	}

	@Override
	public Boolean call() throws Exception {
		Random rand = new Random();
		System.out.println("Smoker running");
		{
			try {
				while (true) {
					int smoking = 500 ;//rand.nextInt(500) + 1000;
					//case statement to determine which product the smoker has ulimited amounts of
					switch (owned.getType()) {
					case "tobacco":
						m_paper.acquire();
						System.out.println(owned.getType());
						System.out.println(this + ": picked up paper");
						if (m_matches.tryAcquire()) {
							m_matches.acquire();
							System.out.println(this + ": picked up matches");
							Thread.sleep(smoking);
							finished(m_paper, m_matches);
							System.out.println(owned.getType() + ": is smoking");
							Supplier.getInstance().clearTable();
							break;
						} else {
							//if smoker could not obtain the second item it drops the first one
							Thread.sleep(1000);
							m_paper.release();
							System.out.println(this + ": dropped paper");
							break;
						}

					case "paper":
						m_matches.acquire();
						System.out.println(owned.getType());
						System.out.println(this + ": picked up matches");
						if (m_tobacco.tryAcquire()) {
							m_tobacco.acquire();
							System.out.println(this + ": picked up tobacco");
							Thread.sleep(smoking);
							finished(m_matches, m_tobacco);
							System.out.println(owned.getType() + ": is smoking");
							Supplier.getInstance().clearTable();
							break;
						} else {
							m_matches.release();
							Thread.sleep(1000);
							System.out.println(this + ": dropped matches");
							break;
						}

					case "matches":
						m_paper.acquire();
						System.out.println(owned.getType());
						System.out.println(this + ": picked up paper");
						if (m_tobacco.tryAcquire()) {
							m_tobacco.acquire();
							System.out.println(this + ": picked up tobacco");
							Thread.sleep(smoking);
							finished(m_paper, m_tobacco);
							System.out.println(owned.getType() + ": is smoking");
							Supplier.getInstance().clearTable();
							break;
						} else {
							m_paper.release();
							Thread.sleep(1000);
							System.out.println(this + ": dropped paper");
							break;
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
	}

}
