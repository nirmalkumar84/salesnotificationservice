package com.jpmc.salesnotification.dao;

import java.util.List;

import com.jpmc.salesnotification.bean.ProductCatalog;

public interface ProductCatalogRepository {
	/**
	 * It get the product from the repository based on the product type
	 * 
	 * @param get product type as String
	 * @return type as productcatalog
	 * @see ProductCatalog
	 */
	ProductCatalog find(String type);

	/**
	 * It get all the products from product repository irrespective of product type
	 * 
	 * @return the List of productcatalog
	 * @see List
	 */
	List<ProductCatalog> findAll();

	/**
	 * It is uses the map as internal data structure as map it uses product type as
	 * key and Product as value
	 * 
	 * @param type
	 * @param productCatalog
	 * @return type as productcatalog
	 * @see ProductCatalog
	 */
	ProductCatalog save(String type, ProductCatalog productCatalog);

	/**
	 * 
	 * @param type
	 * @return
	 */
	boolean exists(String type);

}