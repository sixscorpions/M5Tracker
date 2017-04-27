package com.magni5.m5tracker.parsers;

import com.magni5.m5tracker.models.Model;
import com.magni5.m5tracker.models.SigninModel;

/**
 * Created by Manikanta on 4/27/2017.
 */

public class SignInParser implements Parser<Model> {
    SigninModel mSigninModel = new SigninModel();
    @Override
    public Model parse(String s) {
        return mSigninModel;
    }
}
