package njust.dzh.ordersystem.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import njust.dzh.ordersystem.Bean.Cart;
import njust.dzh.ordersystem.DataBase.CartDao;
import njust.dzh.ordersystem.Fragment.CartFragment;
import njust.dzh.ordersystem.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context mContext;
    private List<Cart> mCartList;
    private CartDao cartDao;
    private Handler handler = new Handler();

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView cartImage;
        TextView cartName;
        TextView cartPrice;
        TextView cartNum;
        TextView cartAdd;
        TextView cartMinus;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            cartImage = itemView.findViewById(R.id.cart_image);
            cartName = itemView.findViewById(R.id.cart_name);
            cartPrice = itemView.findViewById(R.id.cart_price);
            cartNum = itemView.findViewById(R.id.cart_num);
            cartAdd = itemView.findViewById(R.id.cart_add);
            cartMinus = itemView.findViewById(R.id.cart_minus);
        }
    }
    // 构造函数
    public CartAdapter(List<Cart> cartList) {
        mCartList = cartList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        // 卡片布局长按删除
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = holder.getAdapterPosition();
                Cart cart = mCartList.get(position);
                cartDao = new CartDao(mContext);
                // 提示框，确定按钮和取消按钮
                AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("警告")
                        .setMessage("您确定要删除此商品吗？")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cartDao.openDB();
                                cartDao.deleteCart(cart);
                                cartDao.closeDB();
                                Toast.makeText(mContext, "删除成功！请下拉刷新界面~", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(mContext, "删除已取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                return true;
            }
        });

        // 子项的增加按钮
        holder.cartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Cart cart = mCartList.get(position);
                cartDao = new CartDao(mContext);
                cartDao.openDB();
                cartDao.addCartNum(cart);
                String curPrice = cartDao.getPrice(cart);
                cartDao.closeDB();
                String curCartNum = holder.cartNum.getText().toString();
                int curNum = Integer.parseInt(curCartNum);
                holder.cartNum.setText("" + (curNum + 1));
                holder.cartPrice.setText("" + curPrice);
                Toast.makeText(mContext, "增加成功", Toast.LENGTH_SHORT).show();
            }
        });
        // 子项的减少按钮
        holder.cartMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Cart cart = mCartList.get(position);
                // 对表数据进行更新
                cartDao = new CartDao(mContext);
                cartDao.openDB();
                cartDao.minusCartNum(cart);
                String curPrice = cartDao.getPrice(cart);
                cartDao.closeDB();

                String curCartNum = holder.cartNum.getText().toString();
                int curNum = Integer.parseInt(curCartNum);
                if (curNum > 1) {
                    holder.cartNum.setText("" + (curNum - 1));
                    holder.cartPrice.setText(curPrice);
                    Toast.makeText(mContext, "减少成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cart cart = mCartList.get(position);
        holder.cartName.setText(cart.getName());
        Glide.with(mContext).load(cart.getImageId()).into(holder.cartImage);
        holder.cartPrice.setText(cart.getPrice());
        holder.cartNum.setText(""+cart.getNum());
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    // Handler封装成发送消息的方法
    public void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = CartFragment.UPDATE_CART;
                handler.sendMessage(message);
            }
        }).start();
    }
}


