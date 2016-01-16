package com.arifcebe.artistdemenan.config;

/**
 * Created by arifcebe on 16/01/16.
 */
public class Webservice {
    private static final String URL= "http://api.arifcebe.com/sample_json/";
    private static String demenan = "sample_json_object.php";
    private static String gebetan = "sample_json_array.php";
    private static String artist = "artis_favorite.php";

    public static String getDemenan() {
        return URL + demenan;
    }

    public static String getGebetan() {
        return URL + gebetan;
    }

    public static String getArtist() {
        return URL + artist;
    }
}
