package befaster.solutions.CHK;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/*
    CHK_R1
    ROUND 1 - Our supermarket
    The purpose of this challenge is to implement a supermarket checkout that calculates the total price of a number of items.
    
    In a normal supermarket, things are identified using Stock Keeping Units, or SKUs. 
    In our store, we'll use individual letters of the alphabet (A, B, C, and so on). 
    Our goods are priced individually. In addition, some items are multi-priced: buy n of them, and they'll cost you y pounds. 
    For example, item A might cost 50 pounds individually, but this week we have a special offer: 
     buy three As and they'll cost you 130.
    
    Our price table and offers: 
    +------+-------+----------------+
    | Item | Price | Special offers |
    +------+-------+----------------+
    | A    | 50    | 3A for 130     |
    | B    | 30    | 2B for 45      |
    | C    | 20    |                |
    | D    | 15    |                |
    +------+-------+----------------+
    
    
    Notes: 
     - For any illegal input return -1
    
    In order to complete the round you need to implement the following method:
         checkout(String) -> Integer
    
    Where:
     - param[0] = a String containing the SKUs of all the products in the basket
     - @return = an Integer representing the total checkout value of the items 
 */

public class CheckoutSolution {
    private Map<Character, PricingInfo> inventory;
    
    public CheckoutSolution() {
        this.inventory = new HashMap<>();
        inventory.put('A', new PricingInfo(50, Map.entry(3, 130)));
        inventory.put('B', new PricingInfo(30, Map.entry(2, 45)));
        inventory.put('C', new PricingInfo(20));
        inventory.put('D', new PricingInfo(15));
    }
    
    public Integer checkout(String skus) {
        // Validate if the skus aren't null
        if (skus == null) {
            return -1;
        }
        
        // Validate if there aren't any illegal products
        for (char sku : skus.toCharArray()) {
            if (!inventory.containsKey(sku)) {
                return -1;
            }
        }
        
        // Build the sum of all the products
        Map<Character, Integer> products = new HashMap<>();
        for (char sku : skus.toCharArray()) {
            products.compute(sku, (k, v) -> v == null ? 1 : v + 1);
        }
        
        // Sum products based on their quantity
        int total = 0;
        for (Map.Entry<Character, Integer> productQuantity : products.entrySet()) {
            Character sku = productQuantity.getKey();
            Integer quantity = productQuantity.getValue();
            
            // Get product pricing
            PricingInfo productPricing = inventory.get(sku);
            
            // If it doesn't have any special offers, then use the regular pricing
            if (productPricing.getSpecialQuantityOffers() == null) {
                total += quantity * productPricing.getRegularPrice();
                continue;
            }
            
            // If it has special pricing, then handle it accordingly
            // First calculate the special price for the available "bundles"
            int specialPriceItems = (quantity / productPricing.getSpecialQuantityOffers().getKey()) * productPricing.getSpecialQuantityOffers().getValue();
            // Then add the extras as regular priced
            int regularPriceItems = (quantity % productPricing.getSpecialQuantityOffers().getKey()) * productPricing.getRegularPrice();
            total += specialPriceItems + regularPriceItems;
        }
        
        return total;
    }
    
    class PricingInfo {
        private final int regularPrice;
        private final Entry<Integer, Integer> specialQuantityOffer;
        
        PricingInfo(int regularPrice) {
            this.regularPrice = regularPrice;
            this.specialQuantityOffer = null;
        }
        
        PricingInfo(int regularPrice, Entry<Integer, Integer> specialQuantityOffers) {
            this.regularPrice = regularPrice;
            this.specialQuantityOffer = specialQuantityOffers;
        }

        public int getRegularPrice() {
            return regularPrice;
        }

        public Entry<Integer, Integer> getSpecialQuantityOffers() {
            return specialQuantityOffer;
        }

        @Override
        public String toString() {
            return "PricingInfo [regularPrice=" + regularPrice + ", specialQuantityOffers=" + specialQuantityOffer + "]";
        }
    }
}




