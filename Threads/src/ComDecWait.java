import java.util.concurrent.Semaphore;


public class ComDecWait {

	/**
	 * @param args
	 */
	
	volatile int memoire = 0;
	Object jeton ;

	public static void main(String[] args) {
		new ComDecWait().go () ;
	}

	private void go () {
		jeton = new Object() ;
		
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
					synchronized (jeton) {
						try {
							if (memoire>0) jeton.wait () ;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						memoire++ ;
						System.out.println ("Thread-1:"+memoire+"["+counter+"]") ;
						jeton.notify();
					}
				}
				if (nb==1) {
					synchronized (jeton) {
						try {
							if (memoire==0) jeton.wait () ;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						memoire-- ;
						System.out.println ("Thread-2:"+memoire+"["+counter+"]") ;
						jeton.notify() ;
					}
				}
				counter++ ;
			}		
		}
	}
}
