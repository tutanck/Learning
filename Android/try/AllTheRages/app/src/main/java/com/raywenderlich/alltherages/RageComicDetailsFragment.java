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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RageComicDetailsFragment extends Fragment {
  private static final String ARGUMENT_IMAGE_RES_ID = "imageResId";
  private static final String ARGUMENT_NAME = "name";
  private static final String ARGUMENT_DESCRIPTION = "description";
  private static final String ARGUMENT_URL = "url";

  public static RageComicDetailsFragment newInstance(int imageResId, String name,
                                                     String description, String url) {

    final Bundle args = new Bundle();
    args.putInt(ARGUMENT_IMAGE_RES_ID, imageResId);
    args.putString(ARGUMENT_NAME, name);
    args.putString(ARGUMENT_DESCRIPTION, description);
    args.putString(ARGUMENT_URL, url);
    final RageComicDetailsFragment fragment = new RageComicDetailsFragment();
    fragment.setArguments(args);
    return fragment;
  }
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_rage_comic_details, container, false);
    final ImageView imageView = (ImageView) view.findViewById(R.id.comic_image);
    final TextView nameTextView = (TextView) view.findViewById(R.id.name);
    final TextView descriptionTextView = (TextView) view.findViewById(R.id.description);

    final Bundle args = getArguments();
    imageView.setImageResource(args.getInt(ARGUMENT_IMAGE_RES_ID));
    nameTextView.setText(args.getString(ARGUMENT_NAME));
    final String text = String.format(getString(R.string.description_format), args.getString
        (ARGUMENT_DESCRIPTION), args.getString(ARGUMENT_URL));
    descriptionTextView.setText(text);
    return view;
  }
}
