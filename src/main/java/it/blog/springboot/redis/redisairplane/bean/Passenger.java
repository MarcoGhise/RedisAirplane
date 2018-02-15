package it.blog.springboot.redis.redisairplane.bean;

import java.io.Serializable;

public class Passenger implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5081831659963658155L;
	private String code;
	private String name;
	private String seat;
	private String flightCode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getFlightCode() {
		return flightCode;
	}
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
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
		"name:" + name + "\n" + 
		"seat:" + seat + "\n" + 
		"flightCode:" + flightCode;
	}
}
