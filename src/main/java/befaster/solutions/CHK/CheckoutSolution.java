package befaster.solutions.CHK;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CheckoutSolution {
    private Map<Character, PricingInfo> inventory;
    private List<CustomBundle> customBundles;

    public CheckoutSolution() {
        this.inventory = new HashMap<>();
        inventory.put('A', new PricingInfo(50, Map.of(3, 130, 5, 200)));
        inventory.put('B', new PricingInfo(30, Map.of(2, 45)));
        inventory.put('C', new PricingInfo(20));
        inventory.put('D', new PricingInfo(15));
        inventory.put('E', new PricingInfo(40, Collections.emptyMap(), Map.of(2, new FreeProductDiscount(1, 'B'))));
        inventory.put('F', new PricingInfo(10, Collections.emptyMap(), Map.of(3, new FreeProductDiscount(1, 'F'))));
        inventory.put('G', new PricingInfo(20));
        inventory.put('H', new PricingInfo(10, Map.of(5, 45, 10, 80)));
        inventory.put('I', new PricingInfo(35));
        inventory.put('J', new PricingInfo(60));
        inventory.put('K', new PricingInfo(70, Map.of(2, 120)));
        inventory.put('L', new PricingInfo(90));
        inventory.put('M', new PricingInfo(15));
        inventory.put('N', new PricingInfo(40, Collections.emptyMap(), Map.of(3, new FreeProductDiscount(1, 'M'))));
        inventory.put('O', new PricingInfo(10));
        inventory.put('P', new PricingInfo(50, Map.of(5, 200)));
        inventory.put('Q', new PricingInfo(30, Map.of(3, 80)));
        inventory.put('R', new PricingInfo(50, Collections.emptyMap(), Map.of(3, new FreeProductDiscount(1, 'Q'))));
        inventory.put('S', new PricingInfo(20));
        inventory.put('T', new PricingInfo(20));
        inventory.put('U', new PricingInfo(40, Collections.emptyMap(), Map.of(4, new FreeProductDiscount(1, 'U'))));
        inventory.put('V', new PricingInfo(50, Map.of(2, 90, 3, 130)));
        inventory.put('W', new PricingInfo(20));
        inventory.put('X', new PricingInfo(17));
        inventory.put('Y', new PricingInfo(20));
        inventory.put('Z', new PricingInfo(21));
        
        this.customBundles = Collections.singletonList(new CustomBundle(Set.of('S', 'T', 'X', 'Y', 'Z'), 3, 45));
    }

    public Integer checkout(String skus) {
        if (!validateInput(skus)) {
            return -1;
        }

        // Build the sum of all the products
        Map<Character, Integer> products = createSkusMap(skus);
        
        // Initializes the total price
        int total = 0;
        
        // Build any regular bundles there might exist
        for (Map.Entry<Character, Integer> productQuantity : products.entrySet()) {
            Character originalSku = productQuantity.getKey();
            Integer originalSkuQuantity = productQuantity.getValue();
            
            // Get product pricing
            PricingInfo originalProductPricing = inventory.get(originalSku);

            // Iterate each of the bundle offers and apply them, from the best to the worst
            for (Entry<Integer, FreeProductDiscount> entry : originalProductPricing.getSpecialBundleOffers().entrySet()) {
                // Quick rename to make things easier
                int triggeringQuantity = entry.getKey();
                FreeProductDiscount discount = entry.getValue();
                int applicableAmmount = originalSkuQuantity / triggeringQuantity;
                // Calculates the total of units that can be discounted through this offer
                int totalDiscountedUnits = applicableAmmount * discount.getDiscountedUnits();
                // Removes those units from the quantity
                products.computeIfPresent(discount.getDiscountedSku(), (k, v) -> v - totalDiscountedUnits);
                // save the rest to be evaluated next
                originalSkuQuantity = originalSkuQuantity % triggeringQuantity;
            }
        }
                
        // Handle custom bundles
        for (CustomBundle customBundle : customBundles) {
            List<Integer> prices = new ArrayList<>();
            for(Character sku : customBundle.getSkus()) {
                Integer quantity = products.remove(sku);
                if (quantity == null) {
                    continue;
                }
                for (int i = 0; i < quantity; i++) {
                    prices.add(inventory.get(sku).getRegularPrice());
                }
            }
            if (prices.isEmpty()) {
                continue;
            }
            Collections.sort(prices, Collections.reverseOrder());
            List<List<Integer>> particionedList = ListUtils.partition(prices, customBundle.getMinQuantity());
            for (List<Integer> priceParticion : particionedList) {
                int partitionTotal = 0;
                for (Integer price : priceParticion) {
                    partitionTotal += price;
                }
                
                if (priceParticion.size() < customBundle.getMinQuantity() || partitionTotal < customBundle.getPrice()) {
                    total += partitionTotal;
                } else {
                    total += customBundle.getPrice();
                }
            }
        }

        // Sum products based on their quantity
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
    
    private boolean validateInput(String skus) {
        // Validate if the skus aren't null
        if (skus == null) {
            return false;
        }

        // Validate if there aren't any illegal products
        for (char sku : skus.toCharArray()) {
            if (!inventory.containsKey(sku)) {
                return false;
            }
        }
        
        return true;
    }
    
    private Map<Character, Integer> createSkusMap(String skus) {
        Map<Character, Integer> products = new HashMap<>();
        for (char sku : skus.toCharArray()) {
            products.compute(sku, (k, v) -> v == null ? 1 : v + 1);
        }
        return products;
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
        
        FreeProductDiscount(int discountedUnits, char discountedSku) {
            this.discountedSku = discountedSku;
            this.discountedUnits = discountedUnits;
        }
        
        FreeProductDiscount(int discountedUnits, char discountedSku, int minimumQuantity) {
            this.discountedSku = discountedSku;
            this.discountedUnits = discountedUnits;
        }

        public char getDiscountedSku() {
            return discountedSku;
        }

        public int getDiscountedUnits() {
            return discountedUnits;
        }
    }

    class CustomBundle {
        private Set<Character> skus;
        private int minQuantity;
        private int price;
        
        CustomBundle(Set<Character> skus, int minQuantity, int price) {
            this.skus = skus;
            this.minQuantity = minQuantity;
            this.price = price;
        }

        public Set<Character> getSkus() {
            return skus;
        }

        public int getMinQuantity() {
            return minQuantity;
        }

        public int getPrice() {
            return price;
        }
    }
}



