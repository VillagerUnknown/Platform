package me.villagerunknown.platform.timer;

import java.util.HashMap;
import java.util.Map;

public class TickTimer {
	
	private boolean ALARM_ACTIVATED = false;
	private int ALARM_INTERVAL_TICKS = 72000;
	private int tickCounter = 0;
	
	private Map<String, Object> dataset = new HashMap<>();
	
	public TickTimer(int minutes, int seconds) {
		minutes = minutes * 60 * 20;
		seconds = seconds * 20;
		ALARM_INTERVAL_TICKS = minutes + seconds;
	}
	
	public TickTimer(int minutes) {
		ALARM_INTERVAL_TICKS = minutes * 60 * 20;
	}
	
	public void tick() {
		tickCounter++;
		
		if( tickCounter >= ALARM_INTERVAL_TICKS) {
			tickCounter = 0;
			ALARM_ACTIVATED = true;
		} // if
	}
	
	public boolean isAlarmActivated() {
		if( ALARM_ACTIVATED ) {
			return true;
		} // if
		
		return false;
	}
	
	public void resetAlarmActivation() {
		ALARM_ACTIVATED = false;
	}
	
	public int getDuration() {
		return ALARM_INTERVAL_TICKS;
	}
	
	public int getTicksUntilAlarm() {
		return ALARM_INTERVAL_TICKS - tickCounter;
	}
	
	public int getMinutesUntilAlarm() {
		return (getTicksUntilAlarm() / 1200) + 1;
	}
	
	public boolean hasData() {
		return !dataset.isEmpty();
	}
	
	public void putData( String key, Object data ) {
		dataset.put( key, data );
	}
	
	public Object getData( String key ) {
		return dataset.get( key );
	}
	
}
