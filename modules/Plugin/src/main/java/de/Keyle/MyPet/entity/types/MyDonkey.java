/*
 * This file is part of MyPet
 *
 * Copyright © 2011-2018 Keyle
 * MyPet is licensed under the GNU Lesser General Public License.
 *
 * MyPet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyPet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package de.Keyle.MyPet.entity.types;

import de.Keyle.MyPet.MyPetApi;
import de.Keyle.MyPet.api.entity.MyPetType;
import de.Keyle.MyPet.api.player.MyPetPlayer;
import de.Keyle.MyPet.entity.MyPet;
import de.keyle.knbt.TagByte;
import de.keyle.knbt.TagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MyDonkey extends MyPet implements de.Keyle.MyPet.api.entity.types.MyDonkey {
    public boolean baby = false;
    public ItemStack chest = null;
    public ItemStack saddle = null;

    public MyDonkey(MyPetPlayer petOwner) {
        super(petOwner);
    }

    public ItemStack getChest() {
        return chest;
    }

    public boolean hasChest() {
        return chest != null;
    }

    public void setChest(ItemStack item) {
        if (item != null && item.getType() != Material.CHEST && item.getType() != Material.TRAPPED_CHEST) {
            return;
        }
        this.chest = item;
        if (this.chest != null) {
            this.chest.setAmount(1);
        }
        if (status == PetState.Here) {
            getEntity().get().getHandle().updateVisuals();
        }
    }

    public ItemStack getSaddle() {
        return saddle;
    }

    public boolean hasSaddle() {
        return saddle != null;
    }

    public void setSaddle(ItemStack item) {
        if (item != null && item.getType() != Material.SADDLE) {
            return;
        }
        this.saddle = item;
        if (this.saddle != null) {
            this.saddle.setAmount(1);
        }
        if (status == PetState.Here) {
            getEntity().get().getHandle().updateVisuals();
        }
    }

    @Override
    public TagCompound writeExtendedInfo() {
        TagCompound info = super.writeExtendedInfo();
        info.getCompoundData().put("Baby", new TagByte(isBaby()));
        if (hasChest()) {
            info.getCompoundData().put("Chest", MyPetApi.getPlatformHelper().itemStackToCompund(getChest()));
        }
        if (hasSaddle()) {
            info.getCompoundData().put("Saddle", MyPetApi.getPlatformHelper().itemStackToCompund(getSaddle()));
        }
        return info;
    }

    @Override
    public void readExtendedInfo(TagCompound info) {
        if (info.getCompoundData().containsKey("Baby")) {
            setBaby(info.getAs("Baby", TagByte.class).getBooleanData());
        }
        if (info.containsKeyAs("Chest", TagByte.class)) {
            boolean chest = info.getAs("Chest", TagByte.class).getBooleanData();
            if (chest) {
                ItemStack item = new ItemStack(Material.CHEST);
                setChest(item);
            }
        } else if (info.containsKeyAs("Chest", TagCompound.class)) {
            TagCompound itemTag = info.get("Chest");
            ItemStack item = MyPetApi.getPlatformHelper().compundToItemStack(itemTag);
            setChest(item);
        }
        if (info.containsKeyAs("Saddle", TagByte.class)) {
            boolean saddle = info.getAs("Saddle", TagByte.class).getBooleanData();
            if (saddle) {
                ItemStack item = new ItemStack(Material.SADDLE);
                setSaddle(item);
            }
        } else if (info.containsKeyAs("Saddle", TagCompound.class)) {
            TagCompound itemTag = info.get("Saddle");
            ItemStack item = MyPetApi.getPlatformHelper().compundToItemStack(itemTag);
            setSaddle(item);
        }
    }

    @Override
    public MyPetType getPetType() {
        return MyPetType.Donkey;
    }

    @Override
    public boolean isBaby() {
        return baby;
    }

    public void setBaby(boolean flag) {
        this.baby = flag;
        if (status == PetState.Here) {
            getEntity().get().getHandle().updateVisuals();
        }
    }

    @Override
    public String toString() {
        return "MyMule{owner=" + getOwner().getName() + ", name=" + ChatColor.stripColor(petName) + ", exp=" + experience.getExp() + "/" + experience.getRequiredExp() + ", lv=" + experience.getLevel() + ", status=" + status.name() + ", skilltree=" + (skilltree != null ? skilltree.getName() : "-") + ", worldgroup=" + worldGroup + ", chest=" + chest + "}";
    }
}