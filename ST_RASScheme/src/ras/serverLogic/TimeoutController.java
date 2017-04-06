package ras.serverLogic;

public class TimeoutController {
	public TimeoutController(){
		
	}
	
	public static void execute(Thread task, long timeout) throws TimeoutException {
        task.start();
        try {
            task.join(timeout);
        } catch (InterruptedException e) {
            /* if somebody interrupts us he knows what he is doing */
        }
        if (task.isAlive()) {
            task.interrupt();
            throw new TimeoutException();
        }
    }

    public static void execute(Runnable task, long timeout) throws TimeoutException {
        Thread t = new Thread(task, "Timeout guard");
        t.setDaemon(true);
        execute(t, timeout);
    }

    public static class TimeoutException extends Exception {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/** Create an instance */
        public TimeoutException() {
        }
    }
    
    public static void main(String[] args){
    	TimeoutController controller = new TimeoutController();
    	Runnable test = controller.new HelloRunnable();
    	test.run();
    	boolean flag = false;
    	while(true){
    		try {
    			if(!flag){
    				//flag = true;
    				TimeoutController.execute(test, ras.interfaces.Utilities.TIMEOUT*1000);
    			}
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public class HelloRunnable implements Runnable {

        public void run() {
        	try {
				Thread.sleep(4000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
            System.out.println("Hello from a thread!");
        }
        /*
        public static void main(String args[]) {
            (new Thread(new HelloRunnable())).start();
        }
        */
    }
}
