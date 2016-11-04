package base.rechargeapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import base.rechargeapp.AppPrefernces.AppPreference;
import base.rechargeapp.R;
import base.rechargeapp.adapter.DrawerAdapter;
import base.rechargeapp.beans.Register.Response.Response;
import base.rechargeapp.utils.AppConstant;

/**
 * Organization LPTPL
 * Created by Vaibhav Jain on 28/6/16.
 * <p>
 * Fragment to be displayed as Left menu drawer
 */

public class DrawerFragment extends Fragment {

    private View mView;
    private RecyclerView drawerListItem;
    private DrawerAdapter drawerAdapter;
    private List<String> list;
    private List<String> iconList;
    private View containerView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FragmentDrawerListener drawerListener;
    private TextView mUserName ;
    private int selectedPosition = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /**
         * Any kind of layout can be inflated as drawer layout
         */
        mView = inflater.inflate(R.layout.drawer_activity, container, false);

        drawerListItem = (RecyclerView) mView.findViewById(R.id.recylerListItem);

        mUserName = (TextView) mView.findViewById(R.id.txt_username);

        drawerListItem.addOnItemTouchListener(new RecyclerTouch(getActivity(), drawerListItem, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                // position of the RecyclerView item clicked
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
                selectedPosition = position ;
                initAdapter(selectedPosition);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setLayoutManager();
        prepareList();
        initAdapter(selectedPosition);
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    private void prepareList() {

        String leftMenuItems[] = getActivity().getResources().getStringArray(R.array.leftMenuItems);

        list = new ArrayList<>();
        list =  (List<String>)Arrays.asList(leftMenuItems);


        String leftMenuIcons[] = getActivity().getResources().getStringArray(R.array.leftMenuIcons);

        iconList = new ArrayList<>();
        iconList =  (List<String>)Arrays.asList(leftMenuIcons);
    }

    /**
     * Set Layout Manager for the recycler View
     */
    private void setLayoutManager() {

        if (drawerListItem != null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            drawerListItem.setLayoutManager(layoutManager);
        }
    }


    /**
     * Initialize tha adapter if null and
     * notify if not null
     */
    public void initAdapter(int selectedPosition) {

        if (drawerAdapter == null) {
            drawerAdapter = new DrawerAdapter(list,iconList,getActivity(),selectedPosition);
            drawerListItem.setAdapter(drawerAdapter);

        } else {
            drawerAdapter.setUpDateList(list,iconList,getActivity(),selectedPosition);
            drawerAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Method is used to set this fragment as a Drawer Fragment
     *
     * @param fragmentId
     * @param drawerLayout
     * @param toolbar
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getDataFromPreferences();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    public boolean checkIfDrawerOpened() {
        if(mDrawerLayout.isDrawerOpen(containerView))
            return true ;
        else
            return false ;
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(containerView);
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }


    /**
     * RecyclerView have onItemTouchListner inspite of onItemClickListner
     */

    class RecyclerTouch implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouch(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        getDataFromPreferences();
    }

    private void getDataFromPreferences() {

        AppPreference mAppPreference = AppPreference.getInstance(getActivity());

        if(mAppPreference.getString(AppConstant.USERDATA) != null && !mAppPreference.getString(AppConstant.USERDATA).equals("")){
            Response mUserData = new Gson().fromJson(mAppPreference.getString(AppConstant.USERDATA),Response.class);
            setData(mUserData);
        }
    }

    private void setData(Response mUserData) {

        mUserName.setText("Hi , "+mUserData.getFirstName() + " " + mUserData.getLastName());
    }
}
