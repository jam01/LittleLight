package com.jam01.littlelight.adapter.android.presentation.user;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bungie.netplatform.destiny.representation.Endpoints;
import com.jam01.littlelight.R;
import com.jam01.littlelight.adapter.android.LittleLight;
import com.jam01.littlelight.adapter.android.presentation.activity.ActivityFragment;
import com.jam01.littlelight.adapter.android.presentation.inventory.ExoticsFragment;
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
    private ImageView accountPic;
    private TabLayout tabs;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            accountSelectedId = new AccountId(savedInstanceState.getInt(MEMBERSHIP_TYPE), savedInstanceState.getString(MEMBERSHIP_ID));
        }

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
        accountsView = headerView.findViewById(R.id.accountsView);
        accountListView = (ListView) headerView.findViewById(R.id.lvAccountList);
        spinnerArrow = headerView.findViewById(R.id.ivSpinnerArrow);
        accountPic = (ImageView) headerView.findViewById(R.id.ivAccountPic);
        tabs = (TabLayout) findViewById(R.id.tabs);

        headerView.findViewById(R.id.bAddAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountsView.setVisibility(View.GONE);
                spinnerArrow.setScaleY(1f);
                drawer.closeDrawers();
                presenter.onAddAccount();
            }
        });

        headerView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (accountsView.getVisibility() == View.GONE) {
                                                  spinnerArrow.setScaleY(-1f);
                                                  accountsView.setVisibility(View.VISIBLE);
                                              } else {
                                                  accountsView.setVisibility(View.GONE);
                                                  spinnerArrow.setScaleY(1f);
                                              }
                                          }
                                      }
        );

        accountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   @Override
                                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                       Account toDraw = ((Account) parent.getItemAtPosition(position));
                                                       displayAccount(toDraw);
                                                       accountsView.setVisibility(View.GONE);
                                                       spinnerArrow.setScaleY(1f);
                                                       drawer.closeDrawers();
                                                   }
                                               }
        );

        if (presenter == null) {
            presenter = ((LittleLight) getApplication()).getComponent().provideMainPresenter();
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
        switch (item.getItemId()) {
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
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // TODO: 10/8/16 Pretty this up
    @Override
    public void showWebSignIn() {
        final int[] membershipType = new int[1];
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Little Light")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Where should I look for you, Guardian?")
                .setPositiveButton("PSN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        membershipType[0] = 2;
                        final SignInFragment signInFragment = SignInFragment.newInstance(Endpoints.PSN_AUTH_URL);
                        signInFragment.setOnCookiesInterceptedListener(new SignInFragment.OnCookiesInterceptedListener() {
                            @Override
                            public void onCookiesIntercepted(String[] cookies) {
                                presenter.onWebSignInCompleted(membershipType[0], cookies);
                                signInFragment.dismiss();
                            }
                        });
                        signInFragment.show(getSupportFragmentManager(), "SignInDialog");
                    }
                })
                .setNegativeButton("Xbox Live", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        membershipType[0] = 1;
                        final SignInFragment signInFragment = SignInFragment.newInstance(Endpoints.XBOX_AUTH_URL);
                        signInFragment.setOnCookiesInterceptedListener(new SignInFragment.OnCookiesInterceptedListener() {
                            @Override
                            public void onCookiesIntercepted(String[] cookies) {
                                presenter.onWebSignInCompleted(membershipType[0], cookies);
                                signInFragment.dismiss();
                            }
                        });
                        signInFragment.show(getSupportFragmentManager(), "SignInDialog");
                    }
                })
                .create()
                .show();

    }

    @Override
    public void showWebSignInForUpdatingCredentials(final AccountId accountId) {
        final SignInFragment signInFragment = SignInFragment.newInstance(accountId.withMembershipType() == 2 ? Endpoints.PSN_AUTH_URL : Endpoints.XBOX_AUTH_URL);
        signInFragment.setOnCookiesInterceptedListener(new SignInFragment.OnCookiesInterceptedListener() {
            @Override
            public void onCookiesIntercepted(String[] cookies) {
                presenter.onWebCredentialsUpdated(accountId, cookies);
                signInFragment.dismiss();
            }
        });
        signInFragment.show(getSupportFragmentManager(), "SignInDialog");
    }

    @Override
    public void setUser(User user) {
        final Context context = this;
        final List<Account> registeredAccounts = new ArrayList<>(user.allRegisteredAccounts());
        final AccountsAdapter accountsAdapter = new AccountsAdapter(getApplicationContext(), R.layout.view_account_row, registeredAccounts);
        accountsAdapter.setOnItemRemoveClickListener(new AccountsAdapter.OnItemRemoveClickListener() {
            @Override
            public void onItemRemoveClick(View view, final int position) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.onRemoveAccount(accountsAdapter.getItem(position));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        accountListView.setAdapter(accountsAdapter);

        if (!registeredAccounts.isEmpty()) {
            if (accountSelectedId == null) {
                displayAccount(registeredAccounts.get(registeredAccounts.size() - 1));
            }
        }
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
        if (accountSelectedId.equals(accountUpdated.withId())) {
            displayAccount(accountUpdated);
        }
        ((AccountsAdapter) accountListView.getAdapter()).updateAccount(accountUpdated);
    }

    @Override
    public void removeAccount(Account account) {
        ((AccountsAdapter) accountListView.getAdapter()).remove(account);
        if (account.withId().equals(accountSelectedId)) {
            if (!accountListView.getAdapter().isEmpty()) {
            }
        }
    }

    @Override
    public void displayAccount(Account anAccount) {
        accountSelectedId = anAccount.withId();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.account_frame, InventoryFragment.newInstance(accountSelectedId))
                .commit();

        ((TextView) headerView.findViewById(R.id.tvAccountNameSelected)).setText(anAccount.withName());
        Picasso.with(getApplicationContext())
                .load(anAccount.profilePath())
                .transform(new CircleTransform())
                .fit()
                .into(accountPic);
    }

    @Override
    public void showLoading(boolean bool) {
        if (bool) {
            findViewById(R.id.pbMain).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.pbMain).setVisibility(View.GONE);
        }
    }

    public TabLayout getTabs() {
        return tabs;
    }
}