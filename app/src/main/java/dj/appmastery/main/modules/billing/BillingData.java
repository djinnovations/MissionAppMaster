package dj.appmastery.main.modules.billing;

import dj.appmastery.main.modules.billing.Purchase;
import dj.appmastery.main.modules.billing.SkuDetails;

/**
 * Created by User on 18-11-2016.
 */
public class BillingData {

    private String expiryDate = "";
    private boolean isPurchased;
    private String purchaseType;
    private SkuDetails skuDetails;
    private Purchase purchase;

    public BillingData(boolean isPurchased, SkuDetails skuDetails) {
        this.isPurchased = isPurchased;
        this.skuDetails = skuDetails;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public SkuDetails getSkuDetails() {
        return skuDetails;
    }

    public void setSkuDetails(SkuDetails skuDetails) {
        this.skuDetails = skuDetails;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }
}
