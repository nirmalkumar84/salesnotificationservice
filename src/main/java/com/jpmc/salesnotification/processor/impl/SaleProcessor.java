package com.jpmc.salesnotification.processor.impl;

import static com.jpmc.salesnotification.util.EventNotificationUtilties.amendmentMap;
import static com.jpmc.salesnotification.util.EventNotificationUtilties.convertProductTypePlural;
import static com.jpmc.salesnotification.util.EventNotificationUtilties.convertRawPriceToDouble;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import com.jpmc.salesnotification.bean.ProductCatalog;
import com.jpmc.salesnotification.bean.SalesType;

public class SaleProcessor {

	public static int DEFAULT_PRECESSION = 8;

	/**
	 * This method is responsible for doing the data transformation based on the
	 * message types 1,2,3 Based on each pattern matching it will get into
	 * respective transformation based on the offset value. transformed will return
	 * an SalesType object,These object will be passed inmemory database to persist
	 * in memory.
	 * 
	 * @param String sale
	 * @return SaleType type
	 */
	public static SalesType transform(String sale) {
		if (sale == null)
			sale = "";
		String[] saleArr = null;
		/**
		 * Message type 3 if it is ADD/SUBTRACT/MULTIPLY Based on that it will do the
		 * adjustments
		 */
		if (Pattern.matches("(Add|Subtract|Multiply) [0-9]+p [A-Za-z]+", sale)) {

			ProductCatalog product = new ProductCatalog();
			saleArr = sale.trim().split("\\s+");
			String type = convertProductTypePlural(saleArr[2]);
			product.setType(type);
			BigDecimal cost = convertRawPriceToDouble(saleArr[1]);
			product.setAdjustment(cost);
			return createSaleTypes.apply(saleArr, product);
		}
		/**
		 * Message type 2 if it is more than one product it needs to multiply the price
		 * with quantity
		 */
		else if (Pattern.matches("[0-9]+ sales of [A-Za-z]+ at [0-9]+p each", sale)) {

			saleArr = sale.trim().split("\\s+");
			String saleType = convertProductTypePlural(saleArr[3]);
			BigDecimal cost = convertRawPriceToDouble(saleArr[5]);
			int quantity = Integer.valueOf(saleArr[0]);
			ProductCatalog product = new ProductCatalog(saleType, cost, quantity);
			return createSaleTypes.apply(saleArr, product);
			/**
			 * Message type 1 if it is single product it needs to be created newly if
			 * already existed append the price with existing product type
			 */
		} else if (Pattern.matches("[A-Za-z]+ at [0-9]+p", sale)) {

			saleArr = sale.trim().split("\\s+");
			String saleType = convertProductTypePlural(saleArr[0]);
			BigDecimal cost = convertRawPriceToDouble(saleArr[2]);
			ProductCatalog product = new ProductCatalog(saleType, cost, 1);
			return createSaleTypes.apply(saleArr, product);

		} else
			return null;
	}

	private static BiFunction<String[], ProductCatalog, SalesType> createSaleTypes = (saleArr, product) -> {

		SalesType saleType = new SalesType();
		saleType.setProductCatalog(product);
		if (Pattern.matches("(Add|Subtract|Multiply)", saleArr[0])) 
			saleType.setOperations(amendmentMap.get(saleArr[0].toLowerCase()));
		

		return saleType;

	};


	
}
