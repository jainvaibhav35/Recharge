package base.rechargeapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import base.rechargeapp.R;
import base.rechargeapp.appInterface.DialogInterface;
import base.rechargeapp.fragment.AboutFragment;
import base.rechargeapp.fragment.ChangePasswordFragment;
import base.rechargeapp.fragment.CommissionFragment;
import base.rechargeapp.fragment.DailyTransactionFragment;
import base.rechargeapp.fragment.DrawerFragment;
import base.rechargeapp.fragment.HelpFragment;
import base.rechargeapp.fragment.ProfileFragment;
import base.rechargeapp.fragment.RechargeFragment;
import base.rechargeapp.fragment.RechargeStatusFragment;
import base.rechargeapp.fragment.SearchTransactionFragment;
import base.rechargeapp.utils.AppConstant;
import base.rechargeapp.utils.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Organization - LPTPL
 * Vaibhav Jain
 */

public class MainActivity extends AppCompatActivity implements DrawerFragment.FragmentDrawerListener {

    private static final int DIALOGID = 100;
    private final String TAG = getClass().getSimpleName();
    DialogInterface dialogInterface = new DialogInterface() {
        @Override
        public void clickResponse(int Id, String response) {
            if (Id == DIALOGID) {
                if (response.equals(AppConstant.dialogYes)) {
                    // Do here what you want to perform on yes click
                    Utils.logout(MainActivity.this);
                    finish();

                }
            }
        }
    };
    private FrameLayout content_frame;
    private DrawerFragment drawerFragemnt;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private Fragment mFragment;
    private String title;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

//        mToolbar.setTitle(TAG);
//        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // By default this method will replace RechargeFragment on MainActivity

        displayView(-1);


        /**
         * Navigation menu commented because of toggle menu icon
         */

