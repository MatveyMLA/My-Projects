

public class Main {

	public static void main(String[] args) throws InterruptedException {
		init();
		
	}

	private static void init() {
		SocketConnekctionManager socketConnekctionManager =  new SocketConnekctionManager();
		socketConnekctionManager.start();		
		
	}

}
