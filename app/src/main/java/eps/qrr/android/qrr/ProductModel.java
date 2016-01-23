
package eps.qrr.android.qrr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductModel {

    private Integer id;

    private String name;

    private String description;

    private BigDecimal price;

    private byte[] image;

    private Integer restaurant;

    private Set<String> ingredients;

    public ProductModel() {
        this.ingredients = new HashSet<>();
    }

    public ProductModel(JSONObject json) throws JSONException {
        this();
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.price = new BigDecimal(json.getDouble("price"));
        this.image = json.getString("image").getBytes();
        this.restaurant = json.getInt("restaurant");
        JSONArray jarray = json.getJSONArray("ingredients");
        for(int i = 0; i < jarray.length(); i++) {
            this.ingredients.add(jarray.getJSONObject(i).getString("name"));
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public byte[] getImage() {
        return image;
    }

    public Integer getRestaurant() {
        return restaurant;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }
}
