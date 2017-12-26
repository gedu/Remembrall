package com.gemapps.remembrall.ui.widget;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;

import com.gemapps.remembrall.R;
import com.gemapps.remembrall.data.RemembrallContract;
import com.gemapps.remembrall.ui.model.Client;
import com.gemapps.remembrall.ui.model.Product;
import com.gemapps.remembrall.ui.widget.searchtext.SearchPopupHelper;
import com.gemapps.remembrall.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by edu on 1/20/17.
 * Main class to handle all the inputs from the creation form
 */
public class FormUIHandler {
  // TODO: 2/2/17 Would be good to create a Builder for this
  private static final String TAG = "FormUIHandler";

  @BindView(R.id.nested_scroll)
  LockNestedScrollView mNestedScroll;
  @BindView(R.id.form_first_name_edit)
  TextInputEditText mFirstNameEdit;
  @BindView(R.id.form_last_name_edit)
  TextInputEditText mLastNameEdit;
  @BindView(R.id.form_id_card_edit)
  TextInputEditText mIdCardEdit;
  @BindView(R.id.form_address_edit)
  TextInputEditText mAddressEdit;
  @BindView(R.id.form_email_edit)
  TextInputEditText mEmailEdit;
  @BindView(R.id.form_home_phone_edit)
  TextInputEditText mHomePhoneEdit;
  @BindView(R.id.form_mobile_phone_edit)
  TextInputEditText mMobilePhoneEdit;

  @BindView(R.id.form_equip_label_edit)
  TextInputEditText mEquipLabelEdit;
  @BindView(R.id.form_equip_num_edit)
  TextInputEditText mEquipNumEdit;
  @BindView(R.id.form_tester_num_edit)
  TextInputEditText mTesterNumEdit;
  @BindView(R.id.form_terminal_num_edit)
  TextInputEditText mTerminalNumEdit;
  @BindView(R.id.form_description_edit)
  TextInputEditText mDescriptionEdit;

  @Nullable @BindView(R.id.form_start_day_text)
  TextView mStartDayText;
  @Nullable @BindView(R.id.form_end_day_text)
  TextView mEndDayText;
  @Nullable @BindView(R.id.form_price_edit)
  TextInputEditText mPriceNumEdit;

  @Nullable @BindView(R.id.ink_view)
  InkWritingWrapper mInkView;

  private SearchPopupHelper mClientSearchPopup;
  private SearchPopupHelper mProductSearchPopup;

  public FormUIHandler(View rootView, boolean withSearchPopups) {
    ButterKnife.bind(this, rootView);
    if (withSearchPopups) setupSearchPopups();
  }

  private void setupSearchPopups() {
    mClientSearchPopup = new SearchPopupHelper(mFirstNameEdit, Client.class,
        RemembrallContract.ClientEntry.COLUMN_FIRST_NAME, mClientSearchListener);
    mProductSearchPopup = new SearchPopupHelper(mEquipLabelEdit, Product.class,
        RemembrallContract.ProductEntry.COLUMN_LABEL, mProductSearchListener);
  }


  public void setLockScrollListener() {
    if (inkViewExist()) mInkView.setLockNestedScrollView(mNestedScroll);
  }

  public void clearSignView() {
    if (inkViewExist()) mInkView.clear();
  }

  private boolean inkViewExist() {
    return mInkView != null;
  }

  public void setStartDayText(String dateText) {
    mStartDayText.setText(dateText);
  }

  public void setEndDayText(String dateText) {
    mEndDayText.setText(dateText);
  }

  public void fillClientUI(Client client) {
    fillClientUI(client, true);
  }

  public void fillClientUI(Client client, boolean fillName) {

    mFirstNameEdit.setText(client.getFirstName());
    mLastNameEdit.setText(client.getLastName());
    mIdCardEdit.setText(client.getIdCard());
    mAddressEdit.setText(client.getAddress());
    mEmailEdit.setText(client.getEmail());
    mHomePhoneEdit.setText(client.getHomePhone());
    mMobilePhoneEdit.setText(client.getMobilePhone());
  }

  public void fillProductUI(Product product) {
    fillProductUI(product, true);
  }

  public void fillProductUI(Product product, boolean fillLabel) {

    if (fillLabel) mEquipLabelEdit.setText(product.getEquipLabel());
    mEquipNumEdit.setText(product.getEquipNum());
    mTesterNumEdit.setText(product.getTesterNum());
    mTerminalNumEdit.setText(product.getTerminalNum());
    mDescriptionEdit.setText(product.getDescription());
  }

  public void updateClient(Realm realm, final Client client) {

    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        client.setFirstName(mFirstNameEdit.getText().toString());
        client.setLastName(mLastNameEdit.getText().toString());
        client.setIdCard(mIdCardEdit.getText().toString());
        client.setAddress(mAddressEdit.getText().toString());
        client.setEmail(mEmailEdit.getText().toString());
        client.setHomePhone(mHomePhoneEdit.getText().toString());
        client.setMobilePhone(mMobilePhoneEdit.getText().toString());
      }
    });
  }

  public Client buildClient() {
    String firstName = mFirstNameEdit.getText().toString();
    String lastName = mLastNameEdit.getText().toString();
    String idCard = mIdCardEdit.getText().toString();
    String address = mAddressEdit.getText().toString();
    String email = mEmailEdit.getText().toString();
    String homePhone = mHomePhoneEdit.getText().toString();
    String mobilePhone = mMobilePhoneEdit.getText().toString();
    byte[] signImage = ImageUtil.convertBitmapToByte(mInkView.getBitmap());

    return new Client(firstName, lastName, idCard, address, email,
        homePhone, mobilePhone, signImage);
  }

  public Product buildProduct() {
    String equipLabel = mEquipLabelEdit.getText().toString();
    String equipNum = mEquipNumEdit.getText().toString();
    String testerNum = mTesterNumEdit.getText().toString();
    String terminalNum = mTerminalNumEdit.getText().toString();
    String description = mDescriptionEdit.getText().toString();

    return new Product(equipLabel, equipNum, testerNum, terminalNum, description);
  }

  public String getPriceFromView() {
    return mPriceNumEdit.getText().toString();
  }

  public void onDestroy() {
    mClientSearchPopup.onDestroy();
    mProductSearchPopup.onDestroy();
  }

  private SearchPopupHelper.SearchPopupListener mClientSearchListener = new SearchPopupHelper.SearchPopupListener() {
    @Override
    public void onItemClick(RealmObject selected) {

      fillClientUI((Client) selected, false);
    }
  };

  private SearchPopupHelper.SearchPopupListener mProductSearchListener = new SearchPopupHelper.SearchPopupListener() {
    @Override
    public void onItemClick(RealmObject selected) {
      fillProductUI((Product) selected, false);
    }
  };
}
