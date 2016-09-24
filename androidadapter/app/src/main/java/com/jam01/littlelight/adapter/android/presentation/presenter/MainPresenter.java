package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.jam01.littlelight.application.UserService;

/**
 * Created by jam01 on 9/23/16.
 */
public class MainPresenter {
    private MainView view;
    private UserService service;

    public MainPresenter(UserService service) {
        this.service = service;
    }

    public void bindView(MainView mainView) {
        view = mainView;
        if (service.userAccounts().isEmpty())
            view.loadSignInView();
    }

    public interface MainView {
        void loadSignInView();
    }
}
