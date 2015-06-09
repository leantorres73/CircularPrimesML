package circularPrimes;
import java.util.concurrent.Callable;


public class ListPrimeGenerator implements Callable<Boolean>{
	
	int min;
	int max;
	
	public ListPrimeGenerator(int intMarker, int poolThread) {
        this.min = intMarker;
        this.max = poolThread;
    }

    @Override
    public Boolean call() {
		for (int i = min; i <= max; i++) 
		{
			if (!Main.isComposite [i])
				Main.primeList.add(new Integer(i));
		}
		return true;
    }
}
