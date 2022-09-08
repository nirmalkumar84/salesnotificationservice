package com.jpmc.salesnotification.service;

public interface INotificationReportService 
{
	/**
	 * It is the first call of entry of the service.It get input as text and it
	 * transforms into java objects as per three message types.if it fails it throws
	 * Runtime exception
	 * 
	 * @param sale
	 */
	public void processNotification(String sale);
}
