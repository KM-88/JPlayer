/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import framew.bus.infc.Event;

/**
 *
 * @author kranti
 */
public class EventMessage implements Event{
    
    private String eventMessage;

    public EventMessage(String message){
        this.eventMessage = message;
    }
    
    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    @Override
    public String toString() {
        return "EventMessage[" + eventMessage + "]";
    }

	@Override
	public String getMessage() {
		return eventMessage;
	}
    
    
    
}
