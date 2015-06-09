package circularPrimes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

public class PrimeHelper {
	
	/**
	 * Método para generar primos.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void generatePrimes() throws InterruptedException, ExecutionException
	{
        // Lista de hilos a correr en paralelo
		ArrayList<Callable<Boolean>> threads = new ArrayList<Callable<Boolean>>(Configs.MAX);
		// Vamos tachando todos los multiplos de cada numero.
		for(int i = 0 ; i < Configs.THREAD_POOLS ; i++)
		{
			threads.add(new FalseMarker(i+2 , Configs.THREAD_POOLS));
		}
        ExecutorService ejecutor = new ScheduledThreadPoolExecutor(100);
        // Ejecuto todos los hilos 
        List<Future<Boolean>> results = null;
        try {
            System.out.println("Running " + threads.size() + " threads burning posibilities.");
            results = ejecutor.invokeAll(threads);
        } catch (InterruptedException ex) {
            Logger.getLogger("Error in process." + ex.getMessage());
        }
        // Espero por sus resultados.
        for (Future<Boolean> f : results)
        {
        	f.get();
        }
        
        // Agrego a la lista los primos (los que quedaron en isComposite como false).
        ArrayList<Callable<Boolean>> threadsListAdding = new ArrayList<Callable<Boolean>>(Configs.THREAD_POOLS);
        ExecutorService addExecutor = new ScheduledThreadPoolExecutor(100);
		for(int i = 0 ; i < Configs.THREAD_POOLS ; i++)
		{
			//Divido en hilos para hacer la verificación.
			int poolCount = Configs.MAX / Configs.THREAD_POOLS;
			int from = i * poolCount;
			int upTo = 0;
			if (i == 0)
				from = 1;
			if((i+1) == Configs.THREAD_POOLS)
				upTo = Configs.MAX ;
			else
				upTo = (i+1) * poolCount;
			threadsListAdding.add(new ListPrimeGenerator(from+1 , upTo));
		}
        // Ejecuto todos los hilos 
        List<Future<Boolean>> addResults = null;
        try {
            System.out.println("Running " + threads.size() + " threads that add to prime list.");
            addResults = addExecutor.invokeAll(threadsListAdding);
        } catch (InterruptedException ex) {
            Logger.getLogger("Error in process." + ex.getMessage());
        }
        // Espero por sus resultados.
        for (Future<Boolean> f : addResults)
        {
    		f.get();
        }
	}
	
	/**
	 * Método para identificar si un número es primo. 
	 * @param n
	 * @return
	 */
	public static boolean isPrime(int n)
	{
		// Si es menor a 2 no es primo.
	    if(n < 2)
	    	return false;
	    // Si es divisible por 2 (modulo 2 = 0) tampoco es primo.
    	if(n != 2 && n % 2 == 0)
    		return false;
    	// Valida si es divisible por algún numero menor a su raiz cuadrada, esto ahorra procesamiento.
	    for(int i = 3; i*i <= n; i += 2)
	    {
	    	if( n % i == 0 )
	    		return false;
	    }
	    return true;
	}
	
	/**
	 * Método para detectar si el número primo es circular.
	 * @param number
	 * @return
	 */
	public static boolean checkCircularPrime(int number)
	{
		int rotate = 0;
		int multiplier = 1;
		int n = number;
		boolean circularPrime = true;
		while(n > 0)
		{
	        rotate++;
	        int lastDigit = n % 10;
	        n /= 10;
	        if(lastDigit % 2 == 0 || lastDigit == 5)
	        {
	        	return false;
	        }
	        multiplier *= 10;
		}    
		multiplier /= 10;
		n = number;
		while(rotate > 0)
		{
			n = (n % 10 * multiplier) + (n/10);
			rotate--;
			if(!PrimeHelper.isPrime(n))
			{
				circularPrime = false;
				break;
			}
		}
		if(circularPrime)
			return true;
		return false;
	}
	
}
