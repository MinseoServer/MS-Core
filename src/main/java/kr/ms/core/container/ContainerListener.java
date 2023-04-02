package kr.ms.core.container;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

public class ContainerListener implements Listener {

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        MSContainer container = getSTContainer(event.getView());
        if(container != null) container.$click(event);
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        MSContainer container = getSTContainer(event.getView());
        if(container != null) container.$close(event);
    }

    @EventHandler
    public void onDragInventory(InventoryDragEvent event) {
        MSContainer container = getSTContainer(event.getView());
        if(container != null) container.$drag(event);
    }

    private MSContainer getSTContainer(InventoryView view) {
        if(view == null) return null;
        InventoryHolder topHolder = view.getTopInventory().getHolder();
        if(topHolder == null) return null;
        return topHolder instanceof MSContainer ? (MSContainer) topHolder : null;
    }

}
