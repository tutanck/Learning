/*
 * Copyright (c) 2016 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.galacticon;

import android.app.Activity;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageRequester {

  public interface ImageRequesterResponse {
    void receivedNewPhoto(Photo newPhoto);
  }

  private Calendar mCalendar;
  private SimpleDateFormat mDateFormat;
  private ImageRequesterResponse mResponseListener;
  private Context mContext;
  private OkHttpClient mClient;
  private static final String BASE_URL = "https://api.nasa.gov/planetary/apod?";
  private static final String DATE_PARAMETER = "date=";
  private static final String API_KEY_PARAMETER = "&api_key=";
  private static final String MEDIA_TYPE_KEY = "media_type";
  private static final String MEDIA_TYPE_VIDEO_VALUE = "video";
  private boolean mLoadingData;

  public boolean isLoadingData() {
    return mLoadingData;
  }

  public ImageRequester(Activity listeningActivity) {
    mCalendar = Calendar.getInstance();
    mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    mResponseListener = (ImageRequesterResponse) listeningActivity;
    mContext = listeningActivity.getApplicationContext();
    mClient = new OkHttpClient();
    mLoadingData = false;
  }

  public void getPhoto() throws IOException {

    String date = mDateFormat.format(mCalendar.getTime());

    String urlRequest = BASE_URL + DATE_PARAMETER + date + API_KEY_PARAMETER +
        mContext.getString(R.string.api_key);
    Request request = new Request.Builder().url(urlRequest).build();
    mLoadingData = true;

    mClient.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        mLoadingData = false;
        e.printStackTrace();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {

        try {
          JSONObject photoJSON = new JSONObject(response.body().string());

          mCalendar.add(Calendar.DAY_OF_YEAR, -1);

          if (!photoJSON.getString(MEDIA_TYPE_KEY).equals(MEDIA_TYPE_VIDEO_VALUE)) {
            Photo receivedPhoto = new Photo(photoJSON);
            mResponseListener.receivedNewPhoto(receivedPhoto);
            mLoadingData = false;
          } else {
            getPhoto();
          }
        } catch (JSONException e) {
          mLoadingData = false;
          e.printStackTrace();
        }
      }
    });
  }
}
