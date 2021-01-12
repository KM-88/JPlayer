/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task;

import app.EventMessage;
import app.ExecutionTask;
import core.global.ApplicationConstantsFramework;
import datasource.CacheManager;
import framew.log.Logger;

/**
 *
 * @author kranti
 */
public class CacheExecutionTask extends ExecutionTask {

	private static Logger log;

	public CacheExecutionTask(String name) {
		super(name);
	}

	public CacheExecutionTask(String name, long refreshInterval) {
		super(name);
		this.refreshInterval = refreshInterval;
	}

	public CacheExecutionTask(String name, EventMessage event, long refreshInterval) {
		super(name, event);
		this.refreshInterval = refreshInterval;
	}

	@Override
	public synchronized void run() {
		super.run();
		log.info(CacheExecutionTask.class, "run()", "Activities : Starting");
		CacheManager.refreshAll();
		log.info(CacheExecutionTask.class, "run()", "Activities : Finished");
	}

	@Override
	public void init() {
		log = (Logger) ApplicationConstantsFramework.getLogger();
	}

}
