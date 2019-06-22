package com.example.administrator.travel.models.bases;

import android.content.Context;

import com.example.administrator.travel.models.entities.BankCard;
import com.example.administrator.travel.models.listeners.Listener;

/**
 * Created by Admin on 6/10/2019.
 */

public interface BankInteractor {
    void checkValidCard(BankCard bankCard, Context context, Listener.OnCheckValidCardFinishedListener listener);
}
