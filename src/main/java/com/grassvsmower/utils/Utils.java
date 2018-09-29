/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


/**
 *
 * @author Adriana
 */
@Component("utils")
public class Utils {
	
	 private static final Charset UTF_8 = StandardCharsets.UTF_8;
    
    @Autowired
    private HttpServletRequest request;
    
     public Map getHttpHeaders(){
       Map<String, String> map = new HashMap <String, String>();
       Enumeration headerNames = request.getHeaderNames();
         while (headerNames.hasMoreElements()) {
             String key = (String)headerNames.nextElement();
             String value = request.getHeader(key);
             map.put(key, value);
         }
         return map;
     }
     
     public String generateToken() throws UnsupportedEncodingException{
         
        Instant instant = Instant.now();
        String info = instant.getEpochSecond() + "";
        return UUID.randomUUID().toString() + "+" + Base64.getEncoder().encodeToString(info.getBytes("utf-8"));
                
     }
     
     public String md5 (String cad) throws NoSuchAlgorithmException{
     
       MessageDigest md = MessageDigest.getInstance("MD5");
       byte[] messageDigest = md.digest(cad.getBytes());
       BigInteger number = new BigInteger(1, messageDigest);
       String hashtext = number.toString(16);
       // Now we need to zero pad it if you actually want the full 32 chars.
       while (hashtext.length() < 32) {
           hashtext = "0" + hashtext;

       }
       return hashtext;
     }
    
     public byte [] base64ToByte(String base64String){
         return org.apache.commons.codec.binary.Base64.encodeBase64(base64String.getBytes(UTF_8));
      }
      
      public String ByteTobase64(byte [] data){
    	  byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(data);
         return new String(decodedBytes, UTF_8);
      }
     
     public byte [] blobToByte (Blob blob) {
    	 int blobLength;
    	 byte[] blobAsBytes = null;
		try {
			blobLength = (int) blob.length();
			blobAsBytes = blob.getBytes(1, blobLength);
	    	 //release the blob and free up memory. (since JDBC 4.0)
	    	 blob.free();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			 return blobAsBytes;
		}  
    	
    	 return blobAsBytes;
     } 
     
    public boolean badWordFilter(String word) {
    	boolean retval = false;
    	List<String> list = new ArrayList<>();
    	
    	list.add("pinga");
    	list.add("cojone");
    	list.add("fuck");
    	list.add("joder");
    	list.add("fana");
    	list.add("mierda");
    	list.add("come mierda");
    	list.add("palestino");
    	list.add("oriental");
    	list.add("bajanda");
    	list.add("singao");
    	list.add("la madre");
    	list.add("motherfucker");
    	list.add("coño");
    	list.add("son of a bitch");
    	list.add("hijo de puta");
    	list.add("berk");
    	list.add("coward");
    	list.add("pussy");
    	list.add("cunt");
    	list.add("fanny");
    	list.add("twat");
    	list.add("canaille");
    	list.add("twat");
    	list.add("shit");
    	list.add("cagada");
    	list.add("crap");
    	list.add("poop");
    	list.add("bullshit");
    	list.add("muck");
    	list.add("estupideces");
    	list.add("estupido");
    	list.add("estupidez");
    	list.add("Fuck you");
    	list.add("Piss off");
    	list.add("muck");
    	list.add("Dick head");
    	list.add("Asshole");
    	list.add("Damn");
    	list.add("Cunt");
    	list.add("bastard");
    	list.add("darn");
    	list.add("slut");
    	list.add("douche");
    	list.add("stupid");
    	list.add("idiot");
    	list.add("homosexual");
    	list.add("bollo");
    	
    	for (String item : list) {
    		
    		word = word.toLowerCase();
    		
			if (word.contains(item) ) {
				retval = true;
				break;
			}else {
				word = word.toUpperCase();

				if (word.contains(item) ) {
					retval = true;
					break;
				}
			}
		}
    	
    	return retval;
    } 
    
}
