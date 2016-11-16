package com.jam01.littlelight.adapter.common.presentation;

import com.jam01.littlelight.domain.activity.Account;
import com.jam01.littlelight.domain.legend.Legend;

/**
 * Created by jam01 on 11/16/16.
 */

public class ActivitiesDPO {
    public final Legend legend;
    public final Account activities;

    public ActivitiesDPO(Legend legend, Account activities) {
        this.legend = legend;
        this.activities = activities;
    }
}
