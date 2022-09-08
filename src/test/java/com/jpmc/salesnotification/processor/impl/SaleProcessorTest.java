package com.jpmc.salesnotification.processor.impl;

import org.junit.Assert;
import org.junit.Test;

import com.jpmc.salesnotification.bean.SalesType;
import com.jpmc.salesnotification.util.Operations;

import static com.jpmc.salesnotification.util.EventNotificationUtilties.getPrecession;

public class SaleProcessorTest 
{
	
	@Test
	public void validate_SaleType1()
	{
		String msg = "apple at 20p";
		SalesType saleType = SaleProcessor.transform(msg);
		Assert.assertEquals(1, saleType.getProductCatalog().getQuantity());
		Assert.assertEquals(getPrecession(0.20), saleType.getProductCatalog().getOriginalPrice());
	}
	@Test
	public void validate_SaleType2()
	{
		String msg = "80 sales of apples at 20p each";
		SalesType saleType = SaleProcessor.transform(msg);
		Assert.assertEquals(80, saleType.getProductCatalog().getQuantity());
		Assert.assertEquals(getPrecession(0.20), saleType.getProductCatalog().getOriginalPrice());
	}
	@Test
	public void validate_SaleType3()
	{
		String msg = "Add 20p apples";
		SalesType saleType = SaleProcessor.transform(msg);
		Assert.assertEquals(getPrecession(0.20), saleType.getProductCatalog().getAdjustment());
		Assert.assertEquals(Operations.ADDITION, saleType.getOperation());
	}



	
}
