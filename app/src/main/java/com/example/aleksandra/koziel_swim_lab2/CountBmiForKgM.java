package com.example.aleksandra.koziel_swim_lab2;

/**
 * Created by Aleksandra on 15.03.2017.
 */

public class CountBmiForKgM implements ICountBmi
{
    static final float minMass = 10;
    static final float maxMass = 250;
    static final float minHeight =0.5f;
    static final float maxHeight = 2.5f;
    static final float metrsToFeets = 3.2808399f;
    static final float kgToPounds =2.20462262f;

    public boolean isMassValid(float mass)
    {
        return ((mass<maxMass)&&(mass>minMass));
    }
    public boolean isHeightValid(float height)
    {
        return ((height<maxHeight)&&(height>minHeight));
    }
    public float countBmi(float mass, float height)
    {

        if (isMassValid(mass)&&isHeightValid(height))
        {
            float bmi = mass / (height * height);
            return bmi;
        }
        else
        {
            throw new IllegalArgumentException("WRONG DATA");
        }
    }
    public float countMassImperial(float mass)
    {
        mass = mass/kgToPounds;
        return mass;
    }
    public float countHeightImperial(float height)
    {
        height = height/metrsToFeets;
        return height;
    }
}
