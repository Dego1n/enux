package com.gameserver.template.buylist;

import java.util.List;

public class BuyList {
    private final int _buyListId;
    private final int _currencyId;
    private final List<Integer> _allowedNpcIdList;
    private final List<BuyListItem> _items;

    public static class BuyListItem{
        private final int _itemId;
        private final int _price;

        public BuyListItem(int itemId, int price) {
            this._itemId = itemId;
            this._price = price;
        }

        public int getItemId() {
            return _itemId;
        }

        public int getPrice() {
            return _price;
        }
    }

    public BuyList(int buyListId, int currencyId, List<Integer> allowedNpcIdList, List<BuyListItem> items) {
        this._buyListId = buyListId;
        this._currencyId = currencyId;
        this._allowedNpcIdList = allowedNpcIdList;
        this._items = items;
    }

    public int getBuyListId() {
        return _buyListId;
    }

    public int getCurrencyId() {
        return _currencyId;
    }

    public List<Integer> get_allowedNpcIdList() {
        return _allowedNpcIdList;
    }

    public List<BuyListItem> getItems() {
        return _items;
    }

    public int getPriceForItem(int item_id)
    {
        for(BuyListItem buyListItem : getItems())
        {
            if(buyListItem.getItemId() == item_id)
                return buyListItem.getPrice();
        }
        return -1;
    }
}
