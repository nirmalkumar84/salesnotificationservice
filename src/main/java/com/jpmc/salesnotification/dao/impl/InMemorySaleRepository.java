package com.jpmc.salesnotification.dao.impl;

import static com.jpmc.salesnotification.util.EventNotificationUtilties.chooseOperationLogic;
import static com.jpmc.salesnotification.util.EventNotificationUtilties.getPrecession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import com.jpmc.salesnotification.bean.ProductCatalog;
import com.jpmc.salesnotification.bean.SalesType;
import com.jpmc.salesnotification.dao.ProductCatalogRepository;
import com.jpmc.salesnotification.dao.SaleRepository;
/**
 * This class deals in memory storage for all message type  ,It deals with all CRUD operation
 * needs to be performed on database.
 * 
 * @author Nirmal
 *
 */

public class InMemorySaleRepository implements SaleRepository 
{
	private ProductCatalogRepository productRepository;
	private List<SalesType> listofSaleTypes;
	
	public InMemorySaleRepository() {
		this.listofSaleTypes = new ArrayList<SalesType>();
		this.productRepository = new InMemoryProductCatalogRepository();
	}
	
	@Override
	public ProductCatalog getProductCatalog(String type)
	{
		return productRepository.find(type);
	}
	@Override
	public int getCount() 
	{
		return listofSaleTypes.size();
	}
	
	@Override
	public List<SalesType> getAdjustmentMessages()
	{
		return listofSaleTypes.stream().filter((msg) -> msg.getOperation()!=null).collect(Collectors.toList());
	}
	@Override
	public Collection<ProductCatalog> getProducts() 
	{
		return Collections.unmodifiableCollection(productRepository.findAll());
	}
	@Override
	public void save(SalesType saleType)
	{
		
			listofSaleTypes.add(saleType);
			if(saleType.getOperation() != null) 
			{
				saveEncrichedProductBasedOnOperations(saleType);
				
			} else 
				saveProducts(saleType.getProductCatalog());
			
		
	}

	private void saveEncrichedProductBasedOnOperations(SalesType saleType) {
		BiConsumer<ProductCatalog , BigDecimal> operation = chooseOperationLogic(saleType.getOperation());
		ProductCatalog product = getProductCatalog(saleType.getProductCatalog().getType());
		if(product == null)
		    throw new RuntimeException("Product catalog not available in Product repository");
		
		operation.accept(product, saleType.getProductCatalog().getAdjustment());
		saleType.getProductCatalog().setOriginalPrice(product.getEnchriedPrice());
		saleType.getProductCatalog().setQuantity(product.getQuantity());
		saleType.getProductCatalog().setEnchriedPrice(product.getEnchriedPrice());
	}

	private void saveProducts(ProductCatalog currentProduct) {
		if(currentProduct != null && productRepository.exists(currentProduct.getType())) 
		{
			ProductCatalog productFromMemory = productRepository.find(currentProduct.getType());
			productFromMemory.setQuantity(productFromMemory.getQuantity() + currentProduct.getQuantity());
			productFromMemory.setType(currentProduct.getType());
			productFromMemory.setEnchriedPrice(getPrecession(productFromMemory.getEnchriedPrice().doubleValue() + currentProduct.getQuantity() * currentProduct.getOriginalPrice().doubleValue()));
		} 
		else 
		{
			ProductCatalog product = new ProductCatalog();
			product.setQuantity(currentProduct.getQuantity());
			product.setType(currentProduct.getType());
			product.setEnchriedPrice(getPrecession( currentProduct.getQuantity() * currentProduct.getOriginalPrice().doubleValue()));
			productRepository.save(currentProduct.getType(), product);
		}
	}
}