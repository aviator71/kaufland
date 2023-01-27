import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.*;
import javax.xml.bind.DatatypeConverter;
//import javax.xml.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

public class Hmac {

    public static void main(String[] args) {
        String method = "POST";
        String uri = "https://sellerapi.kaufland.com/v2/units/";

        String body = "4009750243800";
        String secretKey = "3468e09dfbd0c4e10476f05bd1205b76503867384560c4c59c36bef99b8cfb11";
        long timestamp =  System.currentTimeMillis() / 1000L;
        System.out.println(timestamp);
        try {
            String signature = signRequest(method, uri, body, timestamp, secretKey);
            System.out.println(signature); // da0b65f51c0716c1d3fa658b7eaf710583630a762a98c9af8e9b392bd9df2e2a
        } catch (Exception e) {
            System.out.println("Error signing request");
        }
    }

    public static String signRequest(String method, String uri, String body, long timestamp, String secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException {

        String plainText = method + "\n" + uri + "\n" + body + "\n" + timestamp;

        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        mac.init(secretKeySpec);

        byte[] rawHmac = mac.doFinal(plainText.getBytes());
        return DatatypeConverter.printHexBinary(rawHmac);
    }
}