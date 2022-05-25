package com.company.serialization;

import com.company.stores.Address;
import com.company.stores.orders.Card;
import com.company.stores.products.Product;
import com.company.stores.products.Review;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadFromCSV {
    private static final ReadFromCSV instance = new ReadFromCSV();
    private static final String commaDelimiter = ",";

    @SuppressWarnings( "rawtypes")
    private List<Class> list = new ArrayList<>();

    private ReadFromCSV(){

    }

    private static boolean tryInt(String value){
        try{
           int number = Integer.parseInt(value);
           return true;
        }catch(IllegalArgumentException iiae){
            return false;
        }
    }

    private static boolean tryDouble(String value){
        try{
            double number = Double.parseDouble(value);
            return true;
        }catch(IllegalArgumentException iiae){
            return false;
        }
    }

    private static boolean tryBool(String value){
       if(Objects.equals(value, "true") || Objects.equals(value, "false"))
           return true;
       return false;
    }

    private static boolean tryUUID(String value){
        try{
            UUID uuid = UUID.fromString(value);
            return true;
        }catch(IllegalArgumentException iiae){
            return false;
        }
    }

    private static boolean tryDate(String value){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        format.setLenient(false);
        try{
            Date date =   format.parse(value);
            return true;
        }catch(IllegalArgumentException | ParseException iiae){
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> List<T> readFromCsv(Class<T> classOf, String fileName) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        try {
            List<T> list = new ArrayList<>();
            BufferedReader readerFile = new BufferedReader(new FileReader(fileName));

            readerFile.readLine();
            String[] columns = readerFile.readLine().split(",");
            String valuesLineString = readerFile.readLine();
            while( valuesLineString != null){

                String[] valuesLine = valuesLineString.split(",");
//                System.out.println(Arrays.toString(valuesLine));
                T object = classOf.getConstructor().newInstance();
                Field[] fields;
                Class<? super T> parent = classOf.getSuperclass();
                if (parent==null){
                    fields = classOf.getDeclaredFields();
                }else{
                    Field[] parentFields =  parent.getDeclaredFields();
                    Field[] childFields = classOf.getDeclaredFields();
                    fields = new Field[parentFields.length + childFields.length];
                    System.arraycopy(parentFields, 0, fields, 0, parentFields.length);
                    System.arraycopy(childFields, 0, fields, parentFields.length, childFields.length);
                }
                for (int i=0;i<valuesLine.length;i++){
                    var field = fields[i+1];
                    field.setAccessible(true);
                    var value = valuesLine[i];

                    if(tryInt(value) && value.length()<16){
                        field.set(object,Integer.parseInt(value));
                    }else if(tryDouble(value) && value.length()<16 ){
                        field.set(object,Double.parseDouble(value));
                    }else if(tryBool(value)){
                        field.set(object,Boolean.parseBoolean(value));
                    }else if(tryUUID(value)){
                        field.set(object,UUID.fromString(value));
                    }else if(tryDate(value)){
                        field.set(object,format.parse(value));
                    }else if(value.startsWith("address={")){
                        String[] addressValues = value.split("\\|");
                        Address address = new Address(addressValues[1],addressValues[2],Integer.parseInt(addressValues[3]),addressValues[4]);
                        field.set(object,address);
                    }else if(value.startsWith("card={")){
                        String[] cardValues = value.split("\\|");
                        Card card = new Card(cardValues[1],format.parse(cardValues[2]),cardValues[3],Integer.parseInt(cardValues[4]));
                        field.set(object,card);
                    }else if(value.startsWith("listReview=[")){
                        List<Review> reviews = new ArrayList<>();
                        String[] reviewsString = value.split("\\?");
                        for(int j=1;j< reviewsString.length-1;j++){
                            if(reviewsString[j].startsWith("review={")){
                                String[] reviewColumns = reviewsString[j].split("\\|");
                                if(!Objects.equals(reviewColumns[1], "null")){
                                    Review review = new Review(null,reviewColumns[2],Integer.parseInt(reviewColumns[3]));
                                    reviews.add(review);
                                    field.set(object,reviews);
                                }
                            }
                        }
                    }else if(value.startsWith("program=null")){
                        List<Map<String,String>> program = new ArrayList<>();
                        field.set(object,program);
                    }else if(value.startsWith("products=null")){
                        List<Product>products = new ArrayList<>();
                        field.set(object,products);
                    }
                    else{
                        field.set(object,value);
                    }
                }
                list.add(object);
                valuesLineString = readerFile.readLine();
            }
            readerFile.close();
            return (List<T>) list;
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("Invalid file!");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> getInstance(Class<T> classOf, String fileName) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        synchronized(instance){

            List<T> objects = new ArrayList<>();
            if(!instance.list.contains(classOf)){
                try {
                    objects = (List<T>) readFromCsv(classOf, fileName);

                    instance.list.add(classOf);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return (List<T>) objects;
        }
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
