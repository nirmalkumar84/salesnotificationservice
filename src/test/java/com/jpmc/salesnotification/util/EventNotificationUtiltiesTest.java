package com.jpmc.salesnotification.util;

import static com.jpmc.salesnotification.util.EventNotificationUtilties.chooseOperationLogic;
import static com.jpmc.salesnotification.util.EventNotificationUtilties.convertProductTypePlural;
import static com.jpmc.salesnotification.util.EventNotificationUtilties.convertRawPriceToDouble;
import static com.jpmc.salesnotification.util.EventNotificationUtilties.getPrecession;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jpmc.salesnotification.bean.ProductCatalog;

public class EventNotificationUtiltiesTest {
	
	ProductCatalog product;
	
    @Before
    public void setUp() {
    	product = new ProductCatalog("apple",null , 10);
    }
	
	@Test
	public void validate_EncrichedPrice_Addition()
	{
		chooseOperationLogic(Operations.ADDITION).accept(product,getPrecession(.50));
		Assert.assertEquals(getPrecession(5.0), product.getEnchriedPrice());
	}
	@Test
	public void validate_EncrichedPrice_Subtract()
	{
		chooseOperationLogic(Operations.SUBTRACTION).accept(product,getPrecession(.50));
		Assert.assertEquals(getPrecession(-5.0), product.getEnchriedPrice());
	}
	@Test
	public void validate_EncrichedPrice_Multiply()
	{
		chooseOperationLogic(Operations.MULTIPLICATION).accept(product,getPrecession(.50));
		Assert.assertEquals(getPrecession(5.0), product.getEnchriedPrice());
	}

	@Test
	public void product_Type_As_Cherry() {
		String type = "cherry";
		String pluralProductType = convertProductTypePlural(type);
		Assert.assertEquals("cherries", pluralProductType);
	}
	
	@Test
	public void product_Type_As_Potato() {
		String type = "potato";
		String pluralProductType = convertProductTypePlural(type);
		Assert.assertEquals("potatoes", pluralProductType);
	}
	
	@Test
	public void product_Type_As_Spinach() {
		String type = "spinach";
		String pluralProductType = convertProductTypePlural(type);
		Assert.assertEquals("spinaches", pluralProductType);
	}
	
	@Test
	public void product_Type_As_Apple() {
		String type = "apple";
		String pluralProductType = convertProductTypePlural(type);
		Assert.assertEquals("apples", pluralProductType);
	}
	
	@Test(expected = NullPointerException.class)
	public void product_Type_As_Null() {
		String type = null;
		convertProductTypePlural(type);
		
	}
	
	@Test
	public void price_As_Double_RoundOff() {
		String cost = "10p";
		BigDecimal price = convertRawPriceToDouble(cost);
		Assert.assertEquals(BigDecimal.valueOf(0.10).setScale(2, RoundingMode.HALF_UP), price);
	}

}
