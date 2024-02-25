package befaster.solutions.CHK;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CheckoutSolution {
    private Map<Character, PricingInfo> inventory;

    public CheckoutSolution() {
        this.inventory = new HashMap<>();
        inventory.put('A', new PricingInfo(50, Map.of(3, 130, 5, 200)));
        inventory.put('B', new PricingInfo(30, Map.of(2, 45)));
        inventory.put('C', new PricingInfo(20));
        inventory.put('D', new PricingInfo(15));
        inventory.put('E', new PricingInfo(40, Collections.emptyMap(), Map.of(2, new FreeProductDiscount(1, 'B'))));
        inventory.put('F', new PricingInfo(10, Collections.emptyMap(), Map.of(2, new FreeProductDiscount(1, 'F', 3))));
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
        
        // Build any bundles there might exist
        for (Map.Entry<Character, Integer> productQuantity : products.entrySet()) {
            Character sku = productQuantity.getKey();
            Integer quantity = productQuantity.getValue();
            
            // Get product pricing
            PricingInfo productPricing = inventory.get(sku);

            // Iterate each of the bundle offers and apply them, from the best to the worst
            for (Entry<Integer, FreeProductDiscount> offer : productPricing.getSpecialBundleOffers().entrySet()) {
                // Check if we have the minimum quantity to apply this discount
                if (products.getOrDefault(offer.getValue().getDiscountedSku(), 1) >= offer.getValue().getMinimumQuantity()) {
                    // Calculates the total of units that can be discounted through this offer
                    int totalDiscountedUnits = (quantity / offer.getKey()) * offer.getValue().getDiscountedUnits();
                    // Removes those units from the quantity
                    products.computeIfPresent(offer.getValue().getDiscountedSku(), (k, v) -> v - totalDiscountedUnits);
                    // save the rest to be evaluated next
                    quantity = quantity % offer.getKey(); 
                }
            }
        }

        // Sum products based on their quantity
        int total = 0;
        for (Map.Entry<Character, Integer> productQuantity : products.entrySet()) {
            Character sku = productQuantity.getKey();
            Integer quantity = productQuantity.getValue();

            // Get product pricing
            PricingInfo productPricing = inventory.get(sku);

            // Iterate each of the special offers and apply them, from the best to the worst
            for (Entry<Integer, Integer> offer : productPricing.getSpecialQuantityOffers().entrySet()) {
                // Try to apply the offer to most of the available quantity
                total += (quantity / offer.getKey()) * offer.getValue();
                // save the rest to be evaluated next
                quantity = quantity % offer.getKey();
            }
            
            // The rest that doesn't have a special offer should be priced normally
            total += quantity * productPricing.getRegularPrice();
        }

        return total;
    }

    class PricingInfo {
        private final int regularPrice;
        private final Map<Integer, Integer> specialQuantityOffer;
        private final Map<Integer, FreeProductDiscount> specialBundleOffer;

        PricingInfo(int regularPrice) {
            this.regularPrice = regularPrice;
            this.specialQuantityOffer = Collections.emptyMap();
            this.specialBundleOffer = Collections.emptyMap();

        }

        PricingInfo(int regularPrice, Map<Integer, Integer> specialQuantityOffers) {
            this.regularPrice = regularPrice;
            this.specialQuantityOffer = new TreeMap<>(Collections.reverseOrder());
            this.specialQuantityOffer.putAll(specialQuantityOffers);
            this.specialBundleOffer = Collections.emptyMap();
        }
        
        PricingInfo(int regularPrice, Map<Integer, Integer> specialQuantityOffers, Map<Integer, FreeProductDiscount> specialBundleOffer) {
            this.regularPrice = regularPrice;
            this.specialQuantityOffer = new TreeMap<>(Collections.reverseOrder());
            this.specialQuantityOffer.putAll(specialQuantityOffers);
            this.specialBundleOffer = new TreeMap<>(Collections.reverseOrder());
            this.specialBundleOffer.putAll(specialBundleOffer);
        }

        public int getRegularPrice() {
            return regularPrice;
        }

        public Map<Integer, Integer> getSpecialQuantityOffers() {
            return specialQuantityOffer;
        }
        
        public Map<Integer, FreeProductDiscount> getSpecialBundleOffers() {
            return specialBundleOffer;
        }
    }
    
    class FreeProductDiscount {
        private char discountedSku;
        private int discountedUnits;
        private int minimumQuantity;
        
        FreeProductDiscount(int discountedUnits, char discountedSku) {
            this.discountedSku = discountedSku;
            this.discountedUnits = discountedUnits;
            this.minimumQuantity = 1;
        }
        
        FreeProductDiscount(int discountedUnits, char discountedSku, int minimumQuantity) {
            this.discountedSku = discountedSku;
            this.discountedUnits = discountedUnits;
            this.minimumQuantity = minimumQuantity;
        }

        public char getDiscountedSku() {
            return discountedSku;
        }

        public int getDiscountedUnits() {
            return discountedUnits;
        }
        
        public int getMinimumQuantity() {
            return minimumQuantity;
        }
    }
}



