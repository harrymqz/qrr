
package eps.qrr.android.qrr;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductOrderModel {

    private ProductModel product;

    private Integer quantity;

    public ProductOrderModel() {}

    public ProductOrderModel(ProductModel product, Integer qty){
        this.product = product;
        this.quantity = qty;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jobj = new JSONObject();
        jobj.put("dishId", product.getId());
        jobj.put("quantity", this.quantity);
        return jobj;
    }

}
