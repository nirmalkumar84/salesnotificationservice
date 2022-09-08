package com.jpmc.salesnotification.bean;

import com.jpmc.salesnotification.util.Operations;
/**
 * Bean class for storing all sale types 1/2/3
 * @author Nirmal
 *
 */
public class SalesType 
{
	private ProductCatalog productCatalog;

	private Operations operations;

	public ProductCatalog getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(ProductCatalog productCatalog) {
		this.productCatalog = productCatalog;
	}

	public Operations getOperation() {
		return operations;
	}

	public void setOperations(Operations operations) {
		this.operations = operations;
	}

	@Override
	public String toString() {
		return String.format("%-15s%-15s%-12.2f%-14d%-12.2f", operations, productCatalog.getType(),
				productCatalog.getAdjustment().setScale(2), productCatalog.getQuantity(),
				productCatalog.getEnchriedPrice().setScale(2));
	}
}
