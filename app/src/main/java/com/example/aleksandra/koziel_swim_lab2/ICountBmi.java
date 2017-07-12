package com.example.aleksandra.koziel_swim_lab2;

/**
 * Created by Aleksandra on 15.03.2017.
 */

public interface ICountBmi
{
    boolean isMassValid(float mass);
    boolean isHeightValid(float height);
    float countBmi(float mass, float height);
    float countMassImperial(float mass);
    float countHeightImperial(float height);
}
