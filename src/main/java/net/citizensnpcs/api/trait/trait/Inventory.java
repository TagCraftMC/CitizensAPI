package net.citizensnpcs.api.trait.trait;

import net.citizensnpcs.api.exception.NPCLoadException;
import net.citizensnpcs.api.trait.SaveId;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import net.citizensnpcs.api.util.ItemBuilder;

import org.bukkit.inventory.ItemStack;

/**
 * Represents an NPC's inventory
 */
@SaveId("inventory")
public class Inventory extends Trait {
    private ItemStack[] contents;

    public Inventory() {
        contents = new ItemStack[36];
    }

    public Inventory(org.bukkit.inventory.Inventory inventory) {
        contents = inventory.getContents();
    }

    /**
     * Gets the contents of an NPC's inventory
     * 
     * @return ItemStack array of an NPC's inventory contents
     */
    public ItemStack[] getContents() {
        return contents;
    }

    /**
     * Sets the contents of an NPC's inventory
     * 
     * @param contents
     *            ItemStack array to set as the contents of an NPC's inventory
     */
    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }

    @Override
    public void load(DataKey key) throws NPCLoadException {
        contents = parseContents(key);
    }

    @Override
    public void save(DataKey key) {
        int slot = 0;
        for (ItemStack item : contents) {
            // Clear previous items to avoid conflicts
            key.removeKey(String.valueOf(slot));
            if (item != null)
                ItemBuilder.saveItem(item, key.getRelative(String.valueOf(slot)));
            slot++;
        }
    }

    private ItemStack[] parseContents(DataKey key) throws NPCLoadException {
        ItemStack[] contents = new ItemStack[36];
        for (DataKey slotKey : key.getIntegerSubKeys())
            contents[Integer.parseInt(slotKey.name())] = ItemBuilder.getItemStack(slotKey);
        return contents;
    }

    @Override
    public String toString() {
        return "Inventory{" + contents + "}";
    }
}