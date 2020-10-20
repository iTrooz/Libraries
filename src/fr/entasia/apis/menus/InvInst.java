package fr.entasia.apis.menus;

import org.bukkit.inventory.Inventory;

public class InvInst {
    public Inventory inv;
    public Object data;

    public InvInst(Inventory inv, Object data) {
        this.inv = inv;
        this.data = data;
    }
}
