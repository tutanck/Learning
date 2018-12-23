package com.algolia.instantsearch.examples.ecommerce;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.algolia.instantsearch.helpers.Searcher;
import com.algolia.instantsearch.model.FacetStat;
import com.algolia.instantsearch.model.NumericRefinement;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class FilterResultsWindow extends PopupWindow {
    private final Activity activity;
    private Searcher searcher;

    private List<FilterDescription> filterDescriptions = new ArrayList<>();
    private SparseArray<View> filterViews = new SparseArray<>();

    private FilterResultsWindow(Builder builder) {
        super(FilterResultsWindow.getInflatedLayout(builder.activity, R.layout.popup_filter), WRAP_CONTENT, WRAP_CONTENT);
        this.activity = builder.activity;
        this.searcher = builder.searcher;
        this.filterDescriptions = builder.filterDescriptions;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(10);
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        beforeShow();
        super.showAsDropDown(anchor, xOff, yOff, gravity);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        beforeShow();
        super.showAtLocation(parent, gravity, x, y);
    }

    private void beforeShow() {
        LinearLayout layout = (LinearLayout) getContentView().findViewById(R.id.popup_filters);
        layout.removeAllViews();

        filterViews.clear();
        for (FilterDescription view : filterDescriptions) {
            view.create(this);
        }
        for (int i = 0; i < filterViews.size(); i++) {
            View v = filterViews.get(i);
            layout.addView(v);
        }
    }

    private static View getInflatedLayout(Context context, int layoutId) {
        return ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layoutId, null);
    }

    private View getInflatedLayout(int layoutId) {
        return activity.getLayoutInflater().inflate(layoutId, null);
    }

    private void createCheckBox(final CheckBoxDescription description) {
        final String attribute = description.attribute;
        final String name = description.name;
        final boolean checkedIsTrue = description.checkedIsTrue;

        final View checkBoxLayout = getInflatedLayout(R.layout.layout_checkbox);

        final TextView tv = (TextView) checkBoxLayout.findViewById(R.id.dialog_checkbox_text);
        final CheckBox checkBox = (CheckBox) checkBoxLayout.findViewById(R.id.dialog_checkbox_box);
        final Boolean currentFilter = searcher.getBooleanFilter(attribute);
        final FacetStat stats = searcher.getFacetStat(attribute);
        final boolean hasOnlyOneValue = stats != null && stats.min == stats.max;

        if (currentFilter != null) { // If the attribute is currently filtered, show its state
            checkBox.setChecked(currentFilter);
        } else if (hasOnlyOneValue) { // If the attribute is not filtered and has only one value, disable its checkbox
            checkBox.setChecked(stats.min != 0); // If min=max=1, we check the box before disabling
            checkBox.setEnabled(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    searcher.addBooleanFilter(attribute, checkedIsTrue).search();
                } else {
                    searcher.removeBooleanFilter(attribute).search();
                }
            }
        });
        tv.setText(name != null ? name : attribute);
        filterViews.put(description.position, checkBoxLayout);
    }

    private void createSeekBar(SeekBarDescription description) {
        final String attribute = description.attribute;
        final String name = description.name;
        final double minValue = description.min;
        final double maxValue = description.max;
        final int steps = description.steps;

        final View seekBarLayout = getInflatedLayout(R.layout.layout_seekbar);

        final TextView tv = (TextView) seekBarLayout.findViewById(R.id.dialog_seekbar_text);
        final SeekBar seekBar = (SeekBar) seekBarLayout.findViewById(R.id.dialog_seekbar_bar);
        final NumericRefinement currentFilter = searcher.getNumericRefinement(attribute, NumericRefinement.OPERATOR_GT);

        if (currentFilter != null && currentFilter.value != 0) {
            final int progressValue = (int) ((currentFilter.value - minValue) * steps / (maxValue - minValue));
            seekBar.setProgress(progressValue);
        }
        seekBar.setMax(steps);
        final int[] lastProgressValue = {seekBar.getProgress()};
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                onUpdate(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                onUpdate(seekBar);
            }

            private void onUpdate(final SeekBar seekBar) {
                int newProgressValue = seekBar.getProgress(); // avoid double search on ProgressChanged + StopTrackingTouch
                if (newProgressValue != lastProgressValue[0]) {
                    final double actualValue = updateSeekBarText(tv, seekBar, name, minValue, maxValue, steps);
                    if (newProgressValue == 0) {
                        searcher.removeNumericRefinement(new NumericRefinement(attribute, NumericRefinement.OPERATOR_GT, actualValue))
                                .search();
                    } else {
                        searcher.addNumericRefinement(new NumericRefinement(attribute, NumericRefinement.OPERATOR_GT, actualValue))
                                .search();
                    }
                }
                lastProgressValue[0] = newProgressValue;
            }
        });

        updateSeekBarText(tv, currentFilter != null ? currentFilter : new NumericRefinement(name, NumericRefinement.OPERATOR_GT, minValue), name, minValue);
        filterViews.put(description.position, seekBarLayout);
    }

    private double updateSeekBarText(final TextView textView, final NumericRefinement filter, final String name, final double minValue) {
        updateSeekBarText(textView, name != null ? name : filter.attribute, filter.value, minValue);
        return filter.value;
    }

    private double updateSeekBarText(final TextView textView, final SeekBar seekBar, final String name, final double minValue, final double maxValue, int steps) {
        int progress = seekBar.getProgress();
        final double value = minValue + progress * (maxValue - minValue) / steps;
        updateSeekBarText(textView, name, value, minValue);
        return value;
    }

    private void updateSeekBarText(final TextView textView, final String name, final double value, final double minValue) {
        if (value == minValue) {
            textView.setText(String.format("Filter minimum %s", name));
        } else {
            final String prefix = "At least <b>";
            final String suffix = "</b> %s";
            final String html;
            if (value == (long) value) {
                html = String.format(prefix + "%d" + suffix, (long) value, name);
            } else {
                html = String.format(prefix + "%.1f" + suffix, value, name);
            }
            textView.setText(Html.fromHtml(html));
        }
    }

    private Searcher getSearcher() {
        return searcher;
    }

    public static class Builder {
        private final Activity activity;
        private final Searcher searcher;

        private int filterCount = 0;
        private List<FilterDescription> filterDescriptions = new ArrayList<>();

        public Builder(Activity activity, Searcher searcher) {
            this.activity = activity;
            this.searcher = searcher;
        }

        /**
         * Add a SeekBar to the fragment, automatically fetching min and max values for its attribute.
         * This method <b>MUST</b> be called in the enclosing activity's <code>onCreate</code> to setup the required facets <b>before</b> the fragment is created.
         *
         * @param attribute the attribute this SeekBar will filter on.
         * @param steps     the amount of steps between min and max.
         * @return the fragment to allow chaining calls.
         */
        public Builder addSeekBar(final String attribute, final int steps) {
            return addSeekBar(attribute, attribute, null, null, steps);
        }

        /**
         * Add a SeekBar to the fragment, naming the attribute differently in the UI and automatically fetching min and max values for its attribute.
         * This method <b>MUST</b> be called in the enclosing activity's <code>onCreate</code> to setup the required facets <b>before</b> the fragment is created.
         *
         * @param attribute the attribute this SeekBar will filter on.
         * @param name      the name to use when referring to the refined attribute.
         * @param steps     the amount of steps between min and max.
         * @return the fragment to allow chaining calls.
         */
        public Builder addSeekBar(final String attribute, final String name, final int steps) {
            return addSeekBar(attribute, name, null, null, steps);
        }

        /**
         * Add a SeekBar to the fragment.
         *
         * @param attribute the attribute this SeekBar will filter on.
         * @param min       the minimum value that the user can select.
         * @param max       the maximum value that the user can select.
         * @param steps     the amount of steps between min and max.
         * @return the fragment to allow chaining calls.
         */
        public Builder addSeekBar(final String attribute, final Double min, final Double max, final int steps) {
            return addSeekBar(attribute, attribute, min, max, steps);
        }

        /**
         * Add a SeekBar to the fragment, naming the attribute differently in the UI.
         *
         * @param attribute the attribute this SeekBar will filter on.
         * @param name      the name to use when referring to the refined attribute.
         * @param min       the minimum value that the user can select.
         * @param max       the maximum value that the user can select.
         * @param steps     the amount of steps between min and max.
         * @return the fragment to allow chaining calls.
         */
        public Builder addSeekBar(final String attribute, final String name, final Double min, final Double max, final int steps) {
            filterDescriptions.add(new SeekBarDescription(attribute, name, min, max, steps, filterCount++));
            return this;
        }

        /**
         * Add a CheckBox to the fragment.
         *
         * @param attribute     the attribute this SeekBar will filter on.
         * @param checkedIsTrue if {@code true}, a checked box will refine on attribute:true, else on attribute:false.
         * @return the fragment to allow chaining calls.
         */
        public Builder addCheckBox(final String attribute, boolean checkedIsTrue) {
            return addCheckBox(attribute, attribute, checkedIsTrue);
        }

        /**
         * Add a CheckBox to the fragment, naming the attribute differently in the UI.
         *
         * @param attribute     the attribute this SeekBar will filter on.
         * @param name          the name to use when referring to the refined attribute.
         * @param checkedIsTrue if {@code true}, a checked box will refine on attribute:true, else on attribute:false.
         * @return the fragment to allow chaining calls.
         */
        public Builder addCheckBox(final String attribute, final String name, final boolean checkedIsTrue) {
            filterDescriptions.add(new CheckBoxDescription(attribute, name, checkedIsTrue, filterCount++));
            return this;
        }

        public FilterResultsWindow build() {
            String[] attributes = new String[filterDescriptions.size()];
            for (int i = 0; i < filterDescriptions.size(); i++) {
                attributes[i] = filterDescriptions.get(i).attribute;
            }
            searcher.addFacet(attributes);
            searcher.getUpdatedFacetStats();
            return new FilterResultsWindow(this);
        }
    }

    abstract static class FilterDescription {
        protected final String attribute;
        protected final String name;
        protected final int position;

        FilterDescription(String attribute, String name, int position) {
            this.attribute = attribute;
            this.name = name;
            this.position = position;
        }

        protected abstract void create(FilterResultsWindow window);
    }

    private static class SeekBarDescription extends FilterDescription {
        Double min;
        Double max;
        final int steps;

        SeekBarDescription(String attribute, String name, Double min, Double max, int steps, int position) {
            super(attribute, name, position);
            this.min = min;
            this.max = max;
            this.steps = steps;
        }

        protected void create(FilterResultsWindow window) {
            if (min == null || max == null) {
                FacetStat stats = window.getSearcher().getFacetStat(attribute);
                if (stats == null) {
                    throw new RuntimeException("No facet stats were stored for `" + attribute + "`. Did you call addSeekBar(attribute, name, steps) in the activity's onCreate, as required?");
                }
                min = min == null ? stats.min : min;
                max = max == null ? stats.max : max;
            }
            window.createSeekBar(this);
        }

    }

    private static class CheckBoxDescription extends FilterDescription {
        final boolean checkedIsTrue;

        CheckBoxDescription(String attribute, String name, boolean checkedIsTrue, int position) {
            super(attribute, name, position);
            this.checkedIsTrue = checkedIsTrue;
        }

        protected void create(FilterResultsWindow window) {
            window.createCheckBox(this);
        }
    }
}