        /*

        mToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_launcher));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ApplicationSnackbar.showSnackBar(mDrawerLayout, getString(R.string.click_action));
                }
            });

        */

    }

    /**
     * This will initialize all views
     */

    private void initViews() {
        content_frame = (FrameLayout) findViewById(R.id.content_frame);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragemnt = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.left_menu_fragment);

        // Method for setting up navigation drawer on the fragmnet
        drawerFragemnt.setUp(R.id.left_menu_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragemnt.setDrawerListener(this);

        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);

    }

    /**
     * Method used to replace fragment
     *
     * @param replaceFragment
     */

    private void replaceFragment(Fragment replaceFragment, boolean addToBackStack) {
        mFragment = replaceFragment;
        if (addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, replaceFragment)
//                    .addToBackStack(replaceFragment.getClass().getName())
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, replaceFragment)
//                    .addToBackStack(replaceFragment.getClass().getName())
                    .commit();
        }
        toolbarTitle.setText(title);
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    */

    /**
     * Method to perform any action onclick of action menu
     * , always return true if your switch case matches
     * otherwise it will create crash .
     *
     * @param item
     * @return
     *//*
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return true;
    }


    /**
     * Overrided method of FramentDrawerListner.
     * <p>
     * Perform action here in switch case where you want to navidgate
     *
     * @param view
     * @param position
     */
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        switch (position) {
            case -1:
                title = getString(R.string.recharge);
                replaceFragment(new RechargeFragment(), true);
                break;
            case 0:
                title = getString(R.string.recharge);
                if (mFragment != null && !(mFragment instanceof RechargeFragment))
                    replaceFragment(new RechargeFragment(), false);
                break;
            case 1:
                title = getString(R.string.search_transaction);
                ;
                if (mFragment != null && !(mFragment instanceof SearchTransactionFragment))
                    replaceFragment(new SearchTransactionFragment(), false);
                break;
                /*title = "Firebase";
                if (mFragment != null && !(mFragment instanceof FireBaseFragment))
                    replaceFragment(new FireBaseFragment());
                break;*/
            case 2:

                title = getString(R.string.daily_transaction);
                ;
                if (mFragment != null && !(mFragment instanceof DailyTransactionFragment))
                    replaceFragment(new DailyTransactionFragment(), false);
                break;

            case 3:
                title = getString(R.string.commission_report);
                if (mFragment != null && !(mFragment instanceof CommissionFragment))
                    replaceFragment(new CommissionFragment(), false);
                break;

            case 4:
                title = getString(R.string.profile);
                if (mFragment != null && !(mFragment instanceof ProfileFragment))
                    replaceFragment(new ProfileFragment(), false);
                break;

            case 5:
                title = getString(R.string.change_password);
                if (mFragment != null && !(mFragment instanceof ChangePasswordFragment))
                    replaceFragment(new ChangePasswordFragment(), false);
                break;

            case 6:
                title = getString(R.string.about);
                if (mFragment != null && !(mFragment instanceof AboutFragment))
                    replaceFragment(new AboutFragment(), false);
                break;

            case 7:
                title = getString(R.string.help);
                if (mFragment != null && !(mFragment instanceof HelpFragment))
                    replaceFragment(new HelpFragment(), false);
                break;

            case 8:
                showLogoutDialog();
                break;
        }

    }

    public void showRechageStatusFragment(String mBalance, String mStatus, String mTransactionId) {
        title = getString(R.string.recharge);
        replaceFragment(new RechargeStatusFragment(mBalance, mStatus, mTransactionId), true);
//        if(mFragment instanceof RechargeStatusFragment)
//        ((RechargeStatusFragment)mFragment).setData(mBalance,mStatus,mTransactionId);
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null) {

           /* if(mFragment instanceof RechargeStatusFragment){
                getSupportFragmentManager().popBackStackImmediate();
            }else {
                // Logic is for that we have to pop all fragments which we have pushed
                if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                    if (drawerFragemnt.checkIfDrawerOpened()) {
                        drawerFragemnt.closeDrawer();
                    } else
                        showExitDialog();
                } else {

                    if (drawerFragemnt.checkIfDrawerOpened()) {
                        drawerFragemnt.closeDrawer();
                    } else {
                        getSupportFragmentManager().popBackStackImmediate();
                        drawerFragemnt.initAdapter(getPositionForBackStackTrace());
                    }
                }
            }*/

            if (mFragment instanceof RechargeFragment) {
                if (drawerFragemnt.checkIfDrawerOpened()) {
                    drawerFragemnt.closeDrawer();
                } else
                    showExitDialog();
            } else {
                if (drawerFragemnt.checkIfDrawerOpened()) {
                    drawerFragemnt.closeDrawer();
                } else {
                    /*getSupportFragmentManager().popBackStackImmediate();
                    drawerFragemnt.initAdapter(getPositionForBackStackTrace());*/
                    replaceFragment(new RechargeFragment(), false);
                    drawerFragemnt.initAdapter(0);
                }
            }

        }

    }

    private int getPositionForBackStackTrace() {

        mFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (mFragment instanceof SearchTransactionFragment) {
            return 1;
        } else if (mFragment instanceof DailyTransactionFragment)
            return 2;
        else if (mFragment instanceof ProfileFragment)
            return 3;
        else if (mFragment instanceof ChangePasswordFragment)
            return 4;
        else if (mFragment instanceof AboutFragment)
            return 5;
        else if (mFragment instanceof HelpFragment)
            return 6;
        else
            return 0;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause ", "onPause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume ", "onResume called");
    }

    public void setTitle(String title) {
        if (toolbarTitle != null && title != null) {
            toolbarTitle.setText(title);
        }
    }


    private void showLogoutDialog() {
//        AppDialog.showDialog(this,100,getResources().getString(R.string.logout),getResources().getString(R.string.logout_description),getResources().getString(R.string.yes),getResources().getString(R.string.no),true,false,dialogInterface);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(getResources().getString(R.string.logout));
        alertDialog.setMessage(getResources().getString(R.string.logout_description));

        alertDialog.setPositiveButton(getResources().getString(R.string.yes), new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                Utils.logout(MainActivity.this);
                finish();
            }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.no), new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void showExitDialog() {
//        AppDialog.showDialog(this,100,getResources().getString(R.string.logout),getResources().getString(R.string.logout_description),getResources().getString(R.string.yes),getResources().getString(R.string.no),true,false,dialogInterface);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(getResources().getString(R.string.exit));
        alertDialog.setMessage(getResources().getString(R.string.exit_description));

        alertDialog.setPositiveButton(getResources().getString(R.string.exit), new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                //Utils.logout(MainActivity.this);
                finish();
            }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.returne), new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }

    /**
     * This Method is used to get either this fragment already exist in stack or not
     *
     * @param position
     * @return
     */
    private boolean getPreviousAvailability(int position) {
        boolean isAddorNot = false;
        switch (position) {
            case 0:
                for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                    if (getSupportFragmentManager().getFragments().get(i) instanceof RechargeFragment) {
                        isAddorNot = false;
                        break;
                    } else
                        isAddorNot = true;
                }
                break;

            case 1:
                for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                    if (getSupportFragmentManager().getFragments().get(i) instanceof SearchTransactionFragment) {
                        isAddorNot = false;
                        break;
                    } else
                        isAddorNot = true;
                }
                break;

            case 2:
                for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                    if (getSupportFragmentManager().getFragments().get(i) instanceof DailyTransactionFragment) {
                        isAddorNot = false;
                        break;
                    } else
                        isAddorNot = true;
                }
                break;


            case 3:
                for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                    if (getSupportFragmentManager().getFragments().get(i) instanceof ProfileFragment) {
                        isAddorNot = false;
                        break;
                    } else
                        isAddorNot = true;
                }
                break;

            case 4:
                for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                    if (getSupportFragmentManager().getFragments().get(i) instanceof ChangePasswordFragment) {
                        isAddorNot = false;
                        break;
                    } else
                        isAddorNot = true;
                }
                break;

            case 5:
                for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                    if (getSupportFragmentManager().getFragments().get(i) instanceof AboutFragment) {
                        isAddorNot = false;
                        break;
                    } else
                        isAddorNot = true;
                }
                break;

            case 6:
                for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                    if (getSupportFragmentManager().getFragments().get(i) instanceof HelpFragment) {
                        isAddorNot = false;
                        break;
                    } else
                        isAddorNot = true;
                }
                break;
        }

        return isAddorNot;
    }

    @Override
    protected void onDestroy() {
        Log.e("Destriy", "destroy");
        super.onDestroy();

    }
}
