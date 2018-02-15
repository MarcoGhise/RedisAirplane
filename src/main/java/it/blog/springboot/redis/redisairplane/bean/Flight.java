package it.blog.springboot.redis.redisairplane.bean;

import java.io.Serializable;
import java.util.List;

public class Flight implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3323388627406385573L;
	private String code;
	private String from;
	private String to;
	private String gate;
	private String departureTime;
	private List<String> passengers;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getGate() {
		return gate;
	}
	public void setGate(String gate) {
		this.gate = gate;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public List<String> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<String> passengers) {
		this.passengers = passengers;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString()
	{
		return "***********\n" + 
		"code:" + code + "\n" + 
		"from:" + from + "\n" + 
		"to:" + to + "\n" + 
		"gate:" + gate + "\n" + 
		"departureTime:" + departureTime + "\n" + 
		"passengers:" + passengers.toString() ;
		
	}
}
