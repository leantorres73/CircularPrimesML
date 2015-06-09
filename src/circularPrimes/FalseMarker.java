package circularPrimes;
import java.util.concurrent.Callable;


public class FalseMarker implements Callable<Boolean>{
    
	int intMarker;
	int poolThread;
	
	public FalseMarker(int intMarker, int poolThread) {
        this.intMarker = intMarker;
        this.poolThread = poolThread;
    }

    @Override
    public Boolean call() {
		for (int i = intMarker; i * i <= Configs.MAX; i=i+poolThread)
		{
			if (!Main.isComposite [i])
			{
				// Vamos tachando todos los multiplos de cada numero.
				for (int j = i; i * j <= Configs.MAX; j++)
				{
					Main.isComposite [i*j] = true;
				}
			}
		}
		return true;
    }
}
