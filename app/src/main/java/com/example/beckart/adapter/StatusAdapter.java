package com.example.beckart.adapter;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.beckart.R;
import com.example.beckart.ViewModel.AddFavoriteViewModel;
import com.example.beckart.ViewModel.FromCartViewModel;
import com.example.beckart.ViewModel.RemoveFavoriteViewModel;
import com.example.beckart.ViewModel.ToCartViewModel;
import com.example.beckart.databinding.SearchListItemBinding;
import com.example.beckart.databinding.StatusListItemBinding;
import com.example.beckart.model.Cart;
import com.example.beckart.model.Favorite;
import com.example.beckart.model.Product;
import com.example.beckart.storage.LoginUtils;
import com.example.beckart.utils.RequestCallback;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.beckart.utils.Constant.LOCALHOST;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder>{

    private Context mContext;
    // Declare an arrayList for products
    private List<Product> productList;

    private Product currentProduct;

    private StatusAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface StatusAdapterOnClickHandler {
        void onClick(Product product);
    }

    public StatusAdapter(Context mContext,List<Product> productList,StatusAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.productList = productList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        StatusListItemBinding statusListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.status_list_item,parent,false);
        return new StatusViewHolder(statusListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        currentProduct = productList.get(position);
        holder.binding.txtProductName.setText(currentProduct.getProductName());
        holder.binding.orderQuantity.setText(String.valueOf(currentProduct.getOrderQuantity()));

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(currentProduct.getProductPrice());
        holder.binding.txtProductPrice.setText(mContext.getResources().getString(R.string.Rs)+" "+formattedPrice);

        // Load the Product image into ImageView
        String imageUrl = LOCALHOST + currentProduct.getProductImage().replaceAll("\\\\", "/");
        Glide.with(mContext)
                .load(imageUrl)
                .into(holder.binding.imgProductImage);

    }

    @Override
    public int getItemCount() {
        if (productList == null) {
            return 0;
        }
        return productList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class StatusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        private final StatusListItemBinding binding;

        private StatusViewHolder(StatusListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // Get position of the product
            currentProduct = productList.get(position);

            switch (v.getId()) {
                case R.id.card_view:
                    // Send product through click
                    clickHandler.onClick(currentProduct);
                    break;
            }
        }



        private void showSnackBar(String text) {
            Snackbar.make(itemView, text, Snackbar.LENGTH_SHORT).show();
        }


    }
}
