package com.magni5.m5tracker.asynctask;

import com.magni5.m5tracker.models.Model;

/**
 * Created by Shankar on 3/7/2017.
 */

public interface IAsyncCaller {
    void onComplete(Model model);
}
