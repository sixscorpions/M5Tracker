package com.magni5.m5tracker.parsers;


import com.magni5.m5tracker.models.Model;

/**
 * Created by Shankar Rao on 3/28/2016.
 */
public interface Parser<T extends Model> {

    T parse(String s);
}