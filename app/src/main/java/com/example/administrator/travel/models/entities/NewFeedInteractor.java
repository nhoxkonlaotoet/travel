package com.example.administrator.travel.models.entities;

import java.util.List;

/**
 * Created by Administrator on 30/10/2018.
 */

public interface NewFeedInteractor {
    public List<Tour> getTour(final OnGetNewFeedItemFinishedListener listener);

}
