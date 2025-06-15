package njust.dzh.ordersystem.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import njust.dzh.ordersystem.Activity.FoodActivity;
import njust.dzh.ordersystem.Bean.Food;
import njust.dzh.ordersystem.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{
    private Context mContext;
    private List<Food> mFoodList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.food_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Food food = mFoodList.get(position);
                Intent intent = new Intent(mContext, FoodActivity.class);
                intent.putExtra(FoodActivity.FOOD_DATA, food);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = mFoodList.get(position);
        holder.foodName.setText(food.getName());
        Glide.with(mContext).load(food.getImageId()).into(holder.foodImage);
        holder.foodPrice.setText(food.getPrice());
        holder.foodScore.setText(food.getScore());
        holder.foodComment.setText(food.getComment());
    }

    @Override
    public int getItemCount() {
        return mFoodList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView foodImage;
        TextView foodName;
        TextView foodScore;
        TextView foodPrice;
        TextView foodComment;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            foodPrice = itemView.findViewById(R.id.food_price);
            foodScore = itemView.findViewById(R.id.food_score);
            foodComment = itemView.findViewById(R.id.food_comment);
        }
    }
    public FoodAdapter(List<Food> foodList) {
        mFoodList = foodList;
    }


}
