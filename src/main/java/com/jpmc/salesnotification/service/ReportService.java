package com.jpmc.salesnotification.service;

import java.util.Collection;
import java.util.List;

import com.jpmc.salesnotification.bean.ProductCatalog;
import com.jpmc.salesnotification.bean.SalesType;

public interface ReportService {

	/**
	 * It display the reports in system console.It just groups by using count and
	 * price based on unique product types.It is runs every 10 messages.
	 * 
	 * @param products
	 */
	void createSalesReport(Collection<ProductCatalog> products);

	/**
	 * It display the reports in system console.It gets all the amended operation
	 * sale types and it add/sub/mul price in each types .It is runs every 50 messages.
	 * 
	 * @param sale
	 */
	void createAmendmentReport(List<SalesType> sale);

}