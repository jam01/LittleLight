package com.jam01.littlelight.adapter.android.presentation.presenter;

import com.jam01.littlelight.application.UserService;
import com.jam01.littlelight.domain.identityaccess.User;

import javax.inject.Inject;

/**
 * Created by jam01 on 9/23/16.
 */
public class UserPresenter {
    private MainView view;
    private UserService service;

    @Inject
    public UserPresenter(UserService service) {
        this.service = service;
    }

    public void bindView(MainView mainView) {
        view = mainView;
        if (service.userAccounts().isEmpty()) {
            view.loadSignInView();
        }
        view.setUser(service.getUser());
    }

    public void unbindView() {
        view = null;
    }

    public interface MainView {
        void loadSignInView();

        void setUser(User user);
    }
}
