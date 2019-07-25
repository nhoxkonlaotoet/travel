package com.example.administrator.travel;

public class FormatMoney {
    public static String formatToString(int money){
        String priceString = "";
        while (money > 1000) {
            priceString = "." + ((money % 1000)==0?"000":(money%1000) + priceString);
            money /= 1000;
        }
        priceString = money+priceString;
        return priceString;
    }
}
