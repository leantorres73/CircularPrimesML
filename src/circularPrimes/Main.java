package circularPrimes;
import java.util.*;
import java.util.concurrent.ExecutionException;
 
public class Main
{
	static boolean[] isComposite;
	static List<Integer> primeList;
	public static List<Integer> generatePrimes() throws InterruptedException, ExecutionException
	{
		
		//inicializo variables
		List<Integer> circularPrimes = new ArrayList<Integer>();
		primeList = Collections.synchronizedList(new ArrayList<Integer>());
		isComposite = new boolean[Configs.MAX + 1];
		long initialTime = System.currentTimeMillis();

		// genera primos debajo de 1.000.000
		PrimeHelper.generatePrimes();
		//2 y 5 serán eliminados posteriormente por eso los agrego.
		circularPrimes.add(2);
		circularPrimes.add(5);
		TreeSet<Integer> returnList = new TreeSet<Integer>(primeList);
		//calculo los primos circulares en la lista de primos ya obtenidos.
		Iterator<Integer> itr=returnList.iterator();
		while(itr.hasNext()){
		     Integer i = itr.next();
		     if(PrimeHelper.checkCircularPrime(i))
					circularPrimes.add(i);
		}
        return circularPrimes;
  }
 

	
 
}