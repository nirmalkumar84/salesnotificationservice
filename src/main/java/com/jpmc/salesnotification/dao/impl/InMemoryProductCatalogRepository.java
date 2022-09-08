package com.jpmc.salesnotification.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jpmc.salesnotification.bean.ProductCatalog;
import com.jpmc.salesnotification.dao.ProductCatalogRepository;

/**
 * This class deals in memory products storage ,It deals with all CRUD operation
 * needs to be performed on database
 * 
 * @author Nirmal
 *
 */
public class InMemoryProductCatalogRepository implements ProductCatalogRepository {

	private Map<String, ProductCatalog> productRepository;

	public InMemoryProductCatalogRepository() {
		this.productRepository = new HashMap<String, ProductCatalog>();

	}

	@Override
	public ProductCatalog find(String type) {
		return productRepository.get(type);
	}

	@Override
	public ProductCatalog save(String type, ProductCatalog productCatalog) {
		return productRepository.put(type, productCatalog);
	}

	@Override
	public boolean exists(String type) {
		return productRepository.containsKey(type);
	}

	@Override
	public List<ProductCatalog> findAll() {
		Collection<ProductCatalog> values = productRepository.values();
		if (values != null)
			return new ArrayList<>(values);
		return null;
	}

}
