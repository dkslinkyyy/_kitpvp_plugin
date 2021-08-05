package eu.tribusmc.tribuskitpvp.base.economy;

import eu.tribusmc.tribuskitpvp.base.player.TMCPlayer;

public class Purchasable {

    private int price;

    public Purchasable(int price) {
        this.price = price;
    }

    public void openBuyMenu() {

    }


    private boolean canAfford(TMCPlayer tmcPlayer) {
        return tmcPlayer.getCoins() >= price;
    }




    public boolean whenBought(TMCPlayer tmcPlayer, IPurchase purchase) {
        if(!canAfford(tmcPlayer)) {
            tmcPlayer.getPlayer().sendMessage(purchase.getCanAffordMessage(tmcPlayer));
            return false;
        }

        if(purchase.hasBought(tmcPlayer)) {
            tmcPlayer.getPlayer().sendMessage(purchase.getHasBoughtMessage(tmcPlayer));
            return false;
        }

        purchase.buying(price, tmcPlayer.getCoins(), price- tmcPlayer.getCoins(), tmcPlayer);

        return true;
    }

    public static interface IPurchase {
        void buying(int price, int moneyInPocket, int difference, TMCPlayer tmcPlayer);

        String getCanAffordMessage(TMCPlayer tmcPlayer);

        String getHasBoughtMessage(TMCPlayer tmcPlayer);

        boolean hasBought(TMCPlayer tmcPlayer);

    }



}
