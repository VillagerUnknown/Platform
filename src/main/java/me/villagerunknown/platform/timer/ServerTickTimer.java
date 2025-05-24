package me.villagerunknown.platform.timer;

import java.util.HashMap;
import java.util.Map;

public class ServerTickTimer {
	
	private boolean alarmActivated = false;
	private long triggerTick = 0L;
	private long alarmIntervalTicks = 0L;
	
	private Map<String, Object> dataset = new HashMap<>();
	
	public ServerTickTimer(long currentTick, int minutes, int seconds) {
		setAlarm( currentTick, minutes, seconds );
	}
	
	public ServerTickTimer(long currentTick, int minutes) {
		setAlarm( currentTick, minutes, 0 );
	}
	
	public ServerTickTimer(long currentTick, long alarmIntervalTicks) {
		setAlarm( currentTick, alarmIntervalTicks );
	}
	
	public void tick( long currentTick ) {
		if( triggerTick > 0 && currentTick >= triggerTick ) {
			alarmActivated = true;
		} // if
	}
	
	public void setAlarm( long currentTick, int minutes, int seconds ) {
		minutes = minutes * 60 * 20;
		seconds = seconds * 20;
		alarmIntervalTicks = minutes + seconds;
		
		setTriggerTick( currentTick, alarmIntervalTicks );
	}
	
	public void setAlarm( long currentTick, long alarmIntervalTicks ) {
		this.alarmIntervalTicks = alarmIntervalTicks;
		
		setTriggerTick( currentTick, alarmIntervalTicks );
	}
	
	public void setTriggerTick( long currentTick, long alarmIntervalTicks ) {
		triggerTick = currentTick + alarmIntervalTicks;
		
		if( Integer.MAX_VALUE == triggerTick ) {
			triggerTick = alarmIntervalTicks;
		} // if
	}
	
	public boolean isAlarmActivated() {
		return alarmActivated;
	}
	
	public void clearAlarmActivation() {
		alarmActivated = false;
	}
	
	public void resetAlarmActivation( long currentTick ) {
		alarmActivated = false;
		setTriggerTick( currentTick, alarmIntervalTicks );
	}
	
	public long getTicksUntilAlarm( long currentTick ) {
		return triggerTick - currentTick;
	}
	
	public long getMinutesUntilAlarm( long currentTick ) {
		return (getTicksUntilAlarm( currentTick ) / 1200) + 1;
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
