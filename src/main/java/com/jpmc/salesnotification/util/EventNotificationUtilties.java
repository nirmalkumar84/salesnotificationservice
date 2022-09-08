package com.jpmc.salesnotification.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import com.jpmc.salesnotification.bean.ProductCatalog;

/**
 * Utilities class will have all utility methods required.
 * 
 * @author Nirmal
 *
 */
public class EventNotificationUtilties {


	static public class pluralTypes {
		public static final String O = String.valueOf('o');
		public static final String Y = String.valueOf('y');
		public static final String H = String.valueOf('h');
		public static final String S = String.valueOf('s');
	}

	static public class AmendmentTypes {
		public static final String ADD = String.valueOf("add");
		public static final String SUB = String.valueOf("subtract");
		public static final String MUL = String.valueOf("multiply");

	}
	
	static public Map<String, String> pluralMap;
	static public Map<String, Operations> amendmentMap;
	static public Map<Operations, BiConsumer<ProductCatalog, BigDecimal>> operationMap;

	// static EnumMap<PLURAL, String> pluralMap = new EnumMap<PLURAL,
	// String>(PLURAL.class);
	static{
		 pluralMap = new HashMap<>();
		 pluralMap.put(pluralTypes.O, "%soes");
		 pluralMap.put(pluralTypes.Y, "%sies");
		 pluralMap.put(pluralTypes.H, "%shes");
		 pluralMap.put(pluralTypes.S, "%ss");
		 
		 amendmentMap = new HashMap<>();
		 amendmentMap.put(AmendmentTypes.ADD, Operations.ADDITION);
		 amendmentMap.put(AmendmentTypes.SUB, Operations.SUBTRACTION);
		 amendmentMap.put(AmendmentTypes.MUL, Operations.MULTIPLICATION);
		 
		 operationMap = new HashMap<>();
		 operationMap.put(Operations.ADDITION, (product, adjustmentPrice) -> {
			 double newPrice = product.getEnchriedPrice().doubleValue()
						+ product.getQuantity() * adjustmentPrice.doubleValue();
				product.setEnchriedPrice(getPrecession(newPrice));
		 });
		 operationMap.put(Operations.SUBTRACTION, (product, adjustmentPrice) -> {
			 double newPrice = product.getEnchriedPrice().doubleValue()
						- product.getQuantity() * adjustmentPrice.doubleValue();
				product.setEnchriedPrice(getPrecession(newPrice));
		 });
		 operationMap.put(Operations.MULTIPLICATION, (product, adjustmentPrice) -> {
				double newPrice = 0.0;
				if (product.getEnchriedPrice().doubleValue() != 0.0)
					newPrice = product.getEnchriedPrice().doubleValue() * product.getQuantity()
							* adjustmentPrice.doubleValue();
				else
					newPrice = product.getQuantity() * adjustmentPrice.doubleValue();
				product.setEnchriedPrice(getPrecession(newPrice));
		 });
		 
	}
	




	public static BiConsumer<ProductCatalog, BigDecimal> chooseOperationLogic(Operations adjustment) {
		return operationMap.get(adjustment);

	}

	/**
	 * This method will convert all product types as plural if apple is passed it
	 * need to be converted as apples
	 * 
	 * @param product
	 * @return 
	 */
	public static String convertProductTypePlural(String productType) {
		if (productType == null) 
			throw new NullPointerException("product type is null");
		String enchriedPluralProductType;
		String productPlural = productType.substring(productType.length() - 1);
		String typeWithoutLastChar = productType.substring(0, productType.length() - 1);

		if (pluralMap.containsKey(productPlural))
			enchriedPluralProductType = String.format(pluralMap.get(productPlural), typeWithoutLastChar);
		else if (!pluralMap.containsKey(productPlural))
			enchriedPluralProductType = String.format(pluralMap.get(pluralTypes.S), productType);
		else
			enchriedPluralProductType = String.format("%s", productType);
		return enchriedPluralProductType.toLowerCase();
	}

	public static BigDecimal convertRawPriceToDouble(String cost) {
		double price = Double.parseDouble(cost.replaceAll("[a-z]+", ""));
		price = Double.valueOf(Double.valueOf(price) / Double.valueOf("100"));
		BigDecimal roundOffPrice = getPrecession(price);
		return roundOffPrice;
	}

	public static BigDecimal getPrecession(double value) {
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
	}

}