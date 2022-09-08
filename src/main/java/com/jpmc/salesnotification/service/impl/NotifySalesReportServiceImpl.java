package com.jpmc.salesnotification.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.jpmc.salesnotification.bean.SalesType;
import com.jpmc.salesnotification.dao.impl.InMemorySaleRepository;
import com.jpmc.salesnotification.dao.SaleRepository;
import com.jpmc.salesnotification.processor.impl.SaleProcessor;
import com.jpmc.salesnotification.service.INotificationReportService;
import com.jpmc.salesnotification.service.ReportService;

/**
 * This is root class of streaming service.It calls the processnotification
 * method by just the text message as argument which will call the SaleProcessor
 * transform method to convert text message into java objects which will stores
 * these data in memory database.
 * 
 * @author Nirmal
 *
 */
public class NotifySalesReportServiceImpl implements INotificationReportService {
	private SaleRepository saleRepository;

	private ReportService salesReport;

	public NotifySalesReportServiceImpl() {
		this.saleRepository = new InMemorySaleRepository();
		this.salesReport = new TemplateReportServiceImpl();
	}

	@Override
	public void processNotification(String record) {
		SalesType saleType = SaleProcessor.transform(record);
		if (saleType == null)
			throw new RuntimeException("Invalid Sale Type Request");

		saleRepository.save(saleType);

		// For Every 10 record it should print the product and value of each type
		if (saleRepository.getCount() % 10 == 0)
			salesReport.createSalesReport(saleRepository.getProducts());

		// For Every 50 record it should pass/stop accepting new message and print the
		// adjustments
		if (saleRepository.getCount() % 50 == 0) {

			ExecutorService exec = Executors.newFixedThreadPool(2);
			exec.submit(() -> salesReport.createAmendmentReport(saleRepository.getAdjustmentMessages()));
			exec.shutdown();
			try {
				exec.awaitTermination(10, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
