package com.zg.westlake.homepage.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Picutil {
	private static final Logger logger = LoggerFactory.getLogger(Picutil.class);

	public static Bitmap returnBitMap(String url) {

		final HttpClient client = new DefaultHttpClient();
		final HttpGet getRequest = new HttpGet(url);
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			HttpResponse response = client.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.debug("PicShow", "Request URL failed, error code ="
						+ statusCode);

			}

			HttpEntity entity = response.getEntity();
			if (entity == null) {
				logger.debug("PicShow", "HttpEntity is null");
			}

			baos = new ByteArrayOutputStream();
			is = entity.getContent();
			byte[] buf = new byte[1024];
			int readBytes = -1;
			while ((readBytes = is.read(buf)) != -1) {
				baos.write(buf, 0, readBytes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		byte[] imageArray = baos.toByteArray();
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = 2;
		return BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length,options);

	}
}
