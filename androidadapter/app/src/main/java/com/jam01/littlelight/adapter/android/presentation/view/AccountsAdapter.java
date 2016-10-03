package com.jam01.littlelight.adapter.android.presentation.view;

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

    public AccountsAdapter(Context context, int resource, List<Account> objects) {
        super(context, resource, objects);
        inflater = LayoutInflater.from(context);
        accounts = objects;
        mContext = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Account toDraw = accounts.get(position);
        View toReturn = inflater.inflate(R.layout.account_row, parent, false);
        ((TextView) toReturn.findViewById(R.id.tvAccountName)).setText(toDraw.withName());
        Picasso.with(mContext)
                .load(toDraw.profilePath())
                .transform(new CircleTransform())
                .fit()
                .into((ImageView) toReturn.findViewById(R.id.ivAccountIcon));
        return toReturn;
    }
}
