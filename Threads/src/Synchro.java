import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;


public class Synchro {

	private static final long THRESHOLD = 100000000 ;
	private static final int NB_TREADS = 100 ;
	private static final boolean SYNCHRONIZED = false ;
	
	AtomicLong counterSynchronized = new AtomicLong(0) ;
	volatile long counterNotSynchronized = 0 ;

	public static void main (String args []) {
		new Synchro().go ();
	}
	
	private void go () {		
		ArrayList<VrThread> allThreads = new ArrayList<VrThread>() ;
		counterNotSynchronized=0;
		counterSynchronized.set(0) ;
		for (int i=1 ; i <= NB_TREADS ; i++) {
			VrThread th = new VrThread(i,SYNCHRONIZED) ;
			allThreads.add(th);
			th.start() ;
		}
		for (VrThread th : allThreads) {
			try {
				th.join() ;
			} catch (InterruptedException e) {
			}
		}
		System.out.println ("Terminé.") ;
		
	}

	class VrThread extends Thread {

		int number ;
		private boolean isRunning ;
		private boolean synchro;
		
		public VrThread(int number, boolean synchro) {
			super();
			this.number = number;
			this.isRunning = true ;
			this.synchro=synchro ;
		}	
		public void run() {
			while (isRunning) {
				if (synchro) incrSynchro() ; else incrNotSynchro();
			}
			System.out.println("Thread="+number+", value="+(synchro?counterSynchronized.get():counterNotSynchronized)) ;
		}
		void  incrSynchro() {
			synchronized (counterSynchronized) {
				if (counterSynchronized.get()<THRESHOLD) {
					counterSynchronized.incrementAndGet() ;
				}
				else {
					isRunning = false ;
				}			
			}
		}
		void incrNotSynchro() {
			if (counterNotSynchronized<THRESHOLD) {
				counterNotSynchronized++ ;
			}
			else {
				isRunning = false ;
			}			
		}
		
	}
	
}
