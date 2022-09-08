package com.jpmc.salesnotification.dao.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jpmc.salesnotification.bean.ProductCatalog;
import com.jpmc.salesnotification.bean.SalesType;
import com.jpmc.salesnotification.dao.SaleRepository;
import com.jpmc.salesnotification.util.Operations;

public class InMemorySaleRepositoryTest {
	
	private SaleRepository saleRepository;
	
	@Before
	public void setUp(){
		this.saleRepository = new InMemorySaleRepository();
		
	}

	@Test
	public void check_RepositoryCount_After_SaleType_Insert() {
		SalesType saleType = new SalesType();
		saleType.setProductCatalog(new ProductCatalog("apples", BigDecimal.valueOf(30).setScale(2,RoundingMode.HALF_UP), 1));
		saleRepository.save(saleType);
		Assert.assertEquals(1, saleRepository.getCount());
		Assert.assertEquals(BigDecimal.valueOf(30).setScale(2,RoundingMode.HALF_UP), saleRepository.getProductCatalog("apples").getEnchriedPrice());
	}
	
	@Test(expected = RuntimeException.class)
	public void check_RepositoryCount_Check_ProductCatalog() {
		SalesType saleType = new SalesType();
		ProductCatalog product = new ProductCatalog();
		product.setType("cherries");
		product.setAdjustment(BigDecimal.valueOf(30).setScale(2,RoundingMode.HALF_UP));
		saleType.setOperations(Operations.ADDITION);
		saleType.setProductCatalog(product);
		saleRepository.save(saleType);
		
	}

}
