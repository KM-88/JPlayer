package framew.threads.api;

public abstract class ContextThread extends ReferenceThread {

	public ContextThread(String name) {
            super(name);
	}

	@Override
	public void run() {
		super.run();
		performStartUpActivites();
	}
        
	public abstract void performStartUpActivites();

}
