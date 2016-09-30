package com.jam01.littlelight.adapter.android.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.presenter.UserPresenter;
import com.jam01.littlelight.domain.identityaccess.Account;
import com.jam01.littlelight.domain.identityaccess.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, UserPresenter.MainView {
    private Account accountSelected;
    private UserPresenter presenter;
    private ListView accountListView;
    private View headerView;
    private View spinnerArrow;
    private ImageView accountPic;
//    private Map<String, Account> accountMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerView = navigationView.getHeaderView(0);

        accountListView = (ListView) headerView.findViewById(R.id.lvAccountList);
        spinnerArrow = (View) headerView.findViewById(R.id.ivSpinnerArrow);
        accountPic = (ImageView) headerView.findViewById(R.id.ivAccountPic);

        headerView.findViewById(R.id.bAddAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddAccount();
            }
        });

        headerView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              View toHide = v.findViewById(R.id.accountsView);
                                              if (toHide.getVisibility() == View.GONE) {
                                                  spinnerArrow.setScaleY(-1f);
                                                  toHide.setVisibility(View.VISIBLE);
                                              } else {
                                                  toHide.setVisibility(View.GONE);
                                                  spinnerArrow.setScaleY(1f);
                                              }
                                          }
                                      }

        );

        accountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   @Override
                                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                       accountSelected = ((Account) parent.getItemAtPosition(position));
                                                       ((TextView) headerView.findViewById(R.id.tvAccountNameSelected)).setText(accountSelected.withName());
                                                       Picasso.with(getApplicationContext())
                                                               .load(accountSelected.profilePath())
                                                               .resize(90, 90)
                                                               .placeholder(R.mipmap.ic_launcher)
                                                               .centerCrop()
                                                               .into(accountPic);
                                                       View toHide = headerView.findViewById(R.id.accountsView);
                                                       if (toHide.getVisibility() == View.GONE)
                                                           toHide.setVisibility(View.VISIBLE);
                                                       else toHide.setVisibility(View.GONE);
                                                       getSupportFragmentManager()
                                                               .beginTransaction()
                                                               .replace(R.id.account_frame, InventoryFragment.newInstance(accountSelected.withId()))
                                                               .commit();
                                                       drawer.closeDrawers();
                                                   }
                                               }

        );

        if (presenter == null) {
            presenter = ((LittleLight) getApplication()).getComponent().provideMainPresenter();
        }

        presenter.bindView(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        presenter.unbindView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.account_frame, InventoryFragment.newInstance(accountSelected.withId()))
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loadSignInView() {
        startActivity(new Intent(this, SignInActivity.class));
    }

    @Override
    public void setUser(User user) {
        accountListView.setAdapter(new AccountsAdapter(getApplicationContext(), R.layout.account_row, new ArrayList<>(user.allRegisteredAccounts())));
        accountSelected = ((Account) accountListView.getItemAtPosition(0));
        ((TextView) headerView.findViewById(R.id.tvAccountNameSelected)).setText(accountSelected.withName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.account_frame, InventoryFragment.newInstance(accountSelected.withId()))
                .commit();
    }

    @Override
    public void updateAccount(Account accountUpdated) {
        if (accountSelected.withId().equals(accountUpdated.withId())) {
            ((TextView) headerView.findViewById(R.id.tvAccountNameSelected)).setText(accountSelected.withName());
            Picasso.with(getApplicationContext())
                    .load(accountUpdated.profilePath())
//                    .resize(90, 90)
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(accountPic);
        }
        ((AccountsAdapter) accountListView.getAdapter()).updateAccount(accountUpdated);
    }
}
