package io.github.fourohfour.mcreader.inventory;

import org.apache.commons.lang.builder.EqualsBuilder;

public class InventorySlot {
	private Integer slot;
	private SpecialSlot specials;
	private int type;
	
	public InventorySlot(int s){
		this.slot = s;
		this.specials = null;
		this.type = 0;
	}
	
	public InventorySlot(SpecialSlot s){
		this.specials = s;
		this.slot = null;
		this.type = 1;
	}
	
	public boolean isSpecial(){
		if (this.type == 1){
			return true;
		}
		return false;
	}
	
	public Integer getIntegerSlot(){
		return this.slot;
	}
	
	public SpecialSlot getSpecialSlot(){
		return this.specials;
	}
	
	@Override
	public boolean equals(Object obj){
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof InventorySlot))
            return false;
        
        InventorySlot newo = (InventorySlot) obj;
		return new EqualsBuilder().append(slot, newo.slot).append(specials, newo.specials).isEquals();
	}
}
