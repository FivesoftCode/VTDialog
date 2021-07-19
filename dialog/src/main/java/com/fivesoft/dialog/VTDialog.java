package com.fivesoft.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.fivesoft.smartutil.Metrics;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.ListPopupWindow.WRAP_CONTENT;

public class VTDialog {

    private final Activity activity;
    private int dialogMode = 0;

    //Buttons
    private Button leftButton = null;
    private Button centralButton = null;
    private Button rightButton = null;

    private String title = "";
    private String message = "";
    private View contentView;

    private boolean dismissOnButtonClick = true;
    private Drawable dialogIcon;
    private final Dialog dialog;
    private boolean enabledLinkify = false;
    private TextView dialTitle;
    private TextView dialMessage;
    private boolean messageSingleLine = false;
    private boolean titleSingleLine = false;
    private boolean buttonIconAutoColor = true;

    private int paddingLeft = 8;
    private int paddingRight = 8;
    private int paddingTop = 8;
    private int paddingBottom = 8;

    private int gravity = Gravity.CENTER;


    private int buttonsStyle;

    private DialogCustomization dialogCustomization = null;

    public static final int DIALOG_MODE_NORMAL = 0;
    public static final int DIALOG_MODE_FULLSCREEN = 1;


    public static final int BUTTONS_STYLE_HORIZONTAL = 0;
    public static final int BUTTONS_STYLE_VERTICAL = 1;

    private VTDialog(Activity activity){
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    /**
     * Creates new VTDialog instance.
     * @param activity the running activity.
     * @return new VTDialog instance.
     */

    public static VTDialog from(Activity activity){
        return new VTDialog(activity);
    }

    /**
     * Sets the left button. If you don't call this method
     * the left button will not be displayed.
     * @param text The text displayed on the button.
     * @param clickListener The click listener.
     */

    public VTDialog setLeftButton(String text, OnClickListener clickListener) {
        leftButton = new Button(text, clickListener);
        return this;
    }

    /**
     * Sets the central button. If you don't call this method
     * the central button will not be displayed.
     * @param text The text displayed on the button.
     * @param clickListener The click listener.
     */

    public VTDialog setCentralButton(String text, OnClickListener clickListener) {
        centralButton = new Button(text, clickListener);
        return this;
    }

    /**
     * Sets the right button. If you don't call this method
     * the right button will not be displayed.
     * @param text The text displayed on the button.
     * @param clickListener The click listener.
     */

    public VTDialog setRightButton(String text, OnClickListener clickListener) {
        rightButton = new Button(text, clickListener);
        return this;
    }

    /**
     * Sets the left button. If you don't call this method
     * the left button will not be displayed.
     * @param text The text displayed on the button.
     * @param clickListener The click listener.
     * @param iconRes Button icon resource id.
     */

    public VTDialog setLeftButton(String text, int iconRes, OnClickListener clickListener) {
        leftButton = new Button(text, iconRes, clickListener);
        return this;
    }

    /**
     * Sets the central button. If you don't call this method
     * the central button will not be displayed.
     * @param text The text displayed on the button.
     * @param clickListener The click listener.
     * @param iconRes Button icon resource id.
     */

    public VTDialog setCentralButton(String text, int iconRes, OnClickListener clickListener) {
        centralButton = new Button(text, iconRes, clickListener);
        return this;
    }

    /**
     * Sets the right button. If you don't call this method
     * the right button will not be displayed.
     * @param text The text displayed on the button.
     * @param clickListener The click listener.
     * @param iconRes Button icon resource id.
     */

    public VTDialog setRightButton(String text, int iconRes, OnClickListener clickListener) {
        rightButton = new Button(text, iconRes, clickListener);
        return this;
    }

    /**
     * Sets the dialog title
     * @param title Dialog title displayed at the top.
     * @return current VTDialog instance
     */

    public VTDialog setTitle(String title) {
        this.title = title;
        if(dialTitle != null){
            dialTitle.setText(title);
        }
        return this;
    }

    /**
     * Sets the dialog message
     * @param message Dialog message displayed below the title
     * @return current VTDialog instance
     */

    public VTDialog setMessage(String message) {
        this.message = message;
        if(dialMessage != null){
            dialMessage.setText(message);
        }
        return this;
    }

    /**
     * Sets the dialog icon displayed next to the title.
     * @param dialogIcon Your icon Drawable
     * @return current VTDialog instance
     */

    public VTDialog setDialogIcon(Drawable dialogIcon) {
        this.dialogIcon = dialogIcon;
        return this;
    }

    /**
     * Sets the dialog icon displayed next to the title.
     * @param resId Your icon resource id
     * @return current VTDialog instance
     */

    public VTDialog setDialogIcon(int resId){
        try { dialogIcon = ContextCompat.getDrawable(activity, resId); } catch (Exception e){ e.printStackTrace(); }
        return this;
    }

    /**
     * When you set to true user will can dismiss the dialog
     * by clicking the back button or clicking outside the dialog layout.
     */

    public VTDialog setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * Sets the view displayed below title and message.
     * @param contentView your view.
     */

    public VTDialog setContentView(View contentView) {
        this.contentView = contentView;
        return this;
    }

    /**
     * Sets the view displayed below title and message.
     * @param resId your view resource id.
     */

    public VTDialog setContentView(int resId){
        try { contentView = activity.getLayoutInflater().inflate(resId, null); } catch (Exception e){ e.printStackTrace(); }
        return this;
    }

    /**
     * When you set to true, the dialog will be automatically
     * dismissed when user clicks one of the buttons.
     */

    public VTDialog setDismissOnButtonClick(boolean dismissOnButtonClick) {
        this.dismissOnButtonClick = dismissOnButtonClick;
        return this;
    }

    /**
     * Makes the urls in the message clickable. User will simple can
     * open them in the browser.
     */

    public VTDialog setEnabledLinkify(boolean enabledLinkify) {
        this.enabledLinkify = enabledLinkify;
        return this;
    }

    /**
     * The dialog title will be displayed in a single line.
     */

    public VTDialog setTitleSingleLine(boolean titleSingleLine) {
        this.titleSingleLine = titleSingleLine;
        return this;
    }

    /**
     * The dialog message will be displayed in a single line.
     */

    public VTDialog setMessageSingleLine(boolean messageSingleLine) {
        this.messageSingleLine = messageSingleLine;
        return this;
    }

    /**
     * Setups the dialog mode.
     * There are 2 options available:
     * @see #DIALOG_MODE_NORMAL
     * @see #DIALOG_MODE_FULLSCREEN
     * @return current VTDialog instance.
     */

    public VTDialog setDialogMode(int dialogMode) {
        this.dialogMode = dialogMode;
        return this;
    }

    /**
     * Sets the dialog {@link DialogInterface.OnDismissListener}
     * @return current VTDialog instance
     */

    public VTDialog setOnDialogDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        dialog.setOnDismissListener(onDismissListener);
        return this;
    }

