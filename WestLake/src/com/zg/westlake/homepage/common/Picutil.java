package com.zg.westlake.homepage.common;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Picutil {

	public static Bitmap returnBitMap(String url) {  
	    HttpGet get = new HttpGet(url); 
	    HttpClient client = new DefaultHttpClient(); 
	    Bitmap pic = null; 
	    try { 
	        HttpResponse response = client.execute(get); 
	        HttpEntity entity = response.getEntity(); 
	        InputStream is = entity.getContent(); 
	        if(is!=null){
		        BitmapFactory.Options options=new BitmapFactory.Options();  
		        options.inJustDecodeBounds = false;  
		        options.inSampleSize = 2;     
		        pic=BitmapFactory.decodeStream(is,null,options);  
	        }
	        
	    } catch (ClientProtocolException e) { 
	        e.printStackTrace(); 
	    } catch (IOException e) { 
	        e.printStackTrace(); 
	    } 
	    return pic; 
		}
}
