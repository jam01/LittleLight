package com.jam01.littlelight.adapter.android.presentation.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jam01 on 9/29/16.
 */
public class AccountsAdapter extends ArrayAdapter<Account> {
    private final LayoutInflater inflater;
    private final List<Account> accounts;
    private final Context mContext;
    private OnItemRemoveClickListener itemRemoveClickListener;

    public AccountsAdapter(Context context, int resource, List<Account> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        accounts = objects;
        mContext = context;
    }

    public void setOnItemRemoveClickListener(OnItemRemoveClickListener itemRemoveClickListener) {
        this.itemRemoveClickListener = itemRemoveClickListener;
    }

    public void updateAccount(Account account) {
        for (Account instance : accounts) {
            if (instance.withId().equals(account.withId())) {
                accounts.remove(instance);
                accounts.add(instance);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Account toDraw = accounts.get(position);
        final View toReturn = inflater.inflate(R.layout.view_account_row, parent, false);
        ((TextView) toReturn.findViewById(R.id.tvAccountName)).setText(toDraw.withName());
        Picasso.with(mContext)
                .load(toDraw.profilePath())
                .transform(new CircleTransform())
                .fit()
                .into((ImageView) toReturn.findViewById(R.id.ivAccountIcon));
        toReturn.findViewById(R.id.ivRemove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemRemoveClickListener != null) {
                    itemRemoveClickListener.onItemRemoveClick(toReturn, position);
                }
            }
        });
        return toReturn;
    }

    @Override
    public void remove(Account object) {
        for (Account instance : accounts) {
            if (instance.withId().equals(object.withId())) {
                accounts.remove(instance);
                break;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void add(Account object) {
        accounts.add(object);
        notifyDataSetChanged();
    }

    public interface OnItemRemoveClickListener {
        void onItemRemoveClick(View view, int position);
    }
}
