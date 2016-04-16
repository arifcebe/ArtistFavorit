package com.arifcebe.artistdemenan;

import com.arifcebe.artistdemenan.config.Utils;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by arifcebe on 28/03/16.
 */
public class ConvertUtilsTest {

    @Test
    public void testConvertFahrenheitToCelcius(){
        float actual = Utils.convertNilai(101);
        float expected = 111;
        Assert.assertEquals("convertion failed",expected,actual);
    }

    @Test
    public void testConvertInteger(){
        float actual = Utils.convertNilaiInt(10);
        float expected = 110;
        Assert.assertEquals("convertion integer failed",expected,actual);
    }
}
