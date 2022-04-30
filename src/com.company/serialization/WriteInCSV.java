package com.company.serialization;

import com.company.stores.Address;
import com.company.stores.orders.Card;
import com.company.stores.products.Review;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WriteInCSV {

    private static <T> String convertToCsvFormat(Class<T> classOf, T newOBJ) throws IllegalAccessException {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder stringOBJ = new StringBuilder();
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
        stringOBJ.append("\n");
        for(Field field:fields){
            Class<?> fieldType = field.getType();
            field.setAccessible(true);
            if(field.getType()==int.class){
                stringOBJ.append(field.get(newOBJ).toString()).append(",");
            }else if(field.getType()==boolean.class){
                stringOBJ.append(field.get(newOBJ).toString()).append(",");
            }else if(field.getType()==double.class){
                stringOBJ.append(field.get(newOBJ).toString()).append(",");
            }else if(field.getType()== Date.class) {
                stringOBJ.append(formatDate.format(field.get(newOBJ))).append(",");
            }else if(field.getType()== String.class){
                stringOBJ.append(field.get(newOBJ)).append(",");
            }else if(field.getType()== Address.class){
                Address address = (Address) field.get(newOBJ);
                String addressString = "";
                String number = String.valueOf(address.getNumber());
                addressString += "address={|"+address.getCity() + "|" + address.getStreet() +  "|" + number + "|"+
                                  address.getCity() + "|}";
                stringOBJ.append(addressString).append(",");
            }else if(field.getType()== Card.class){
                Card card = (Card) field.get(newOBJ);
                String cardString = "";
                String validThru = formatDate.format(card.getValidThru());
                String authorisedSignature = String.valueOf(card.getAuthorisedSignature());
                cardString+= "card={|"+card.getNumber() + "|" + validThru + "|" + card.getType() + "|" + authorisedSignature+
                             "|}";
                stringOBJ.append(cardString).append(",");
            }else if(field.getType()== List.class){
                if((field.getName()).equals("program")) {
                    stringOBJ.append("program=null").append(",");
                }else if((field.getName()).equals("products")){
                    stringOBJ.append("products=null").append(",");
                }else if((field.getName()).equals("review")){
                    List<Review> reviewList = (List<Review>) field.get(newOBJ);
                    String reviewListString = "";
                    String reviewString = "";
                    reviewListString+= "listReview=[";
                    if (reviewList.size()==0){
                        reviewListString+="?review={|null|}?";
                    }else{
                        for(Review review:reviewList){
                            String numberStars = String.valueOf(review.getNumberStars());
                            reviewString = "?review={|" + "null|" + review.getMesaje() + "|" + numberStars +"|}?";
                        }
                    }
                    reviewListString+=reviewString + "]";
                    stringOBJ.append(reviewListString).append(",");
                }
            }
        }
        return stringOBJ.toString();
    }

    private static String removeLastChar(String s)
    {
        return s.substring(0, s.length() - 1);
    }

    public static <T> void writeInCSV(Class<T> classOf, String fileName, T newOBJ) throws IOException, IllegalAccessException {
        File csvOutputFile = new File(fileName);
        String stringOBJ = removeLastChar( convertToCsvFormat(classOf,newOBJ));
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(csvOutputFile, true))) {
            pw.append(stringOBJ);
         }
    }
}
