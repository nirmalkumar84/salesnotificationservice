package com.jpmc.salesnotification.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
/**
 * Bean classes for product
 * @author Nirmal
 *
 */
public class ProductCatalog 
{
	private String type;
	
	private BigDecimal originalPrice;
	
	private int quantity;
	
	private BigDecimal adjustment;
	
	private BigDecimal enchriedPrice;
	
	public ProductCatalog() 
	{
		this.quantity=1;
		this.enchriedPrice = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
	}
	
	public ProductCatalog(String type , BigDecimal originalPrice , int quantity)
	{
		this.type = type;
		this.originalPrice = originalPrice;
		this.quantity = quantity;
		this.enchriedPrice = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
	}
	
	public String getType() 
	{
		return type;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public BigDecimal getOriginalPrice() 
	{
		return originalPrice;
	}
	
	public void setOriginalPrice(BigDecimal originalPrice) 
	{
		this.originalPrice = originalPrice;
	}
	
	public int getQuantity() 
	{
		return quantity;
	}
	public void setQuantity(int quantity) 
	{
		this.quantity = quantity;
	}
	
	
	public BigDecimal getAdjustment() 
	{
		return adjustment;
	}
	
	public void setAdjustment(BigDecimal adjustment) 
	{
		this.adjustment = adjustment;
	}
	
	public BigDecimal getEnchriedPrice() 
	{
		return enchriedPrice;
	}
	
	public void setEnchriedPrice(BigDecimal enchriedPrice) 
	{
		this.enchriedPrice = enchriedPrice;
	}
	
	@Override
	public String toString() 
	{
		return String.format("%-42s%-18d%-18.2f", type, quantity, enchriedPrice);
	}
}
