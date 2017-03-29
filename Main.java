import java.util.*;
import java.util.concurrent.*;

public class Main {
	public static void main(String args[]) throws Exception {
		Supplier supplier = null;
		List<Person> person = new ArrayList<Person>();
		//initiates and adds requried persons
		person.add(supplier.getInstance());
		person.add(new Smoker(new Product("tobacco"),supplier.getInstance().getTobacco(),supplier.getInstance().getPaper(),supplier.getInstance().getMatches()));
		person.add(new Smoker(new Product("paper"),supplier.getInstance().getTobacco(),supplier.getInstance().getPaper(),supplier.getInstance().getMatches()));
		person.add(new Smoker(new Product("matches"),supplier.getInstance().getTobacco(),supplier.getInstance().getPaper(),supplier.getInstance().getMatches()));
		ExecutorService executor = Executors.newFixedThreadPool(4);
		//runs the proper threads;
		executor.invokeAll(person);
		executor.shutdown();
	}
}
