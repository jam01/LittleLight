package com.jam01.littlelight.adapter.android.rxlegacy.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.model.CharacterLegend;
import com.jam01.littlelight.adapter.android.rxlegacy.ui.presenter.CharacterLegendPresenter;

/**
 * Created by jam01 on 5/2/16.
 */
public class CharacterLegendFragment extends Fragment implements CharacterLegendPresenter.CharacterLegendView {
    private String TAG = getClass().getSimpleName();
    private CharacterLegendPresenter presenter;
    private String charId;
    private View rootView;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        charId = getArguments().getString("charId");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_in_legend, container, false);

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
            presenter = new CharacterLegendPresenter();
        presenter.bindView(this, charId);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbindView();
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
    public void renderCharacterLegend(CharacterLegend characterLegend) {
        if (rootView != null) {
            TextView light = (TextView) rootView.findViewById(R.id.inLegend_tvLight);
            TextView defense = (TextView) rootView.findViewById(R.id.inLegend_tvDef);
            TextView intellect = (TextView) rootView.findViewById(R.id.inLegend_tvIntellect);
            TextView discipline = (TextView) rootView.findViewById(R.id.inLegend_tvDiscipline);
            TextView strength = (TextView) rootView.findViewById(R.id.inLegend_tvStrength);
            ProgressBar armor = (ProgressBar) rootView.findViewById(R.id.inLegend_pbArmor);
            ProgressBar recovery = (ProgressBar) rootView.findViewById(R.id.inLegend_pbRecovery);
            ProgressBar agility = (ProgressBar) rootView.findViewById(R.id.inLegend_pbAgility);

            light.setText(String.valueOf(characterLegend.getLight()));
            defense.setText(String.valueOf(characterLegend.getCharacterLevel()));
            intellect.setText(String.valueOf(characterLegend.getIntellect()));
            discipline.setText(String.valueOf(characterLegend.getDiscipline()));
            strength.setText(String.valueOf(characterLegend.getStrength()));
            armor.setProgress(characterLegend.getArmor());
            recovery.setProgress(characterLegend.getRecovery());
            agility.setProgress(characterLegend.getAgility());

            switch (characterLegend.getClassType()) {
                case 0:
                    rootView.setBackgroundDrawable(getResources().getDrawable(R.drawable.titan));
                    break;
                case 1:
                    rootView.setBackgroundDrawable(getResources().getDrawable(R.drawable.hunter));
                    break;
                case 2:
                    rootView.setBackgroundDrawable(getResources().getDrawable(R.drawable.warlock));
                    break;
            }
        }
    }
}