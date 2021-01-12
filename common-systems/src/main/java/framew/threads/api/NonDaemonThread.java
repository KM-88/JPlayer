package framew.threads.api;

public abstract class NonDaemonThread extends ContextThread {

        //Default value : should be set all implementations
	protected Integer refreshInterval = 60*60*1000;

	public void setRefreshInterval(Integer refreshInterval) {
		this.refreshInterval = refreshInterval;
	}

	public Integer getRefreshInterval() {
		return refreshInterval;
	}

	public NonDaemonThread(String name) {
		super(name);
		this.setDaemon(false);
	}

	public NonDaemonThread(String name, Integer refreshInterval) {
		super(name);
		this.setDaemon(false);
		this.setRefreshInterval(refreshInterval);
	}

	@Override
	public void run() {
		super.run();
		while (true) {
			execute();
			try {
				sleep(refreshInterval);
			} catch (InterruptedException iex) {
				iex.getMessage();
			}
		}
	}
        
        /**
         * This method is to perform necessary events before executing main task of the thread.<br>
         * It should be overriden whenever required.
         */
        public void performStartUpActivites(){
            
        }

	/**
	 * Implementing classes must provide their own implementation logic by
	 * overriding this method. Overriding the run() method directly is
	 * discouraged.
	 */
	public abstract void execute();

}
