package com.jpmc.salesnotification.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.jpmc.salesnotification.service.INotificationReportService;

/**
 * This is root test class will get the stream of input data from a input file
 * and display as report in the console.
 * 
 * @author Nirmal
 *
 */
public class NotifySalesReportServiceImplTest {
	@Test
	public void streamValidSalesEvents() {
		INotificationReportService reportNotification = new NotifySalesReportServiceImpl();
		try {
			Files.lines(Paths.get("src/test/resources/input.txt")).forEach(reportNotification::processNotification);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = RuntimeException.class)
	public void streamInValidSalesEvents() {
		INotificationReportService reportNotification = new NotifySalesReportServiceImpl();
		try {
			Files.lines(Paths.get("src/test/resources/invalid_input.txt"))
					.forEach(reportNotification::processNotification);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}