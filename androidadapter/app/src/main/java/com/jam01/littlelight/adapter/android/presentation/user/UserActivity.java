package com.jam01.littlelight.adapter.android.presentation.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.activity.ActivityFragment;
import com.jam01.littlelight.adapter.android.presentation.exotics.ExoticsFragment;
import com.jam01.littlelight.adapter.android.presentation.inventory.InventoryFragment;
import com.jam01.littlelight.adapter.android.presentation.legend.LegendFragment;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UserPresenter.MainView {
    private static final String MEMBERSHIP_TYPE = "param1";
    private static final String MEMBERSHIP_ID = "param2";
    private AccountId accountSelectedId;
    private UserPresenter presenter;
    private View accountsView;
    private ListView accountListView;
    private View headerView;
    private View spinnerArrow;
    private ImageView selectedAccountAvatar;
    private String TAG = this.getClass().getSimpleName();
    private int lastCheckedNavId = -1;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            accountSelectedId = new AccountId(savedInstanceState.getInt(MEMBERSHIP_TYPE), savedInstanceState.getString(MEMBERSHIP_ID));
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        headerView = mNavigationView.getHeaderView(0);
        accountsView = headerView.findViewById(R.id.accountsView);
        accountListView = (ListView) headerView.findViewById(R.id.lvAccountList);
        spinnerArrow = headerView.findViewById(R.id.ivSpinnerArrow);
        selectedAccountAvatar = (ImageView) headerView.findViewById(R.id.ivAccountPic);
//        tabs = (TabLayout) findViewById(R.id.tabs);

        headerView.findViewById(R.id.bAddAccount).setOnClickListener(v -> {
            setAccountsViewVisibility(false);
            drawer.closeDrawers();
            presenter.onAddAccount();
        });

        headerView.setOnClickListener(v -> {
            setAccountsViewVisibility(accountsView.getVisibility() == View.GONE);
        });

        accountListView.setOnItemClickListener((parent, view, position, id) -> {
            displayAccount(((Account) parent.getItemAtPosition(position)));
            setAccountsViewVisibility(false);
            drawer.closeDrawers();
        });

        if (presenter == null) {
            presenter = ((LittleLight) getApplication()).getComponent().provideMainPresenter();
        }
    }

    private void setAccountsViewVisibility(boolean visible) {
        if (visible) {
            spinnerArrow.setScaleY(-1f);
            accountsView.setVisibility(View.VISIBLE);
        } else {
            accountsView.setVisibility(View.GONE);
            spinnerArrow.setScaleY(1f);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.bindView(this);
        presenter.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (accountSelectedId != null) {
            outState.putInt(MEMBERSHIP_TYPE, accountSelectedId.withMembershipType());
            outState.putString(MEMBERSHIP_ID, accountSelectedId.withMembershipId());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        presenter.unbindView();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            setAccountsViewVisibility(false);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        showFragmentFromMenuId(item.getItemId());
        return true;
    }

    private void showFragmentFromMenuId(int menuId) {
        lastCheckedNavId = menuId;
        mNavigationView.setCheckedItem(menuId);
        switch (menuId) {
            case R.id.nav_inventory:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.account_frame, InventoryFragment.newInstance(accountSelectedId))
                        .commit();
                break;
            case R.id.nav_legend:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.account_frame, LegendFragment.newInstance(accountSelectedId))
                        .commit();
                break;
            case R.id.nav_exotics:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.account_frame, ExoticsFragment.newInstance(accountSelectedId))
                        .commit();
                break;
            case R.id.nav_activities:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.account_frame, ActivityFragment.newInstance(accountSelectedId))
                        .commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setAccountsViewVisibility(false);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setUser(User user) {
        List<Account> registeredAccounts = new ArrayList<>(user.allRegisteredAccounts());
        AccountsAdapter accountsAdapter = new AccountsAdapter(getApplicationContext(), R.layout.view_account_row, registeredAccounts);

        accountsAdapter.setOnItemRemoveClickListener((view, position) -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage("Are you sure you want to remove this account?")
                    .setPositiveButton("Yes", (dialog, which) -> presenter.onRemoveAccount(accountsAdapter.getItem(position)))
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        accountListView.setAdapter(accountsAdapter);

        if (!registeredAccounts.isEmpty()) {
            displayAccount(accountSelectedId == null ? registeredAccounts.get(0) : user.ofId(accountSelectedId));
        }
    }

    @Override
    public void displayAccount(Account anAccount) {
        accountSelectedId = anAccount.withId();
        showFragmentFromMenuId(lastCheckedNavId == -1 ? R.id.nav_inventory : lastCheckedNavId);

        ((TextView) headerView.findViewById(R.id.tvAccountNameSelected)).setText(anAccount.withName());
        Picasso.with(getApplicationContext())
                .load(anAccount.profilePath())
                .transform(new CircleTransform())
                .fit()
                .into(selectedAccountAvatar);
    }

    @Override
    public void showError(String localizedMessage) {
        Snackbar.make(findViewById(R.id.account_frame), localizedMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void addAccount(Account accountToAdd) {
        ((AccountsAdapter) accountListView.getAdapter()).add(accountToAdd);
    }

    @Override
    public void updateAccount(Account accountUpdated) {
        ((AccountsAdapter) accountListView.getAdapter()).updateAccount(accountUpdated);
        if (!accountSelectedId.equals(accountUpdated.withId())) {
            displayAccount(accountUpdated);
        }
    }

    @Override
    public void removeAccount(Account account) {
        ((AccountsAdapter) accountListView.getAdapter()).remove(account);
        if (account.withId().equals(accountSelectedId)) {
            accountSelectedId = null;
            getSupportFragmentManager()
                    .popBackStack();
            if (!accountListView.getAdapter().isEmpty()) {
                displayAccount(((AccountsAdapter) accountListView.getAdapter()).getItem(0));
            }
        }
    }

    @Override
    public void showLoading(boolean bool) {
        if (bool) {
            findViewById(R.id.pbMain).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.pbMain).setVisibility(View.GONE);
        }
    }

    @Override
    public void showChoosePlatformDialog(String[] platforms) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your platform")
                .setItems(platforms, (dialogInterface, i) -> presenter.onPlatformChosen(i))
                .show();
    }

    @Override
    public void showWebSignIn(String url, UserPresenter.AccountCredentialsCallback accountCredentialsCallback) {
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();

        SignInFragment signInFragment = SignInFragment.newInstance(url);
        signInFragment.setCallback(accountCredentialsCallback);

        fragTransaction.add(R.id.drawer_layout, signInFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showUpdateCredentialsNowDialog(String accountName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Account credentials expired")
                .setMessage("Credentials for " + accountName + " expired. Would you like to update them now?")
                .setPositiveButton("Yes", (dialogInterface, i) -> presenter.onUpdateCredentialsResponse(true))
                .setNegativeButton("Remind me later", (dialogInterface, i) -> presenter.onUpdateCredentialsResponse(false))
                .show();
    }

    public AppBarLayout getAppBar() {
        return (AppBarLayout) findViewById(R.id.app_bar);
    }
}