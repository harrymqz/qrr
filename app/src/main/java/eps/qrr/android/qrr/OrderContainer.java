package eps.qrr.android.qrr;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by ruben on 21/01/16.
 */
public class OrderContainer {
    private final ArrayList<ProductOrderModel> ORDERS;
    private static OrderContainer instance;
    private ArrayAdapter instanceAdapter;

    private OrderContainer() {
        this.ORDERS = new ArrayList<>();
    }

    public static synchronized OrderContainer getInstances() {
        if (instance == null) {
            instance = new OrderContainer();
        }
        return instance;
    }

    public void addOrder(ProductOrderModel order) {
        this.ORDERS.add(order);
    }

    public JSONArray toJSONArray() throws JSONException {
        JSONArray array = new JSONArray();
        for (ProductOrderModel order : ORDERS) {
            array.put(order.toJSON());
        }
        return array;
    }

    public ArrayAdapter getAdapter(Context c) {
        // return new ProductOrderAdapter(c, ORDERS);
        if (instanceAdapter == null) {
            instanceAdapter = new ProductOrderAdapter(c, ORDERS);
        }

        return instanceAdapter;
    }
}
