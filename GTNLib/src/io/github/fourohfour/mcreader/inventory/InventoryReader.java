package io.github.fourohfour.mcreader.inventory;

import io.github.fourohfour.gtnlib.GTNReader;
import io.github.fourohfour.gtnlib.GroupedTagItem;
import io.github.fourohfour.gtnlib.GroupedTagList;
import io.github.fourohfour.gtnlib.InvalidFileException;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class InventoryReader implements GTNReader<SimpleEntry<InventorySlot, ItemStack>, Map<InventorySlot, ItemStack>>{
	
	public Player setInventory(GroupedTagList list, Player s, String n){
		Map<InventorySlot, ItemStack> m = this.multipleAccumulator(list, n);
		s.getInventory().clear();
		for(int i = 0; i < m.size(); i++){
			InventorySlot key = (InventorySlot) m.keySet().toArray()[i];
			if(key.isSpecial()){
				SpecialSlot sslot = key.getSpecialSlot();

				if (sslot == SpecialSlot.HEAD){
					s.getInventory().setHelmet(m.get(key));
				}
				else if (sslot == SpecialSlot.CHEST){
					s.getInventory().setChestplate(m.get(key));
				}
				else if (sslot == SpecialSlot.LEGS){
					s.getInventory().setLeggings(m.get(key));
				}
				else if (sslot == SpecialSlot.FEET){
					s.getInventory().setBoots(m.get(key));
				}
				
				
			}
			else{
				s.getInventory().setItem(key.getIntegerSlot(), m.get(key));
			}
		}
		
		return s;
	}
	

	@Override
	public SimpleEntry<InventorySlot, ItemStack> singleAccumulator(GroupedTagList list, String name) {
		try {
			return reader(list.getItems(name).iterator().next());
		} catch (InvalidFileException e) {
			e.printWarning();
		}
		return null;
	}
	@Override
	public Map<InventorySlot, ItemStack> multipleAccumulator(GroupedTagList list, String name) {
		Set<GroupedTagItem> iset = list.getItems(name);
		Map<InventorySlot, ItemStack> itemset = new HashMap<>();
		for (GroupedTagItem i : iset){
			SimpleEntry<InventorySlot, ItemStack>  kv = null;
			try {
				kv = reader(i);
			} catch (InvalidFileException e) {
				e.printWarning();
			}
			itemset.put(kv.getKey(), kv.getValue());
		}
		return itemset;
	}
	@Override
	public SimpleEntry<InventorySlot, ItemStack> reader(GroupedTagItem i) throws InvalidFileException {
		ItemStack item = null;
		InventorySlot slot;
		
		//Get Type
		if(i.hasTag("t")){
		    try{
		        Material iname = Material.valueOf(i.getTag("t"));
		        item = new ItemStack(iname);
		    }
		    catch (IllegalArgumentException e){
		    	throw new InvalidFileException(3);
		    }
		}
		else{
			throw new InvalidFileException(2);
		}
		
		
		//Get Slot
		if(i.hasTag("b")){
		    try{
		    	Integer slint = Integer.valueOf(i.getTag("b"));
		    	slot = new InventorySlot(slint);
		    }
		    catch (NumberFormatException e){
		    	throw new InvalidFileException(3);
		    }
		}
		else if (i.hasTag("sb")){
		    try{
		        SpecialSlot ss = SpecialSlot.valueOf(i.getTag("sb"));
		    	slot = new InventorySlot(ss);
		    }
		    catch (IllegalArgumentException e){
		    	throw new InvalidFileException(3);
		    }
		}
		else{
			throw new InvalidFileException(2);
		}
		
		//Get Type
		if(i.hasTag("s")){
		    try{
		        Integer sint = Integer.valueOf(i.getTag("s"));
		        item.setAmount(sint);
		    }
		    catch (IllegalArgumentException e){
		    	throw new InvalidFileException(3);
		    }
		}
		else{
			throw new InvalidFileException(2);
		}
		
		
		//Get Name
		if(i.hasTag("n")){
			ItemMeta m = item.getItemMeta();
			m.setDisplayName(i.getTag("n"));
			item.setItemMeta(m);
		}
		
		
		//Get Durability
		if(i.hasTag("d")){
			try{
			    item.setDurability(Short.valueOf(i.getTag("d")));
			}
			catch (NumberFormatException e){
			    throw new InvalidFileException(3);
			}
		}
		
		//Get Enchants
		if(i.hasTag("e")){
			String v = i.getTag("e");
			String[] ench = v.split(",");
			for(String e : ench){
				String[] kv = e.split("-");
				Enchantment enchant = Enchantment.getByName(kv[0]);
				Integer lvl = Integer.valueOf(kv[1]);
				item.addUnsafeEnchantment(enchant, lvl);
			}
		}
		
		//Get Colour
		if(i.hasTag("c")){
			String rgb = i.getTag("c");
			String[] cvalues = rgb.split(",");
			
			int red = Integer.valueOf(cvalues[0]);
			int green = Integer.valueOf(cvalues[1]);
			int blue = Integer.valueOf(cvalues[2]);
			
			if(isLeatherArmour(item)){
				LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
				meta.setColor(Color.fromRGB(red, green, blue));
				item.setItemMeta(meta);
			}
			else{
				throw new InvalidFileException(5);
			}
		}
		return new SimpleEntry<InventorySlot, ItemStack>(slot, item);
	}
	
	private static boolean isLeatherArmour(ItemStack i){
		Material m = i.getType();
		if(
				(m == Material.LEATHER_BOOTS) ||
				(m == Material.LEATHER_CHESTPLATE) ||
				(m == Material.LEATHER_HELMET) ||
				(m == Material.LEATHER_LEGGINGS)
				
				){
			return true;
		}
		return false;
	}
}
