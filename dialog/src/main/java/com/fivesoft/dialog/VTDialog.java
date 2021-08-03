package com.fivesoft.dialog;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import com.fivesoft.smartutil.Metrics;
import com.fivesoft.smartutil.Screen;
import com.fivesoft.smartutil.ViewUtil;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class VTDialog {

    private final Activity activity;
    private int dialogMode = 0;

    //Buttons
    private Button leftButton = null;
    private Button centralButton = null;
    private Button rightButton = null;

    private TextView leftButtonView;
    private TextView centerButtonView;
    private TextView rightButtonView;

    private String title = "";
    private String message = "";
    private View contentView;

    private boolean dismissOnButtonClick = true;
    private boolean cancelable = true;
    private Drawable dialogIcon;
    private final Dialog dialog;
    private boolean enabledLinkify = false;
    private TextView dialTitle;
    private TextView dialMessage;
    private LinearLayout root;
    private boolean messageSingleLine = false;
    private boolean titleSingleLine = false;
    private boolean buttonIconAutoColor = true;

    private int paddingLeft = 8;
    private int paddingRight = 8;
    private int paddingTop = 8;
    private int paddingBottom = 8;

    private int gravity = Gravity.CENTER;


    private int buttonsStyle;

    private DialogCustomization dialogCustomization = new DialogCustomization();

    public static final int DIALOG_MODE_NORMAL = 0;
    public static final int DIALOG_MODE_FULLSCREEN = 1;

    public static final int BUTTON_ID_LEFT = 0;
    public static final int BUTTON_ID_CENTRAL = 1;
    public static final int BUTTON_ID_RIGHT = 2;


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
        return setLeftButton(text, 0, clickListener);
    }

    /**
     * Sets the central button. If you don't call this method
     * the central button will not be displayed.
     * @param text The text displayed on the button.
     * @param clickListener The click listener.
     */

    public VTDialog setCentralButton(String text, OnClickListener clickListener) {
        return setCentralButton(text, 0, clickListener);
    }

    /**
     * Sets the right button. If you don't call this method
     * the right button will not be displayed.
     * @param text The text displayed on the button.
     * @param clickListener The click listener.
     */

    public VTDialog setRightButton(String text, OnClickListener clickListener) {
        return setRightButton(text, 0, clickListener);
    }

    /**
     * Sets the left button. If you don't call this method
     * the left button will not be displayed.
     * @param text The text displayed on the button.
     * @param clickListener The click listener.
     * @param iconRes Button icon resource id.
     */

    public VTDialog setLeftButton(String text, int iconRes, OnClickListener clickListener) {
        Button newButton = new Button(text, iconRes, clickListener);
        if(dialog.isShowing()){
            setupButton(newButton, leftButtonView);
        }
        leftButton = newButton;
        return this;
    }

    public VTDialog removeLeftButton(){
        setupButton(null, leftButtonView);
        return this;
    }

    public VTDialog removeCentralButton(){
        setupButton(null, centerButtonView);
        return this;
    }

    public VTDialog removeRightButton(){
        setupButton(null, rightButtonView);
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
        Button newButton = new Button(text, iconRes, clickListener);
        if(dialog.isShowing()){
            setupButton(newButton, centerButtonView);
        }
        centralButton = newButton;
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
        Button newButton = new Button(text, iconRes, clickListener);
        if(dialog.isShowing()){
            setupButton(newButton, rightButtonView);
        }
        rightButton = newButton;
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
        this.cancelable = cancelable;
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

    public VTDialog customize(@NonNull DialogCustomization dialogCustomization){
        this.dialogCustomization = dialogCustomization;
        dialogCustomization.dialog = this;
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
        createDialog();
        dialog.show();
    }

    /**
     * Closes the dialog.
     */

    public void dismiss(){
        dialog.dismiss();
    }

    /**
     * Returns left button view.
     * @return left button TextView.
     */

    public TextView getLeftButton(){
        return leftButtonView;
    }

    /**
     * Returns center button view.
     * @return center button TextView.
     */

    public TextView getCentralButton(){
        return centerButtonView;
    }

    /**
     * Returns right button view.
     * @return right button TextView.
     */

    public TextView getRightButton(){
        return rightButtonView;
    }

    public TextView getTitle(){
        return dialTitle;
    }

    public TextView getMessage(){
        return dialMessage;
    }

    public View getContentView(){
        return contentView;
    }

    public <T extends View> T findViewById(@IdRes int id){
        if(contentView == null)
            return null;
        return contentView.findViewById(id);
    }

    /**
     * Returns weather dialog is visible for user.
     * @return true if dialog is visible.
     */

    public boolean isShowing(){
        return dialog.isShowing();
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
        dialog.getWindow().getAttributes().gravity = Gravity.NO_GRAVITY;


        if(dialogMode == DIALOG_MODE_NORMAL) {
            dialog.setContentView(R.layout.d_vt_dialog_alert);
            dialog.getWindow().setLayout(MATCH_PARENT, Screen.getAbsoluteHeight(activity) - Screen.getNavigationBarHeight(activity) - Screen.getStatusBarHeight(activity));
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            root = dialog.findViewById(R.id.root);
            root.setGravity(gravity);
            root.setOnClickListener(v -> {
                if(cancelable)
                    dialog.dismiss();
            });
        } else if(dialogMode == DIALOG_MODE_FULLSCREEN){
            dialog.setContentView(R.layout.d_vt_dialog_fullscreen);

            dialog.getWindow().setStatusBarColor(Color.WHITE);

            dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialTitle = dialog.findViewById(R.id.title);
        dialMessage = dialog.findViewById(R.id.message);

        ImageView icon = dialog.findViewById(R.id.icon);

        CardView background = dialog.findViewById(R.id.background);

        background.setFocusable(true);
        background.setClickable(true);
        background.setOnClickListener(v -> {

        });

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

        leftButtonView = view.findViewById(R.id.leftButton);
        centerButtonView = view.findViewById(R.id.centerButton);
        rightButtonView = view.findViewById(R.id.rightButton);

        setupTextView(this.title, dialTitle);
        setupTextView(this.message, dialMessage);

        if (enabledLinkify) {
            Linkify.addLinks(dialMessage, Linkify.WEB_URLS);
            dialMessage.setLinksClickable(true);
        }

        dialTitle.setSingleLine(titleSingleLine);
        dialMessage.setSingleLine(messageSingleLine);

        setupButton(this.leftButton, leftButtonView);
        setupButton(this.centralButton, centerButtonView);
        setupButton(this.rightButton, rightButtonView);

        if (contentView != null) {
            content.removeAllViewsInLayout();
            content.addView(contentView);
        }

        setupIcon(dialogIcon, icon);

        if(dialogCustomization != null){

            try {

                dialogCustomization.customizeTitleTextView(dialTitle);
                dialogCustomization.customizeMessageTextView(dialMessage);

                dialogCustomization.customizeButtons(leftButtonView, 0);
                dialogCustomization.customizeButtons(rightButtonView, 2);
                dialogCustomization.customizeButtons(centerButtonView, 1);

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
            if(dialog.isShowing() && textView.getVisibility() == VISIBLE) {
                hideButton(textView);
            } else {
                textView.setVisibility(GONE);
            }
        } else {
            textView.setText(buttonData.text);
            ViewUtil.setMarginTop(textView, Metrics.dpToPx(8, activity));
            dialogCustomization.customizeButtons(textView, textView.equals(leftButtonView) ? BUTTON_ID_LEFT : textView.equals(centerButtonView) ? BUTTON_ID_CENTRAL : BUTTON_ID_RIGHT);
            textView.setOnClickListener(view -> {
                if (buttonData.onClickListener != null)
                    buttonData.onClickListener.onClick(textView);
                if (dismissOnButtonClick) {
                    dialog.dismiss();
                }
            });
            if (buttonData.iconRes != 0) {
                textView.setCompoundDrawablesWithIntrinsicBounds(buttonData.iconRes, 0, 0, 0);
                if (buttonIconAutoColor) {
                    textView.getCompoundDrawables()[0].setColorFilter(textView.getTextColors().getDefaultColor(), PorterDuff.Mode.SRC_IN);
                }
            }
            if(dialog.isShowing() && textView.getVisibility() == GONE) {
                showButton(textView);
            } else {
                textView.setVisibility(VISIBLE);
            }
        }
    }

    private void hideButton(TextView button){

        //TODO Animation for horizontal buttons style
        if(buttonsStyle == BUTTONS_STYLE_HORIZONTAL){
            button.setVisibility(GONE);
            return;
        }

        int viewHeight = ViewUtil.getViewHeight(button);
        int viewStartMarginTop = ViewUtil.getMarginTop(button);
        int viewEndMarginBottom = ViewUtil.getMarginBottom(button);

        ValueAnimator a = ValueAnimator.ofFloat(1, 0);
        a.setDuration(250);
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        a.addUpdateListener((animation) -> {
            float factor = (float) animation.getAnimatedValue();
            ViewUtil.setViewHeight(button, (int) (viewHeight * factor));
            ViewUtil.setMarginTop(button, (int) (viewStartMarginTop * factor));
            ViewUtil.setMarginBottom(button, (int) (viewEndMarginBottom * factor));
        });
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                button.setVisibility(View.GONE);
            }
        });
        a.start();
    }

    private void showButton(TextView button){

        //TODO Animation for horizontal buttons style
        if(buttonsStyle == BUTTONS_STYLE_HORIZONTAL){
            button.setVisibility(VISIBLE);
            return;
        }

        int viewHeight = ViewUtil.getViewHeight(button);
        int viewStartMarginTop = ViewUtil.getMarginTop(button);
        int viewEndMarginBottom = ViewUtil.getMarginBottom(button);

        ValueAnimator a = ValueAnimator.ofFloat(0, 1);
        a.setDuration(250);
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        a.addUpdateListener((animation) -> {
            float factor = (float) animation.getAnimatedValue();
            ViewUtil.setViewHeight(button, (int) (viewHeight * factor));
            ViewUtil.setMarginTop(button, (int) (viewStartMarginTop * factor));
            ViewUtil.setMarginBottom(button, (int) (viewEndMarginBottom * factor));
        });
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(android.animation.Animator animation) {
                button.setVisibility(View.VISIBLE);
            }
        });
        a.start();
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
        public int iconRes = 0;
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

        protected VTDialog dialog;

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
