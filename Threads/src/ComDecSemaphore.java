import java.util.concurrent.Semaphore;


public class ComDecSemaphore {

	/**
	 * @param args
	 */
	
	volatile int memoire = 0;
	Semaphore sem0, sem1 ;
	
	public static void main(String[] args) {
		new ComDecSemaphore().go () ;
	}

	private void go () {
		sem0 = new Semaphore(1) ;
		sem1 = new Semaphore(0) ;
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
					try {
						sem0.acquire() ;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					memoire++ ;
					System.out.println ("Thread-1:"+memoire) ;
					sem1.release() ;
				}
				if (nb==1) {
					try {
						sem1.acquire() ;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					memoire-- ;
					System.out.println ("Thread-2:"+memoire) ;
					sem0.release() ;
				}
				counter++ ;
			}		
		}
	}
}
