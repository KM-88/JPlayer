/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framew.threads.api;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kranti
 * 
 * It's a daemon thread, which means it'll start and executes until a certain condition
 * is satisfied and then finish.
 */
public abstract class DaemonThread extends ReferenceThread{
    public DaemonThread(String name) {
            super(name);
            this.setDaemon(true);
	}

	@Override
	public void run() {
		super.run();
		performStartUpActivites();
                while(isConditionSatisfied()){
                    execute();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DaemonThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
	}
        
        /**
         * Well, necessary pre-checks or set-up to be done before thread execution.
         */
	public abstract void performStartUpActivites();
        
        /**
         * This method is to be used to determine if the thread should continue to run or not.
         * @return 
         */
        public abstract boolean isConditionSatisfied();
        
        /**
         * This method code will be executed in loop until the necessary condition for this thread
         * execution prevails.
         */
        public abstract void execute();
}
