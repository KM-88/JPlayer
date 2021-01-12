/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hob.compo.engine;

import app.EventMessage;
import app.ExecutionTask;
import core.global.ApplicationConstantsFramework;
import framew.log.Logger;
import hob.compo.ui.ViewComponent;

/**
 *
 * 
 * @author kranti
 */
public class PeriodicViewUpdateExecutionTask extends ExecutionTask {

	private static Logger log;

	private static ViewComponent viewComponent;

	public PeriodicViewUpdateExecutionTask(String name) {
		super(name);
	}

	public PeriodicViewUpdateExecutionTask(String name, long refreshInterval) {
		super(name);
		this.refreshInterval = refreshInterval;
	}

	public PeriodicViewUpdateExecutionTask(String name, EventMessage event, long refreshInterval) {
		super(name, event);
		this.refreshInterval = refreshInterval;
	}

	@Override
	public synchronized void run() {
		super.run();
		log.info(PeriodicViewUpdateExecutionTask.class, this.getThreadName() + "execute", "Actvities : Starting");
		viewComponent.refreshData();
		log.info(PeriodicViewUpdateExecutionTask.class, this.getThreadName() + "execute", "Actvities : Finished");

	}

	@Override
	public void init() {
		log = (Logger) ApplicationConstantsFramework.getByName("logger");
		viewComponent = (ViewComponent) ApplicationConstantsFramework.getByName("defaultViewComponent");
		viewComponent.initialize();
	}

}
