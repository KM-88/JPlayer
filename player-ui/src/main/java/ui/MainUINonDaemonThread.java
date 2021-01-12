/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import framew.global.ApplicationConstantsUI;
import framew.log.Logger;
import framew.threads.api.NonDaemonThread;
import hob.compo.ui.MainUIComponents;

/**
 *
 * @author kranti
 */
public class MainUINonDaemonThread extends NonDaemonThread {

	private static Logger log;

	public MainUINonDaemonThread(String name) {
		super(name);
	}

	@Override
	public void execute() {
		log.debug(MainUINonDaemonThread.class, "execute", "Activities : Starting");
		MainUIComponents.updatePlayingComponents();
		log.debug(MainUINonDaemonThread.class, "execute", "Activities : Finished");
	}

	@Override
	public void performStartUpActivites() {

	}

	@Override
	public void init() {
		log = (Logger) ApplicationConstantsUI.getLogger();
		log.info(MainUINonDaemonThread.class, "init", "Initialisation : Starting");
		MainUIComponents.initialize();
		log.info(MainUINonDaemonThread.class, "init", "Initialisation : Finished");
	}

}
