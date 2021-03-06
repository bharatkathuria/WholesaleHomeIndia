package com.becxpress.whi.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.becxpress.whi.R;
import com.becxpress.whi.ViewModel.ProductViewModel;
import com.becxpress.whi.adapter.ProductAdapter;
import com.becxpress.whi.databinding.ActivityProductBinding;
import com.becxpress.whi.model.Product;
import com.becxpress.whi.receiver.NetworkChangeReceiver;
import com.becxpress.whi.storage.LoginUtils;
import com.becxpress.whi.utils.OnNetworkListener;
import com.becxpress.whi.utils.Slide;

import java.util.ArrayList;

import static com.becxpress.whi.utils.Constant.CATEGORY;
import static com.becxpress.whi.utils.Constant.PRODUCT;
import static com.becxpress.whi.utils.InternetUtils.isNetworkConnected;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener, OnNetworkListener, ProductAdapter.ProductAdapterOnClickHandler,
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ProductActivity";
    private ActivityProductBinding binding;

    private ProductAdapter cutleryAdapter;
    private ProductAdapter cookwareAdapter;
    private ProductAdapter cookerAdapter;

    private ProductViewModel productViewModel;


    private Snackbar snack;


    private NetworkChangeReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product);

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadCookware("cookware",userID);
        productViewModel.loadCutlery("cutlery", userID);
        productViewModel.loadCookers("pressure cooker",userID);


        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        binding.included.content.txtSeeAllCutlery.setOnClickListener(this);
        binding.included.content.txtSeeAllCookware.setOnClickListener(this);
        binding.included.content.txtSeeAllCookers.setOnClickListener(this);

        binding.included.txtSearch.setOnClickListener(this);

        setUpViews();

        getCutlery();
        getCookware();
        getCookers();

        flipImages(Slide.getSlides());

        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);
    }


    private void setUpViews() {
        Toolbar toolbar = binding.included.toolbar;
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        View headerContainer = binding.navView.getHeaderView(0);
        TextView userName = headerContainer.findViewById(R.id.nameOfUser);
        userName.setText(LoginUtils.getInstance(this).getUserInfo().getName());
        TextView userEmail = headerContainer.findViewById(R.id.emailOfUser);
        userEmail.setText(LoginUtils.getInstance(this).getUserInfo().getEmail());

        binding.included.content.listOfCutlery.setHasFixedSize(true);
        binding.included.content.listOfCutlery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfCutlery.setItemAnimator(null);

        binding.included.content.listOfCookware.setHasFixedSize(true);
        binding.included.content.listOfCookware.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfCookware.setItemAnimator(null);

        binding.included.content.listOfCookers.setHasFixedSize(true);
        binding.included.content.listOfCookers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.included.content.listOfCookers.setItemAnimator(null);

        cutleryAdapter = new ProductAdapter(this, this);
        cookwareAdapter = new ProductAdapter(this, this);
        cookerAdapter = new ProductAdapter(this,this);

    }

    private void getCutlery() {
        if (isNetworkConnected(this)) {
            productViewModel.productPagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    cutleryAdapter.submitList(products);
                }
            });

            binding.included.content.listOfCutlery.setAdapter(cutleryAdapter);
            cutleryAdapter.notifyDataSetChanged();
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getCookware() {
        if (isNetworkConnected(this)) {
            productViewModel.cookwarePagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    cookwareAdapter.submitList(products);
                }
            });

            binding.included.content.listOfCookware.setAdapter(cookwareAdapter);
            cookwareAdapter.notifyDataSetChanged();
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }
    }

    private void getCookers() {
        if (isNetworkConnected(this)) {
            productViewModel.cookerPagedList.observe(this, new Observer<PagedList<Product>>() {
                @Override
                public void onChanged(@Nullable PagedList<Product> products) {
                    cookerAdapter.submitList(products);
                }
            });

            binding.included.content.listOfCookers.setAdapter(cookerAdapter);
            cookerAdapter.notifyDataSetChanged();
        } else {
            showOrHideViews(View.INVISIBLE);
            showSnackBar();
        }

    }

    private void flipImages(ArrayList<Integer> images) {
        for (int image : images) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(image);
            binding.included.content.imageSlider.addView(imageView);
        }

        binding.included.content.imageSlider.setFlipInterval(2000);
        binding.included.content.imageSlider.setAutoStart(true);

        // Set the animation for the view that enters the screen
        binding.included.content.imageSlider.setInAnimation(this, R.anim.slide_in_right);
        // Set the animation for the view leaving th screen
        binding.included.content.imageSlider.setOutAnimation(this, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtSeeAllCutlery:
                Intent cutleryIntent = new Intent(this, AllCutleryActivity.class);
                startActivity(cutleryIntent);

                break;
            case R.id.txtSeeAllCookware:
                Intent cookwareIntent = new Intent(this, AllCookwareActivity.class);
                startActivity(cookwareIntent);
                break;
            case R.id.txtSeeAllCookers:
                Intent cookerIntent = new Intent(this, AllCookersActivity.class);
                startActivity(cookerIntent);
                break;
            case R.id.txtSearch:
                Intent searchIntent = new Intent(ProductActivity.this, com.becxpress.whi.view.SearchActivity.class);
                startActivity(searchIntent);
                break;
        }
    }

    private void showNormalAlertDialog(String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton(R.string.ok, null).show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.darkGreen));
    }






    public void showSnackBar() {
        snack.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        snack.show();
    }

    public void hideSnackBar() {
        snack.dismiss();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public void onNetworkConnected() {
        hideSnackBar();
        showOrHideViews(View.VISIBLE);
        getCutlery();
        getCookware();
        getCookers();
    }

    @Override
    public void onNetworkDisconnected() {
        showSnackBar();
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(ProductActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem addMenu = menu.findItem(R.id.action_addProduct);
        if (LoginUtils.getInstance(this).getUserInfo().isAdmin()) {
            addMenu.setVisible(true);
        } else {
            addMenu.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent cartIntent = new Intent(this, CartActivity.class);
                startActivity(cartIntent);
                return true;
            case R.id.action_addProduct:
                Intent addProductIntent = new Intent(this, AddProductActivity.class);
                startActivity(addProductIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOrHideViews(int view) {
        binding.included.content.textViewCutlery.setVisibility(view);
        binding.included.content.txtSeeAllCutlery.setVisibility(view);
        binding.included.content.textViewCookware.setVisibility(view);
        binding.included.content.txtSeeAllCookware.setVisibility(view);
        binding.included.content.textViewCookers.setVisibility(view);
        binding.included.content.txtSeeAllCookers.setVisibility(view);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_cookers) {
            goToCategoryActivity("pressure cooker");
        } else if (id == R.id.nav_cookware) {
            goToCategoryActivity("cookware");
        } else if (id == R.id.nav_cutlery) {
            goToCategoryActivity("cutlery");
        }
        else if (id == R.id.nav_trackOrder) {
            Intent orderIntent = new Intent(this, OrdersActivity.class);
            orderIntent.putExtra("track",true);
            startActivity(orderIntent);
        } else if (id == R.id.nav_myAccount) {
            Intent accountIntent = new Intent(this, AccountActivity.class);
            startActivity(accountIntent);
        } else if (id == R.id.nav_wishList) {
            Intent wishListIntent = new Intent(this, com.becxpress.whi.view.WishListActivity.class);
            startActivity(wishListIntent);
        }
        else if (id == R.id.nav_myCart) {
            Intent cartIntent = new Intent(this, com.becxpress.whi.view.CartActivity.class);
            startActivity(cartIntent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToCategoryActivity(String category) {
        Intent categoryIntent = new Intent(this, CategoryActivity.class);
        categoryIntent.putExtra(CATEGORY, category);
        startActivity(categoryIntent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            closeApplication();
        }
    }

    private void closeApplication() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(R.string.want_to_exit)
                .setPositiveButton(R.string.ok, (dialog, which) -> finish())
                .setNegativeButton(R.string.cancel, null)
                .show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.darkGreen));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.darkGreen));
    }


    @Override
    protected void onResume() {
        super.onResume();
        productViewModel.invalidate();
        getCutlery();
        getCookware();
        getCookers();
    }



}
