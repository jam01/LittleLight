package com.jam01.littlelight.adapter.android.rxlegacy.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.InventoryItem;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter.VaultInventoryPresenter;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.view.adapter.InventoryItemAdapter;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapterWrapper;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jam01 on 5/21/16.
 */
public class VaultInventoryFragment extends Fragment implements VaultInventoryPresenter.VaultInventoryView {
    private String TAG = getClass().getSimpleName();
    private VaultInventoryPresenter presenter;
    private View rootView;
    private SwipeRefreshLayout swipeContainer;
    private InventoryItemAdapter itemAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemAdapter = new InventoryItemAdapter(new ArrayList<InventoryItem>(), this.getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_in_vault, container, false);


        StickyGridHeadersGridView gridView = (StickyGridHeadersGridView) rootView.findViewById(R.id.gvVault);
        gridView.setAdapter(itemAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),
                        ((InventoryItem) ((StickyGridHeadersBaseAdapterWrapper) parent.getAdapter()).getWrappedAdapter().getItem(position)).getItemName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (presenter == null)
            presenter = new VaultInventoryPresenter();
        presenter.bindView(this);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView();
        super.onDestroyView();
    }

    @Override
    public void showLoading(boolean show) {
        if (show) {
            swipeContainer.post(new Runnable() {
                @Override
                public void run() {
                    swipeContainer.setRefreshing(true);
                }
            });
        } else
            swipeContainer.setRefreshing(show);
    }

    @Override
    public void renderItems(List<InventoryItem> items) {
        if (items != null) {
            // Items from vault come in no particular order, unlike items from characters,
            // and the stickyHeaderGridView does not deal well with items in random order
            // TODO: 5/22/16 Make the order the same as character inventory
            Collections.sort(items, new Comparator<InventoryItem>() {
                @Override
                public int compare(InventoryItem inventoryItem, InventoryItem inventoryItem2) {
                    return Long.valueOf(inventoryItem.getBucketTypeHash()).compareTo(inventoryItem2.getBucketTypeHash());
                }
            });
            itemAdapter.addItems(items);
            itemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void clearItems() {
        itemAdapter.clear();
    }
}
