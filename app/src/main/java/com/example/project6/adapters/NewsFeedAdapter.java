package com.example.project6.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project6.R;
import com.example.project6.objects.New;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * The type News feed adapter.
 */
public class NewsFeedAdapter extends ArrayAdapter<New> {

    private Context mContext;

    private static final String INPUT_FORMAT_TAG = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String OUTPUT_FORMAT_TAG = "LLL dd, yyyy h:mm a";


    /**
     * Instantiates a new News feed adapter.
     *
     * @param context the context
     * @param values  the values
     */
    public NewsFeedAdapter(Context context, ArrayList<New> values) {
        super(context, 0, values);
        this.mContext = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final New newItem = getItem(position);

        View viewItem = convertView;
        if (viewItem == null) {
            viewItem = LayoutInflater.from(getContext()).inflate(R.layout.news_item_layout, parent, false);
        }

        // Get TextViews objects;
        TextView newTitleTv = viewItem.findViewById(R.id.new_title_tv_id);
        TextView newDateTv = viewItem.findViewById(R.id.new_date_tv_id);
        TextView newSectionTv = viewItem.findViewById(R.id.new_section_tv_id);
        TextView newAuthorTv = viewItem.findViewById(R.id.new_author_tv_id);

        // Set New item values, if item not null and values not null or empty
        if (newItem != null) {

            newTitleTv.setText(newItem.getNewTitle());
            newDateTv.setText(formatDate(newItem.getNewDate()));

            if (!TextUtils.isEmpty(newItem.getNewSection())) {
                newSectionTv.setText(mContext.getString(R.string.section, newItem.getNewSection()));
                newSectionTv.setVisibility(View.VISIBLE);
            } else {
                newSectionTv.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(newItem.getNewAuthor())) {
                newAuthorTv.setText(mContext.getString(R.string.author, newItem.getNewAuthor()));
                newAuthorTv.setVisibility(View.VISIBLE);
            } else {
                newAuthorTv.setVisibility(View.GONE);
            }
        }

        return viewItem;
    }

    // Format date from String with inputFormat, to String with outputFormat
    private String formatDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(INPUT_FORMAT_TAG, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(OUTPUT_FORMAT_TAG, Locale.ENGLISH);
        Date date;
        try {
            date = inputFormat.parse(dateString);
        } catch (ParseException e) {
            return "";
        }
        return outputFormat.format(date);
    }


}
