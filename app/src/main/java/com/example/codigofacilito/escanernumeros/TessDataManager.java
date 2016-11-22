package com.example.codigofacilito.escanernumeros;


import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Marines on 15/09/2016.
 */
public class TessDataManager {

    private static final String tessdir = "tess-two";
    private static final String subdir = "tessdata";
    private static final String filename = "eng.traineddata";

    private static String trainedDataPath;

    private static String tesseractFolder;

    public static String getTesseractFolder() {
        return tesseractFolder;
    }


    private static boolean initiated;

    public static void initTessTrainedData(Context context){//todo

        if(initiated)
            return;

        File appFolder = context.getFilesDir();

        File folder = new File(appFolder, tessdir);
        if(!folder.exists())
            folder.mkdir();
        tesseractFolder = folder.getAbsolutePath();

        File subfolder = new File(folder, subdir);
        if(!subfolder.exists())
            subfolder.mkdir();

        File file = new File(subfolder, filename);
        trainedDataPath = file.getAbsolutePath();
        if(!file.exists()) {

            try {
                FileOutputStream fileOutputStream;
                byte[] bytes = readRawTrainingData(context);
                if (bytes == null)
                    return;
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                initiated = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            initiated = true;
        }
    }

    private static byte[] readRawTrainingData(Context context){//todo

        try {
            InputStream fileInputStream = context.getResources()
                    .openRawResource(R.raw.eng_traineddata);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int bytesRead;

            while (( bytesRead = fileInputStream.read(b))!=-1){
                bos.write(b, 0, bytesRead);
            }

            fileInputStream.close();
            return bos.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}