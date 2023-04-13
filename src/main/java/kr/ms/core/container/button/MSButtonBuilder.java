package kr.ms.core.container.button;

import kr.ms.core.container.MSContainer;
import kr.ms.core.container.wrapper.ButtonClickEventWrapper;
import kr.ms.core.util.PlayerSkullManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class MSButtonBuilder {

    private final MSButton button;
    private MSButtonBuilder() {
        button = new MSButton();
    }

    public MSButtonBuilder(ItemStack itemStack) {
        this();
        button.itemStack = itemStack.clone();
        if(itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) button.lore = itemStack.getItemMeta().getLore();
    }
    public MSButtonBuilder(Material material) {
        this();
        button.itemStack = new ItemStack(material);
    }
    public MSButtonBuilder(Material material, int amount) {
        this();
        button.itemStack = new ItemStack(material, amount);
    }
    public MSButtonBuilder(String url) {
        this();
        button.itemStack = PlayerSkullManager.getCustomSkull(url);
    }
    public MSButtonBuilder(Player player) {
        this();
        button.itemStack = PlayerSkullManager.getPlayerSkull(player.getUniqueId());
    }
    public MSButtonBuilder(UUID uniqueId) {
        this();
        button.itemStack = PlayerSkullManager.getPlayerSkull(uniqueId);
    }

    public MSButtonBuilder setDisplayName(String displayName) {
        button.displayName = displayName;
        return this;
    }

    public MSButtonBuilder setLore(List<String> lore) {
        button.lore = lore;
        return this;
    }

    public MSButtonBuilder setLore(String... lore) {
        button.lore = Arrays.stream(lore).collect(Collectors.toList());
        return this;
    }

    public MSButtonBuilder setGlow(boolean glow) {
        button.isGlow = glow;
        return this;
    }

    public MSButtonBuilder setCleanable(boolean cleanable) {
        button.isCleanable = cleanable;
        return this;
    }

    public MSButtonBuilder setCancelled(boolean cancel) {
        button.isCancelled = cancel;
        return this;
    }

    public MSButtonBuilder setClickFunction(BiConsumer<ButtonClickEventWrapper, MSContainer> func) {
        button.function = func;
        return this;
    }

    public MSButton build() {
        return button.apply();
    }
}
