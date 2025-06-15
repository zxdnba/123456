package njust.dzh.ordersystem.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import njust.dzh.ordersystem.Bean.Order;
import njust.dzh.ordersystem.DataBase.OrderDao;
import njust.dzh.ordersystem.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context mContext;
    private List<Order> mOrderList;
    private OrderDao orderDao;

    public OrderAdapter(List<Order> orderList) {
        mOrderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = viewHolder.getAdapterPosition();
                Order order = mOrderList.get(position);
                orderDao = new OrderDao(mContext);
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("警告")
                        .setMessage("您确定要删除此订单吗？")
                        .setIcon(R.drawable.ic_delete)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                orderDao.openDB();
                                orderDao.deleteOrder(order);
                                orderDao.closeDB();
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


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Order order = mOrderList.get(position);
        holder.orderId.setText(order.getId());
        holder.orderTime.setText(order.getTime());
        holder.orderPrice.setText(order.getPrice());
        holder.orderContent.setText(order.getContent());
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView orderId;
        TextView orderTime;
        TextView orderPrice;
        TextView orderContent;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            orderId = itemView.findViewById(R.id.orderId);
            orderTime = itemView.findViewById(R.id.orderTime);
            orderPrice = itemView.findViewById(R.id.orderPrice);
            orderContent = itemView.findViewById(R.id.orderContent);
        }
    }


}
