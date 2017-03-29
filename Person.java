import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public abstract class Person implements Callable<Boolean>{
	protected Semaphore m_tobacco;
	protected Semaphore m_paper;
	protected Semaphore m_matches;
	
}
