import java.util.concurrent.atomic.AtomicLong;


public class E4Q1 {

	/**
	 * La valeur de myVar devrait etre de 21*N 
	 */

	private static final int NB_TREADS = 10 ;
	
	AtomicLong myVar = new AtomicLong(0) ;

	public static void main (String args []) {
		new E4Q1().go ();
	}
	
	private void go () {		
		for (int i=1 ; i <= NB_TREADS ; i++) {
			VrThread th = new VrThread(i) ;
			th.start() ;
		}
		System.out.println ("Terminé.") ;
		
	}

	class VrThread extends Thread {

		int number ;
		
		public VrThread(int number) {
			super();
			this.number = number;
		}	

		public void run() {
				synchronized (myVar) {
					for (int i=-10 ; i<=10 ; i++) {
					myVar.incrementAndGet() ;
				}
					System.out.println("Thread="+number+", value="+myVar) ;
			}
		}		
	}
	

}
