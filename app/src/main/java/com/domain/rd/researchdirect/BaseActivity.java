package com.domain.rd.researchdirect;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dipenp
 *         <p>
 *         This activity will add Navigation Drawer for our application and all the code related to navigation drawer.
 *         We are going to extend all our other activites from this BaseActivity so that every activity will have Navigation Drawer in it.
 *         This activity layout contain one frame layout in which we will add our child activity layout.
 */
//public class BaseActivity extends Activity {
public class BaseActivity extends Activity  implements SearchView.OnQueryTextListener{
    //private List<String> items = db.getItems();
    /**
     * Frame layout: Which is going to be used as parent layout for child activity layout.
     * This layout is protected so that child activity can access this
     */
    protected FrameLayout frameLayout;

    /**
     * ListView to add navigation drawer item in it.
     * We have made it protected to access it in child class. We will just use it in child class to make item selected according to activity opened.
     */

    protected ListView mDrawerList;

    /**
     * List item array for navigation drawer items.
     */
    protected String[] listArray = {"Research organisations guide", "Conferences & Events", "News & Highlights", "Jobs & Careers", "Agora", "Research Direct Match", "My account", "Home page", "Log out"};

    /**
     * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.
     */
    protected static int position;

    /**
     * This flag is used just to check that launcher activity is called first time
     * so that we can open appropriate Activity on launch and make list item position selected accordingly.
     */
    private static boolean isLaunch = true;

    /**
     * Base layout node of this Activity.
     */
    private DrawerLayout mDrawerLayout;

    /**
     * Drawer listner class for drawer open, close etc.
     */
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public SearchView mSearchView;
    private TextView mStatusView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_drawer_base_layout);

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, listArray));
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openActivity(position);
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,						/* host Activity */
                mDrawerLayout, 				/* DrawerLayout object */
                R.drawable.ic_launcher,     /* nav drawer image to replace 'Up' caret */
                R.string.open_drawer,       /* "open drawer" description for accessibility */
                R.string.close_drawer)      /* "close drawer" description for accessibility */ {
            @Override
            public void onDrawerClosed(View drawerView) {
                getActionBar().setTitle(listArray[position]);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(getString(R.string.app_name));
                // getActionBar().set
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);


        /**
         * As we are calling BaseActivity from manifest file and this base activity is intended just to add navigation drawer in our app.
         * We have to open some activity with layout on launch. So we are checking if this BaseActivity is called first time then we are opening our first activity.
         * */
        if (isLaunch) {
            /**
             *Setting this flag false so that next time it will not open our first activity.
             *We have to use this flag because we are using this BaseActivity as parent activity to our other activity.
             *In this case this base activity will always be call when any child activity will launch.
             */
            isLaunch = false;
            openActivity(7);
        }


        mStatusView = (TextView) findViewById(R.id.status_text);
        /**
         *  We will not use setContentView in this activty
         *  Rather than we will use layout inflater to add view in FrameLayout of our base activity layout*/

        /**
         * Adding our layout to parent class frame layout.
         */
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

    }

    public void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        mSearchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {
        mStatusView.setText("Query = " + newText);
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        mStatusView.setText("Query = " + query + " : submitted");
        return false;
    }

    public boolean onClose() {
        mStatusView.setText("Closed!");
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return false;
    }

    /**
     * @param position Launching activity when any list item is clicked.
     */
    protected void openActivity(int position) {

        /**
         * We can set title & itemChecked here but as this BaseActivity is parent for other activity,
         * So whenever any activity is going to launch this BaseActivity is also going to be called and
         * it will reset this value because of initialization in onCreate method.
         * So that we are setting this in child activity.
         */
//		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                startActivity(new Intent(this, ActivityROG.class));
                break;
            case 1:
                startActivity(new Intent(this, ActivityCE.class));
                break;
            case 2:
                startActivity(new Intent(this, ActivityNH.class));
                break;
            case 3:
                startActivity(new Intent(this, ActivityJC.class));
                break;
            case 4:
                startActivity(new Intent(this, ActivityAgora.class));
                break;
            case 5:
                startActivity(new Intent(this, ActivityRDM.class));
                break;
            case 6:
                startActivity(new Intent(this, ActivityMyAccount.class));
                break;
            case 7:
                startActivity(new Intent(this, ActivityHome.class));
                break;
            case 8:
                startActivity(new Intent(this, ActivityLogout.class));
                break;
            default:
                break;
        }

        //todo: do testów
        Toast.makeText(this, "Selected Item Position::" + position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /* We can override onBackPressed method to toggle navigation drawer*/
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }

    private List<String> items;

    public Menu menu;

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);


        this.menu = menu;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();

            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {

                    loadHistory(query);

                    return true;

                }
            });

        }

        return super.onCreateOptionsMenu(menu);

    }

    public void search(String query) {
        // reset loader, swap cursor, etc.
    }
    // History
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void loadHistory(String query) {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            // Cursor
            String[] columns = new String[] { "_id", "text" };
            Object[] temp = new Object[] { 0, "default" };

            MatrixCursor cursor = new MatrixCursor(columns);
            if(items==null) items = new ArrayList<String>();
            for(int i = 0; i < items.size(); i++) {

                temp[0] = i;
                temp[1] = items.get(i);//replaced s with i as s not used anywhere.

                cursor.addRow(temp);

            }

            // SearchView
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            final SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();

            search.setSuggestionsAdapter(new ExampleAdapter(this, cursor, items));

        }

    }

}
