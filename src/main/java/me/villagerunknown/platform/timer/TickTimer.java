package me.villagerunknown.platform.timer;

import net.minecraft.server.MinecraftServer;

import java.util.HashMap;
import java.util.Map;

public class TickTimer {
	
	private boolean alarmActivated = false;
	private int alarmIntervalTicks = 0;
	private int tickCounter = 0;
	
	private Map<String, Object> dataset = new HashMap<>();
	
	public TickTimer(int minutes, int seconds) {
		setAlarm( minutes, seconds );
	}
	
	public TickTimer(int minutes) {
		setAlarm( minutes, 0 );
	}
	
	public void tick() {
		tickCounter++;
		
		if( alarmIntervalTicks > 0 && tickCounter >= alarmIntervalTicks ) {
			tickCounter = 0;
			alarmActivated = true;
		} // if
	}
	
	public void setAlarm( int minutes, int seconds ) {
		minutes = minutes * 60 * 20;
		seconds = seconds * 20;
		alarmIntervalTicks = minutes + seconds;
	}
	
	public boolean isAlarmActivated() {
		return alarmActivated;
	}
	
	public void resetAlarmActivation() {
		alarmActivated = false;
	}
	
	public int getDuration() {
		return alarmIntervalTicks;
	}
	
	public int getTicksUntilAlarm() {
		return alarmIntervalTicks - tickCounter;
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
