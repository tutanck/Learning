/*
 * Copyright (c) 2015 Razeware LLC
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

package com.raywenderlich.alltherages;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.app.Activity;

public class RageComicListFragment extends Fragment {

  private int[] mImageResIds;
  private String[] mNames;
  private String[] mDescriptions;
  private String[] mUrls;
  private OnRageComicSelected mListener;

  public static RageComicListFragment newInstance() {
    return new RageComicListFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);

    if (context instanceof OnRageComicSelected) {
      mListener = (OnRageComicSelected) context;
    } else {
      throw new ClassCastException(context.toString() + " must implement OnRageComicSelected.");
    }

    // Get rage face names and descriptions.
    final Resources resources = context.getResources();
    mNames = resources.getStringArray(R.array.names);
    mDescriptions = resources.getStringArray(R.array.descriptions);
    mUrls = resources.getStringArray(R.array.urls);

    // Get rage face images.
    final TypedArray typedArray = resources.obtainTypedArray(R.array.images);
    final int imageCount = mNames.length;
    mImageResIds = new int[imageCount];
    for (int i = 0; i < imageCount; i++) {
      mImageResIds[i] = typedArray.getResourceId(i, 0);
    }
    typedArray.recycle();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_rage_comic_list, container, false);

    final Activity activity = getActivity();
    final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
    recyclerView.setAdapter(new RageComicAdapter(activity));
    return view;
  }

  class RageComicAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mLayoutInflater;

    public RageComicAdapter(Context context) {
      mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      return new ViewHolder(mLayoutInflater
          .inflate(R.layout.recycler_item_rage_comic, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
      final int imageResId = mImageResIds[position];
      final String name = mNames[position];
      final String description = mDescriptions[position];
      final String url = mUrls[position];
      viewHolder.setData(imageResId, name);

      viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mListener.onRageComicSelected(imageResId, name, description, url);
        }
      });
    }

    @Override
    public int getItemCount() {
      return mNames.length;
    }
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    // Views
    private ImageView mImageView;
    private TextView mNameTextView;

    private ViewHolder(View itemView) {
      super(itemView);

      // Get references to image and name.
      mImageView = (ImageView) itemView.findViewById(R.id.comic_image);
      mNameTextView = (TextView) itemView.findViewById(R.id.name);
    }

    private void setData(int imageResId, String name) {
      mImageView.setImageResource(imageResId);
      mNameTextView.setText(name);
    }
  }

  public interface OnRageComicSelected {
    void onRageComicSelected(int imageResId, String name,
                             String description, String url);
  }
}
