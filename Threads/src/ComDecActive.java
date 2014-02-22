
public class ComDecActive {

	/**
	 * @param args
	 */
	
	volatile int memoire = 0;
	boolean eligible0 ;
	boolean eligible1 ;
	
	public static void main(String[] args) {
		new ComDecActive().go () ;
	}

	private void go () {
		eligible0 = true ; eligible1 = false ;
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

	public void switchEligible () { 		
		try {
			eligible0 = !eligible0; 
			Thread.sleep(1000) ;
			eligible1 = !eligible1; 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class MyThread extends Thread {
		int nb  ;
		int counter = 0 ;
		public MyThread(int nb) {
			super();
			this.nb = nb; 
		}
		public void run () {
			while (counter < 10000) {
				if (nb==0) {
					while (true) {
						if (eligible0) {
							memoire++ ;
							System.out.println ("Thread-1:"+memoire) ;
							switchEligible () ;
							break ;							
						}
					}
				}
				if (nb==1) {
					while (true) {
						if (eligible1) {
							memoire-- ;
							System.out.println ("Thread-2:"+memoire) ;
							switchEligible () ;
							break ;
						}
					}
				}
				counter++ ;
			}		
		}
	}
}
