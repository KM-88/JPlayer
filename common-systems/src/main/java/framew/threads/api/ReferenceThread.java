/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framew.threads.api;

import core.global.ApplicationConstantsCommon;
import framew.log.Logger;

/**
 *
 * @author kranti
 */
public abstract class ReferenceThread extends Thread {

	private final String threadName;

	private final Logger log = ApplicationConstantsCommon.getLogger();

	public ReferenceThread(String name) {
		this.threadName = name;
	}

	public String getThreadName() {
		return this.threadName;
	}

	public abstract void init();

	public void run() {
		this.setName(threadName);
		log.debug(getClass(), "run()",
				"Thread Started [" + this.getClass() + " - " + this.getId() + " - " + this.getThreadName() + " ]");
	}

}
