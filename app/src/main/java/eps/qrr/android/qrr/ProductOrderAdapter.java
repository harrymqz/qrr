package eps.qrr.android.qrr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ProductOrderAdapter extends ArrayAdapter<ProductOrderModel> {

    public ProductOrderAdapter(Context context, ArrayList<ProductOrderModel> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ProductOrderModel order = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.product_order_item_layout, parent, false);
        }
        // Lookup view for data population
        TextView pName = (TextView) convertView.findViewById(R.id.product_name);
        TextView pQty = (TextView) convertView.findViewById(R.id.product_qty);
        TextView pPrice = (TextView) convertView.findViewById(R.id.product_price);
        // Populate the data into the template view using the data object
        pName.setText(order.getProduct().getName());
        pQty.setText(order.getQuantity().toString());
        pPrice.setText(formatPrice(order.getProduct().getPrice()));
        // Return the completed view to render on screen
        return convertView;
    }

    private String formatPrice(BigDecimal value) {
        return String.format("%.2f", value.floatValue());
    }
}
