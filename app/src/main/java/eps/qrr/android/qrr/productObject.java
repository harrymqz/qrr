package eps.qrr.android.qrr;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ruben on 17/01/16.
 */
public class productObject {

    private Map productsToOrder;

    public productObject() {
        this.productsToOrder = new HashMap<>();
    }

    public void setProduct(String key, String value) {

        this.productsToOrder.put(key, value);

    }

    public Map getProduct() {
        return productsToOrder;
    }

    public void deleteProduct(String key) {
        if (this.productsToOrder.containsKey(key)) {
            this.productsToOrder.remove(key);
        }
    }

}
