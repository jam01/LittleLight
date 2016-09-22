//package com.jam01.littlelight.adapter.android.rxlegacy.ui.view;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Toast;
//
//import com.jam01.littlelight.R;
//import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.InventoryItem;
//import com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter.CharacterInventoryPresenter;
//import com.jam01.littlelight.adapter.android.rxlegacy.ui.view.adapter.InventoryItemAdapter;
//import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapterWrapper;
//import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by jam01 on 5/8/16.
// */
//public class CharacterInventoryFragment extends Fragment implements CharacterInventoryPresenter.CharacterInventoryView {
//
//    private String TAG = getClass().getSimpleName();
//    private CharacterInventoryPresenter presenter;
//    private String charId;
//    private View rootView;
//    private SwipeRefreshLayout swipeContainer;
//    private InventoryItemAdapter itemAdapter;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        charId = getArguments().getString("charId");
//        itemAdapter = new InventoryItemAdapter(new ArrayList<InventoryItem>(), this.getActivity().getApplicationContext());
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fragment_in_character, container, false);
//
//
//        StickyGridHeadersGridView gridView = (StickyGridHeadersGridView) rootView.findViewById(R.id.gvCharac);
//        gridView.setAdapter(itemAdapter);
//
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(),
//                        ((InventoryItem) ((StickyGridHeadersBaseAdapterWrapper) parent.getAdapter()).getWrappedAdapter().getItem(position)).getItemName(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                presenter.onRefresh();
//            }
//        });
//
//        return rootView;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        if (presenter == null)
//            presenter = new CharacterInventoryPresenter();
//        presenter.bindView(this, charId);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        presenter.unbindView();
//    }
//
//    @Override
//    public void showLoading(boolean show) {
//        if (show) {
//            swipeContainer.post(new Runnable() {
//                @Override
//                public void run() {
//                    swipeContainer.setRefreshing(true);
//                }
//            });
//        } else
//            swipeContainer.setRefreshing(show);
//    }
//
//    @Override
//    public void renderItems(List<InventoryItem> items) {
//        if (items != null) {
//            itemAdapter.addItems(items);
//            itemAdapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public void clearItems() {
//        itemAdapter.clear();
//    }
//}
