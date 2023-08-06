package com.kw.gdx.resource.csvanddata.demo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.resource.annotation.ExecuteMathod;
import com.kw.gdx.resource.csvanddata.CsvResource;
import com.kw.gdx.resource.csvanddata.ReadCvs;

import java.io.BufferedReader;

public class CsvUtils {
    private static ReadCvs readCvs = new ReadCvs();
    public static void main(String[] args) {
        new CsvResource(CsvUtils.class);
    }
    @ExecuteMathod
    public void readChapter(){
        Array riderChapters = common("csv/rider_chap.csv", RiderChapter.class);
    }


    public static <T> Array<T> common(String path, Class<T> tClass,boolean isLocal) {
        Array<T> array = new Array<>();
        if (isLocal) {
            readCvs.readMethodMethod(array, new BufferedReader(Gdx.files.internal(path).reader()), tClass);
        }else {
            readCvs.readMethodMethod(array, new BufferedReader(Gdx.files.local(path).reader()), tClass);
        }
        return array;
    }


    public static <T> Array<T> common(String path, Class<T> tClass) {
        Array<T> array = new Array<>();
        readCvs.readMethodMethod(array, new BufferedReader(Gdx.files.internal(path).reader()) , tClass);
        return array;
    }

}
