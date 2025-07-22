package com.alex.cineapp.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utileria {

    public static List<String> getNextDays(int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Date start = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, count);
        Date endDate = cal.getTime();

        GregorianCalendar gcal = new GregorianCalendar();
        gcal.setTime(start);
        List<String> nextDays = new ArrayList<>();
        while (!gcal.getTime().after(endDate)) {
            Date d = gcal.getTime();
            gcal.add(Calendar.DATE, 1);
            nextDays.add(sdf.format(d));
        }
        return nextDays;
    }

    public static String guardarArchivo(MultipartFile multipart, String ruta) {

        String nombreOriginal = multipart.getOriginalFilename();
        nombreOriginal = nombreOriginal.replace(" ", "-");
        String nombreFinal = randomAlphaNumeric(8)+nombreOriginal;
        try {
            File imageFile = new File(ruta + nombreFinal);
            System.out.println("Archivo : " + imageFile.getAbsolutePath());
            multipart.transferTo(imageFile);
            return nombreFinal;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public static String randomAlphaNumeric(int count){
        String CARACTERES = "ABCDEFGHIJKLMNOPQESTUVWXYZ123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0){
            int character = (int) (Math.random() * CARACTERES.length());
            builder.append(CARACTERES.charAt(character));
        }
        return builder.toString();
    }


}
