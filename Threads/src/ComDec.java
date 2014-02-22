import java.util.concurrent.Semaphore;


public class ComDec {

	/**
	 * @param args
	 */
	
	volatile int memoire = 0;
	
	public static void main(String[] args) {
		new ComDec().go () ;
	}

	private void go () {
		MyThread th1 = new MyThread(0) ;
		MyThread th2 = new MyThread(1) ;
		th1.start() ;
		th2.start() ;
		
		try {
			th1.join() ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			th2.join() ;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println ("Terminé") ;
	}
	class MyThread extends Thread {
		int nb  ;
		int counter = 0 ;
		public MyThread(int nb) {
			super();
			this.nb = nb; 
		}
		public void run () {
			while (counter < 100) {
				if (nb==0) {
					memoire++ ;
					System.out.println ("Thread-1:"+memoire) ;
				}
				if (nb==1) {
					memoire-- ;
					System.out.println ("Thread-2:"+memoire) ;
				}
				counter++ ;
			}		
		}
	}
}
