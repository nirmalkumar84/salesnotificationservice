package com.jpmc.salesnotification.dao;

import java.util.Collection;
import java.util.List;

import com.jpmc.salesnotification.bean.ProductCatalog;
import com.jpmc.salesnotification.bean.SalesType;

public interface SaleRepository {

	/**
	 * Get the Product details from the in memory product repository it returns a
	 * single product per product type
	 * 
	 * @param String
	 * @return ProductCatalog
	 */
	ProductCatalog getProductCatalog(String type);

	/**
	 * Get the size of the sale types which received as input reporting will be
	 * calculated the sales repository count
	 * 
	 * @return int
	 */
	int getCount();

	/**
	 * Get Sales types which prices needs to be adjusted,It will show the list in
	 * the adjustment report
	 * 
	 * @return List
	 */
	List<SalesType> getAdjustmentMessages();
    
    /**
     * Get the Whole list of products from the sale repository
     * @return Collection
     */
	Collection<ProductCatalog> getProducts();

	/**
	 * Get every streaming saleType object into inmemory database
	 * @param saleType
	 */
	void save(SalesType saleType);

}