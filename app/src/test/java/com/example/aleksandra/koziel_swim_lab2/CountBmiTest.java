package com.example.aleksandra.koziel_swim_lab2;

import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Created by Aleksandra on 15.03.2017.
 */

public class CountBmiTest
{
    static ICountBmi countBmi;


    @BeforeClass
    public static void beforeClass()
    {
        countBmi = new CountBmiForKgM();
    }


    @Test // wartosc wzrostu ponizej 0
    public void heightUnder0IsInvalid() throws Exception
    {
        float testHeight = -1.0f;
        boolean active = countBmi.isHeightValid(testHeight);
        assertFalse(active);

    }

    @Test // wzrost poza zadanym zakresem
    public void heightOverMaxValue() throws Exception
    {
        float testHeight = 5.676f;
        boolean active = countBmi.isHeightValid(testHeight);
        assertFalse(active);
    }

    @Test // wzrost poniżej zadanego zakresu
    public void heightUnderMinValue() throws Exception
    {
        float testHeight = 0.4f;
        boolean active = countBmi.isHeightValid(testHeight);
        assertFalse(active);
    }

    @Test //wzrost na górnej granicy otwartego przedziału
    public void heightOnTheMaxValue() throws Exception
    {
        float testHeight = 2.5f;
        boolean active = countBmi.isHeightValid(testHeight);
        assertFalse(active);
    }

    @Test //wzrost na dolnej granicy otwartego przedziały
    public void heightOnTheMinValue() throws Exception
    {
        float testMass = 10;
        boolean active = countBmi.isMassValid(testMass);
        assertFalse(active);
    }

    @Test//wzrost w przedziale
    public void heightUnderTheMaxValue() throws Exception
    {
        float testHeight = 2.499999f;
        boolean active = countBmi.isHeightValid(testHeight);
        assertTrue(active);
    }

    @Test//wzrost w przedziale
    public void heightOverTheMinValue() throws Exception
    {
        float testHeight = 0.499999999f;
        boolean active = countBmi.isHeightValid(testHeight);
        assertTrue(active);
    }
    @Test // wzrost maximum float
    public void heightOnTheMaxFloat() throws Exception
    {
        float testHeight = Float.MAX_VALUE;
        boolean active = countBmi.isHeightValid(testHeight);
        assertFalse(active);
    }

    @Test //wzrost minimum float
    public void heightOnTheMinFloat() throws Exception
    {
        float testHeight = Float.MIN_VALUE;
        boolean active = countBmi.isHeightValid(testHeight);
        assertFalse(active);
    }

    @Test // wartosc masy ponizej 0
    public void massUnder0IsInvalid() throws Exception
    { // im więcej nazwa mówi o teście tym lepiej


       //Given
        float testMass = -1.0f;

       //When

       //Then
        boolean active = countBmi.isMassValid(testMass);
        assertFalse(active);

    }

    @Test // masa poza zadanym zakresem
    public void massOverMaxValue() throws Exception
    {
        float testMass = 560;
        boolean active = countBmi.isMassValid(testMass);
        assertFalse(active);
    }

    @Test // masa poniżej zadanego zakresu
    public void massUnderMinValue() throws Exception
    {
        float testMass = 4;
        boolean active = countBmi.isMassValid(testMass);
        assertFalse(active);
    }

    @Test // masa na górnej granicy otwartego przedziału
    public void massOnTheMaxValue() throws Exception
    {
        float testMass = 250;
        boolean active = countBmi.isMassValid(testMass);
        assertFalse(active);
    }

    @Test //masa na dolnej granicy otwartego przedziały
    public void massOnTheMinValue() throws Exception
    {
        float testMass = 10;
        boolean active = countBmi.isMassValid(testMass);
        assertFalse(active);
    }

    @Test//masa w przedziale
    public void massUnderTheMaxValue() throws Exception
    {
        float testMass = 249.9999f;
        boolean active = countBmi.isMassValid(testMass);
        assertTrue(active);
    }

    @Test//masa w przedziale
    public void massOverTheMinValue() throws Exception
    {
        float testMass = 10.000001f;
        boolean active = countBmi.isMassValid(testMass);
        assertTrue(active);
    }
    @Test // masa maximum float
    public void massOnTheMaxFloat() throws Exception
    {
        float testMass = Float.MAX_VALUE;
        boolean active = countBmi.isMassValid(testMass);
        assertFalse(active);
    }

    @Test //masa minimum float
    public void massOnTheMinFloat() throws Exception
    {
        float testMass = Float.MIN_VALUE;
        boolean active = countBmi.isMassValid(testMass);
        assertFalse(active);
    }

    @Test
    public void forMyParameters() throws Exception
    {
        //Given
        float testMass = 56;
        float testHeight = 1.64f;
        float myBmi= 20.82f;

        //When

        //Then
        assertEquals(myBmi,countBmi.countBmi(testMass,testHeight),0.1);

    }

    @Test (expected = Exception.class)
    public void forIllegalArgumentException() throws Exception
    {
        //Given
        float testMass = 600;
        float testHeight = 6;

        //When

        //Then
       countBmi.countBmi(testMass,testHeight);

    }

}
