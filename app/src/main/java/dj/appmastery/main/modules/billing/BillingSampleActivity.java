package dj.appmastery.main.modules.billing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dj.appmastery.main.R;
import dj.appmastery.main.activities.BaseActivity;
import dj.appmastery.main.activities.MyApplication;
import dj.appmastery.main.modules.adapters.BillingThumbnailAdapter;
import dj.appmastery.main.utils.DateTimeUtils;

/**
 * Created by User on 18-11-2016.
 */
public class BillingSampleActivity extends BaseActivity {


    private static final String SKU_SUBSCRIBE = "monthly";

    private static final String TAG = "BillingSampleActivity";
    IabHelper mHelper;

    private IabResult mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_sample);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        tvNoItems.setVisibility(View.GONE);
        listener = new BillingThumbnailAdapter.MenuSelectionListener() {
            @Override
            public void onMenuSelected(BillingData data) {
                purchaseItem(data);
            }
        };
        setUpRecycleView();
        setUpBilling();
    }

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tvNoItems)
    TextView tvNoItems;

    //List<BillingData> billingDataList = new ArrayList<>();

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;

    private void setUpPurchaseFinishListener() {
        mPurchaseFinishedListener
                = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {

                Log.d(TAG, "Purchase response: " + purchase);
                if (result.isFailure()) {
                    Log.d(TAG, "Error purchasing: " + result);
                    return;
                }
                int index = 0;
                for (BillingData data : adapter.getDataList()) {
                    if (data.getSkuDetails().getSku().equals(purchase.getSku())) {
                        break;
                    }
                    index++;
                }
                BillingData data = adapter.getDataList().get(index);
                data.setPurchased(true);
                data.setExpiryDate(DateTimeUtils.getFormattedTimestamp("dd-MM-yyyy", purchase.getPurchaseTime()));// TODO: 18-11-2016  to calc expiry date
                data.setPurchase(purchase);
                //billingDataList.set(index, data);
                adapter.setExisting(index, data);
            }
        };
    }

    String userid = "bohr1234";
    public final int REQUEST_ID_FOR_PURCHASE = 10001;

    private void purchaseItem(BillingData data) {
        if (data == null)
            return;
        mHelper.launchPurchaseFlow(this, data.getSkuDetails().getSku(), IabHelper.ITEM_TYPE_SUBS
                , REQUEST_ID_FOR_PURCHASE, mPurchaseFinishedListener, userid);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mHelper == null) return;
        mHelper.handleActivityResult(requestCode, resultCode, data);
    }

    private void setUpBilling() {
        mHelper = new IabHelper(this, MyApplication.getInstance().LICENSE_KEY_BILLING);
        mHelper.enableDebugLogging(true);
        skuList.add(SKU_PROD1);
        skuList.add(SKU_PROD2);
        //mHelper.enableRemoteLog(true);
        Log.d(TAG, "StartSetup()");

        setQueryFinishListener();
        setUpPurchaseFinishListener();

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                Log.d(TAG, "StartSetup() finish");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    progressBar.setVisibility(View.GONE);
                    tvNoItems.setVisibility(View.VISIBLE);
                    rvThumbnail.setVisibility(View.GONE);
                    mHelper.logError("Problem setting up in-app billing: " + result);
                    //onSubscriptionOperationComplete(result);
                    return;
                }

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                /*mHelper.logInfo("Setup successful. Querying inventory.");
                ArrayList<String> skus = new ArrayList<String>();
                skus.add(SKU_SUBSCRIBE);
                mHelper.queryInventoryAsync(true, skus, mGotInventoryListener);*/
                queryProductsForSale();
            }
        });
    }


    List<String> skuList = new ArrayList<>();
    IabHelper.QueryInventoryFinishedListener mQueryFinishedListener;

    private void setQueryFinishListener() {
        mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                progressBar.setVisibility(View.GONE);
                if (result.isFailure()) {
                    // handle error
                    tvNoItems.setVisibility(View.VISIBLE);
                    rvThumbnail.setVisibility(View.GONE);
                    Toast.makeText(MyApplication.getInstance(), "Unable to get products", Toast.LENGTH_LONG).show();
                    return;
                }

                rvThumbnail.setVisibility(View.VISIBLE);
                tvNoItems.setVisibility(View.GONE);
                List<String> ownedProds = inventory.getAllOwnedSkus();
                Log.d(TAG, "ownedProds: " + ownedProds);
                List<Purchase> purchasedProds = inventory.getAllPurchases();
                Log.d(TAG, "purchasedProds: " + purchasedProds);
                /*Set<String> purchasedItems = new HashSet<>();
                for (Purchase purchase: purchasedProds){
                    purchasedItems.add(purchase.getSku());
                }*/
                List<BillingData> data = new ArrayList<>();
                for (String sku: skuList){
                    if (inventory.hasDetails(sku)){
                        SkuDetails details = inventory.getSkuDetails(sku);
                        BillingData billingData = new BillingData(inventory.hasPurchase(sku), details);
                        if (inventory.hasPurchase(sku)){
                            billingData.setExpiryDate(DateTimeUtils.getFormattedTimestamp("dd-MM-yyyy",
                                    inventory.getPurchase(sku).getPurchaseTime()));
                        }
                        data.add(billingData);
                    }
                }
                //// TODO: 18-11-2016  update initial UI and adapter data fill up
                adapter.changeData(data);
            }
        };
    }

    private void consumeProduct(Purchase purchase) {
        String sku = "";//replace with original sku of the prod to consume
        mHelper.consumeAsync(purchase, //change the purchase obj
                mConsumeFinishedListener);
    }

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;

    private void setUpConsumeListener() {
        mConsumeFinishedListener =
                new IabHelper.OnConsumeFinishedListener() {
                    public void onConsumeFinished(Purchase purchase, IabResult result) {
                        if (result.isSuccess()) {
                            // provision the in-app purchase to the user
                            // (for example, credit 50 gold coins to player's character)
                            //// TODO: 18-11-2016  allow users to purchase that ite,
                        } else {
                            // handle error
                        }
                    }
                };
    }

    public static final String SKU_PROD1 = "test_01.appmastery";
    public static final String SKU_PROD2 = "test_02.appmastery";

    private void queryProductsForSale() {
        List<String> additionalSkuList = new ArrayList<>();
        additionalSkuList.add(SKU_PROD1);
        additionalSkuList.add(SKU_PROD2);
        mHelper.queryInventoryAsync(true, additionalSkuList,
                mQueryFinishedListener);
    }


    private void evaluateBillingResults(IabResult result) {
        /*mResult = result;
        if (mResult.isSuccess()) {
            if (mIsPurchaseResult) { // purchase success
                AppCommon.isSubscribed = true;
                new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.msg_subscription_success)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                enterMainPage();
                            }
                        })
                        .create().show();
            } else {
                if (AppCommon.isSubscribed) { // subscription is confirmed after view loaded
                    enterMainPage();
                } else {
                    tvStatusMsg.setText(R.string.msg_subscription_require);
                    llButtons.setVisibility(View.VISIBLE);
                    btnSubscribe.requestFocus();
                }
            }
        } else {
            imgError.setVisibility(View.VISIBLE);
            llButtons.setVisibility(View.VISIBLE);
            btnSubscribe.requestFocus();
            tvStatusMsg.setText(mResult.getMessage());
        }*/
    }


    @Bind(R.id.rvThumbnail)
    RecyclerView rvThumbnail;
    BillingThumbnailAdapter adapter;

    BillingThumbnailAdapter.MenuSelectionListener listener;

    private void setUpRecycleView() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        rvThumbnail.setHasFixedSize(false);
        rvThumbnail.setLayoutManager(mLayoutManager);
        rvThumbnail.setItemAnimator(new DefaultItemAnimator());
        adapter = new BillingThumbnailAdapter(listener);
        rvThumbnail.setAdapter(adapter);
    }
}
