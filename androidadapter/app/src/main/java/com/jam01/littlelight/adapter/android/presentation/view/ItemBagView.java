package com.jam01.littlelight.adapter.android.presentation.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.presenter.ItemBagPresenter;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemBag;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapterWrapper;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import java.util.ArrayList;

/**
 * Created by jam01 on 9/24/16.
 */
public class ItemBagView extends StickyGridHeadersGridView implements ItemBagPresenter.ItemBagView {
    private ItemBagPresenter presenter;
    private ItemAdapter itemAdapter;
    private ProgressDialog progressDialog;

    public ItemBagView(final Context context, final ItemBag itemBag) {
        super(context);
        setChoiceMode(CHOICE_MODE_MULTIPLE_MODAL);
        setColumnWidth((int) (90 * context.getResources().getDisplayMetrics().density));
        setGravity(Gravity.CENTER);
        setHorizontalSpacing(0);
        setNumColumns(AUTO_FIT);
        setStretchMode(STRETCH_SPACING_UNIFORM);
        setVerticalSpacing((int) (5 * context.getResources().getDisplayMetrics().density));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Little Light");
        progressDialog.setMessage("ItemBag Dialog");

        itemAdapter = new ItemAdapter(new ArrayList<>(itemBag.items()), context);

        setAdapter(itemAdapter);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View dialogView = inflate(context, R.layout.dialog_item_details, null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                dialog.show();

                final Item tmp = ((Item) ((StickyGridHeadersBaseAdapterWrapper) parent.getAdapter()).getWrappedAdapter().getItem(position));
                TextView title = (TextView) dialogView.findViewById(R.id.tvDTitle);
                TextView second = (TextView) dialogView.findViewById(R.id.tvDDamage);
                TextView third = (TextView) dialogView.findViewById(R.id.tvDDType);

                title.setText(tmp.getItemName());

                switch (tmp.getItemType()) {
                    case 2:
                        second.setText("Defense: " + tmp.getDamage());
                        second.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        second.setText("Attack: " + tmp.getDamage());
                        second.setVisibility(View.VISIBLE);
                        third.setText("Damage Type: ");
                        third.append(tmp.getDamageType());
                        third.setVisibility(View.VISIBLE);
                        break;
                }

                switch (tmp.getTierType()) {
                    case 5:
                        title.setBackgroundColor(0xff5a1bff);
                        break;
                    case 6:
                        title.setBackgroundColor(0xffffb200);
                        break;
                }

                if ((!tmp.isEquipped()) && (tmp.getItemType() == 2 || tmp.getItemType() == 3)) {
                    Button equip = (Button) dialogView.findViewById(R.id.bIDetail);
                    equip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            presenter.equipItem(tmp, ((Character) itemBag).withId());
                            dialog.dismiss();
                        }
                    });
                    equip.setVisibility(View.VISIBLE);
                }
            }
        });

        if (presenter == null) {
            presenter = ((LittleLight) ((Activity) context).getApplication()).getComponent().provideItemBagPresenter();
        }
        presenter.bindView(this);
    }

    @Override
    public void showLoading(boolean show) {
        if (show)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }

    @Override
    public void replaceItems(ItemBag itemBag) {
        itemAdapter.clear();
        itemAdapter.addItems(new ArrayList<>(itemBag.items()));
        itemAdapter.notifyDataSetChanged();
    }


    @Override
    public void clearItems() {
        itemAdapter.clear();
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateItem(Item anItem) {
        itemAdapter.updateItem(anItem);
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void removeItem(Item anItem) {
        itemAdapter.removeItem(anItem);
        itemAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String localizedMessage) {
        Toast.makeText(getContext(), localizedMessage, Toast.LENGTH_SHORT).show();
    }
}