    /**
     * Sets the dialog {@link DialogInterface.OnCancelListener}
     * @return current VTDialog instance
     */

    public VTDialog setOnDialogCanceledListener(DialogInterface.OnCancelListener onCancelListener) {
        dialog.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * Sets the dialog {@link DialogInterface.OnShowListener}
     * @return current VTDialog instance
     */

    public VTDialog setOnDialogShowListener(DialogInterface.OnShowListener onShowListener) {
        dialog.setOnShowListener(onShowListener);
        return this;
    }

    /**
     * Sets the dialog padding in dp.
     * @param left padding
     * @param top padding
     * @param right padding
     * @param bottom padding
     * @return current VTDialog instance
     */

    public VTDialog setPadding(int left, int top, int right, int bottom){
        this.paddingLeft = left;
        this.paddingTop = top;
        this.paddingRight = right;
        this.paddingBottom = bottom;
        return this;
    }

    public VTDialog setButtonsStyle(int buttonsStyle){
        this.buttonsStyle = buttonsStyle;
        return this;
    }

    public VTDialog setButtonIconAutoColor(boolean buttonIconAutoColor) {
        this.buttonIconAutoColor = buttonIconAutoColor;
        return this;
    }

    public VTDialog customize(DialogCustomization dialogCustomization){
        this.dialogCustomization = dialogCustomization;
        return this;
    }

    /**
     * Sets gravity of the dialog.
     * @param gravity dialog gravity. Check {@link Gravity}
     * @return current VTDialog instance
     */

    public VTDialog setGravity(int gravity){
        this.gravity = gravity;
        return this;
    }

    /**
     * Shows the dialog.
     */

    public void show(){
        try {
            createDialog();
            dialog.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Closes the dialog.
     */

    public void dismiss(){
        dialog.dismiss();
    }


    public TextView getLeftButton(){
        return leftButton.buttonView;
    }

    public TextView getCenterButton(){
        return centralButton.buttonView;
    }

    public TextView getRightButton(){
        return rightButton.buttonView;
    }

    public Window getWindow(){
        return dialog.getWindow();
    }

    public Activity getActivity(){
        return activity;
    }

    //Private methods

    private void createDialog(){

        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;

        if(dialogMode == DIALOG_MODE_NORMAL) {
            dialog.setContentView(R.layout.d_vt_dialog_alert);
            dialog.getWindow().setLayout(MATCH_PARENT, WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else if(dialogMode == DIALOG_MODE_FULLSCREEN){
            dialog.setContentView(R.layout.d_vt_dialog_fullscreen);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                dialog.getWindow().setStatusBarColor(Color.WHITE);

            dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialTitle = dialog.findViewById(R.id.title);
        dialMessage = dialog.findViewById(R.id.message);

        ImageView icon = dialog.findViewById(R.id.icon);

        CardView background = dialog.findViewById(R.id.background);

        LinearLayout content = dialog.findViewById(R.id.content);

        LinearLayout buttonsBar = dialog.findViewById(R.id.buttonsBar);

        View view;

        if(buttonsStyle == BUTTONS_STYLE_HORIZONTAL){
            view = activity.getLayoutInflater().inflate(R.layout.d_buttons_horizontal, buttonsBar, false);
        } else {
            view = activity.getLayoutInflater().inflate(R.layout.d_buttons_vertical, buttonsBar, false);
        }

        buttonsBar.removeAllViews();
        buttonsBar.addView(view);

        TextView leftButton = view.findViewById(R.id.leftButton);
        TextView centerButton = view.findViewById(R.id.centerButton);
        TextView rightButton = view.findViewById(R.id.rightButton);

        setupTextView(this.title, dialTitle);
        setupTextView(this.message, dialMessage);

        if (enabledLinkify) {
            Linkify.addLinks(dialMessage, Linkify.WEB_URLS);
            dialMessage.setLinksClickable(true);
        }

        dialTitle.setSingleLine(titleSingleLine);
        dialMessage.setSingleLine(messageSingleLine);

        setupButton(this.leftButton, leftButton);
        setupButton(this.centralButton, centerButton);
        setupButton(this.rightButton, rightButton);

        if (contentView != null) {
            content.removeAllViewsInLayout();
            content.addView(contentView);
        }

        setupIcon(dialogIcon, icon);

        if(dialogCustomization != null){

            try {

                dialogCustomization.customizeTitleTextView(dialTitle);
                dialogCustomization.customizeMessageTextView(dialMessage);

                dialogCustomization.customizeButtons(leftButton, 0);
                dialogCustomization.customizeButtons(rightButton, 2);
                dialogCustomization.customizeButtons(centerButton, 1);

                dialogCustomization.customizeDialogIcon(icon);

                dialogCustomization.customizeDialogBackground(background);

                dialogCustomization.customizeWindow(dialog.getWindow());

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        background.setContentPadding(
                Metrics.dpToPx(paddingLeft, activity),
                Metrics.dpToPx(paddingTop, activity),
                Metrics.dpToPx(paddingRight, activity),
                Metrics.dpToPx(paddingBottom, activity));

    }

    private void setupTextView(String text, TextView textView){
        if(text == null || text.equals("")){
            textView.setVisibility(GONE);
        }else{
            textView.setText(text);
        }
    }

    private void setupButton(Button buttonData, TextView textView){
        if(buttonData == null || buttonData.text == null || buttonData.text.equals("")){
            textView.setVisibility(GONE);
        } else {
            textView.setVisibility(VISIBLE);
            textView.setText(buttonData.text);
            textView.setOnClickListener(view -> {
                if(buttonData.onClickListener != null)
                    buttonData.onClickListener.onClick(textView);
                if(dismissOnButtonClick) {
                    dialog.dismiss();
                }
            });
            if(buttonData.iconRes != -1){
                textView.setCompoundDrawablesWithIntrinsicBounds(buttonData.iconRes, 0, 0, 0);
                if(buttonIconAutoColor){
                    textView.getCompoundDrawables()[0].setColorFilter(textView.getTextColors().getDefaultColor(), PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }

    private void setupIcon(Drawable drawable, ImageView icon){
        if(drawable == null){
            icon.setVisibility(GONE);
        }else{
            icon.setImageDrawable(drawable);
        }
    }

    private static class Button {

        public String text;
        public TextView buttonView;
        public int iconRes = -1;
        public OnClickListener onClickListener;

        private Button(String text, OnClickListener onClickListener){
            this.text = text;
            this.onClickListener = onClickListener;
        }

        private Button(String text, int iconRes, OnClickListener onClickListener){
            this.text = text;
            this.iconRes = iconRes;
            this.onClickListener = onClickListener;
        }

    }

    public static class DialogCustomization {

        public void customizeTitleTextView(TextView title){

        }

        public void customizeMessageTextView(TextView message){

        }

        public void customizeButtons(TextView button, int buttonId){

        }

        public void customizeDialogBackground(CardView background){

        }

        public void customizeDialogIcon(ImageView dialogIcon){

        }

        public void customizeWindow(Window dialogWindow){

        }

    }

}
