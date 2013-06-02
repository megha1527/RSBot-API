package org.powerbot.script.methods.tabs;

import java.util.Arrays;

import org.powerbot.script.methods.Game;
import org.powerbot.script.methods.Widgets;
import org.powerbot.script.util.Filter;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Item;

public class Inventory {
	public static final int WIDGET = 679;
	public static final int WIDGET_BANK = 763;
	public static final int WIDGET_PRICE_CHECK = 204;
	public static final int WIDGET_EQUIPMENT_BONUSES = 670;
	public static final int WIDGET_EXCHANGE = 644;
	public static final int WIDGET_SHOP = 621;
	public static final int WIDGET_DUNGEONEERING_SHOP = 957;
	public static final int WIDGET_BEAST_OF_BURDEN_STORAGE = 665;
	public static final int WIDGET_STORE = 1266;
	public static final int WIDGET_SAWMILL_CART = 771;
	private static final int[] ALTERNATIVE_WIDGETS = {
			WIDGET_BANK,
			WIDGET_PRICE_CHECK, WIDGET_EQUIPMENT_BONUSES,
			WIDGET_EXCHANGE, WIDGET_SHOP, WIDGET_DUNGEONEERING_SHOP,
			WIDGET_BEAST_OF_BURDEN_STORAGE, WIDGET_STORE, WIDGET_SAWMILL_CART
	};

	public static Item[] getItems() {
		final Item[] items = new Item[28];
		final Component inv = getComponent();
		if (inv == null) return items;
		int d = 0;
		final Component[] comps = inv.getChildren();
		if (comps.length > 27) for (int i = 0; i < 28; i++) {
			if (comps[i].getItemId() != -1) items[d++] = new Item(comps[i]);
		}
		return Arrays.copyOf(items, d);
	}

	public static Item[] getAllItems() {
		final Item[] items = new Item[28];
		final Component inv = getComponent();
		if (inv == null) return items;
		final Component[] comps = inv.getChildren();
		if (comps.length > 27) for (int i = 0; i < 28; i++) {
			items[i] = new Item(comps[i]);
		}
		return items;
	}

	public static Item getItemAt(final int index) {
		final Component inv = getComponent();
		if (inv == null) return null;
		final Component[] comps = inv.getChildren();
		return index >= 0 && index < 28 && comps.length > 27 && comps[index].getItemId() != -1 ? new Item(comps[index]) : null;
	}

	public static Item[] getItems(final Filter<Item> filter) {
		final Item[] items = getItems();
		final Item[] set = new Item[items.length];
		int d = 0;
		for (final Item item : items) if (filter.accept(item)) set[d++] = item;
		return Arrays.copyOf(set, d);
	}

	public static Item[] getItems(final int... ids) {
		return getItems(new Filter<Item>() {
			@Override
			public boolean accept(final Item item) {
				final int _id = item.getId();
				for (final int id : ids) if (id == _id) return true;
				return false;
			}
		});
	}

	public static Item getItem(final Filter<Item> filter) {
		final Item[] items = getItems(filter);
		return items != null && items.length > 0 ? items[0] : null;
	}

	public static Item getItem(final int... ids) {
		return getItem(new Filter<Item>() {
			@Override
			public boolean accept(final Item item) {
				final int _id = item.getId();
				for (final int id : ids) if (id == _id) return true;
				return false;
			}
		});
	}

	public static int getSelectedItemIndex() {
		final Component inv = getComponent();
		if (inv == null) return -1;
		final Component[] comps = inv.getChildren();
		if (comps.length > 27) for (int i = 0; i < 28; i++) {
			if (comps[i].getBorderThickness() == 2) return i;
		}
		return -1;
	}

	public static Item getSelectedItem() {
		return getItemAt(getSelectedItemIndex());
	}

	public static boolean isItemSelected() {
		return getSelectedItemIndex() != -1;
	}

	public static int indexOf(final int id) {
		final Component inv = getComponent();
		if (inv == null) return -1;
		final Component[] comps = inv.getChildren();
		if (comps.length > 27) for (int i = 0; i < 28; i++) if (comps[i].getItemId() == id) return i;
		return -1;
	}

	public static boolean contains(final int id) {
		return indexOf(id) != -1;
	}

	public static boolean containsAll(final int... ids) {
		for (final int id : ids) if (indexOf(id) == -1) return false;
		return true;
	}

	public static boolean containsOneOf(final int... ids) {
		for (final int id : ids) if (indexOf(id) != -1) return true;
		return false;
	}

	public static int getCount() {
		return getCount(false);
	}

	public static int getCount(final boolean stacks) {
		int count = 0;
		final Component inv = getComponent();
		if (inv == null) return 0;
		final Component[] comps = inv.getChildren();
		if (comps.length > 27) for (int i = 0; i < 28; i++) {
			if (comps[i].getItemId() != -1) if (stacks) count += comps[i].getItemStackSize();
			else ++count;
		}
		return count;
	}

	public static int getCount(final int... ids) {
		return getCount(false, ids);
	}

	public static int getCount(final boolean stacks, final int... ids) {
		int count = 0;
		final Component inv = getComponent();
		if (inv == null) return 0;
		final Component[] comps = inv.getChildren();
		if (comps.length > 27) for (int i = 0; i < 28; i++) {
			for (final int id : ids) {
				if (comps[i].getItemId() == id) {
					if (stacks) count += comps[i].getItemStackSize();
					else ++count;
					break;
				}
			}
		}
		return count;
	}

	public static boolean isFull() {
		return getCount() == 28;
	}

	public static boolean isEmpty() {
		return getCount() == 0;
	}

	private static Component getComponent() {
		Component c;
		for (final int index : ALTERNATIVE_WIDGETS) if ((c = Widgets.get(index, 0)) != null && c.isValid()) return c;
		Game.openTab(Game.TAB_INVENTORY);
		return Widgets.get(WIDGET, 0);
	}
}
