package kr.ms.core.container.button;

import kr.ms.core.container.wrapper.ButtonClickEventWrapper;
import lombok.Getter;
import kr.ms.core.container.MSContainer;
import kr.ms.core.util.PlayerSkullManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class MSButton {

    @Getter protected boolean isGlow;
    @Getter protected boolean isCleanable;
    @Getter protected boolean isCancelled;
    protected BiConsumer<ButtonClickEventWrapper, MSContainer> function;
    protected ItemStack itemStack;
    protected String displayName;
    protected List<String> lore;

    protected MSButton() {
        isGlow = false;
        isCleanable = true;
        isCancelled = false;
        displayName = null;
        lore = null;
    }

    protected MSButton apply() {
        ItemMeta meta = itemStack.getItemMeta();
        if(displayName != null) meta.setDisplayName(displayName);
        if(lore != null) meta.setLore(lore);
        if(isGlow) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        if(isGlow) itemStack.addUnsafeEnchantment(Enchantment.LURE, 1);
        return this;
    }

    public ItemStack getOriginalItemStack() { return  itemStack; }
    public ItemStack getItemStack() {return itemStack.clone(); }
    public String getDisplayName() { return displayName == null ? "" : displayName; }
    public List<String> getLore() { return lore == null ? Collections.emptyList() : lore; }

    public void setSlot(MSContainer container, int... slots) {
        for(int slot: slots)
            container.registerButton(slot, this);
    }

    public void execute(MSContainer container, ButtonClickEventWrapper wrapper) {
        if(function == null) return;
        function.accept(wrapper, container);
    }

    /**
     * {@link MSButtonBuilder}
     */
    @Deprecated
    public static class STButtonBuilder {

        private MSButton button;

        private STButtonBuilder() {
            button = new MSButton();
        }

        public STButtonBuilder(ItemStack itemStack) {
            this();
            button.itemStack = itemStack.clone();
            if(itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) button.lore = itemStack.getItemMeta().getLore();
        }
        public STButtonBuilder(Material material) {
            this();
            button.itemStack = new ItemStack(material);
        }
        public STButtonBuilder(Material material, int amount) {
            this();
            button.itemStack = new ItemStack(material, amount);
        }
        public STButtonBuilder(String url) {
            this();
            button.itemStack = PlayerSkullManager.getCustomSkull(url);
        }
        public STButtonBuilder(Player player) {
            this();
            button.itemStack = PlayerSkullManager.getPlayerSkull(player.getUniqueId());
        }
        public STButtonBuilder(UUID uniqueId) {
            this();
            button.itemStack = PlayerSkullManager.getPlayerSkull(uniqueId);
        }

        public STButtonBuilder setDisplayName(String displayName) {
            button.displayName = displayName;
            return this;
        }

        public STButtonBuilder setLore(List<String> lore) {
            button.lore = lore;
            return this;
        }

        public STButtonBuilder setLore(String... lore) {
            button.lore = Arrays.stream(lore).collect(Collectors.toList());
            return this;
        }

        public STButtonBuilder setGlow(boolean glow) {
            button.isGlow = glow;
            return this;
        }

        public STButtonBuilder setCleanable(boolean cleanable) {
            button.isCleanable = cleanable;
            return this;
        }

        public STButtonBuilder setCancelled(boolean cancel) {
            button.isCancelled = cancel;
            return this;
        }

        public STButtonBuilder setClickFunction(BiConsumer<ButtonClickEventWrapper, MSContainer> func) {
            button.function = func;
            return this;
        }

        public MSButton build() {
            return button.apply();
        }
    }

}
