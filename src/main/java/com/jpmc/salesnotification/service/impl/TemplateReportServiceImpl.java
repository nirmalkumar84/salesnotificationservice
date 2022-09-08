package com.jpmc.salesnotification.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.jpmc.salesnotification.bean.SalesType;
import com.jpmc.salesnotification.service.ReportService;
import com.jpmc.salesnotification.bean.ProductCatalog;

public class TemplateReportServiceImpl implements ReportService {
	@Override
	public void createSalesReport(Collection<ProductCatalog> products) 
	{
		System.out.println("======================== Sales Report =================================");
		System.out.println("Product                                   Count             Price      ");
		System.out.println("=======================================================================");
		products.forEach(System.out::println);
		System.out.println("=======================================================================");
		System.out.println(String.format("%-60s%-18.2f", "Total Sales", products.stream().collect(Collectors.summingDouble(p -> p.getEnchriedPrice().doubleValue()))));
		System.out.println("=======================================================================");
		System.out.println("\n\n");
	}
	
	@Override
	public void createAmendmentReport(List<SalesType> sale) 
	{
		System.out.println("======================== Assignment Report =============================");
		System.out.println("ADJUSTMENT     TYPE           Cost        Count         Enriched Price  ");
		System.out.println("========================================================================");
		sale.forEach(System.out::println);
		System.out.println("========================================================================");
		System.out.println("========================================================================");
		System.out.println("\n\n");
	}
	
}
