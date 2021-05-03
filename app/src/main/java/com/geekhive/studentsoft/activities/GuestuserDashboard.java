package com.geekhive.studentsoft.activities;

        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.app.Dialog;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.graphics.drawable.Drawable;
        import android.os.Build;
        import android.os.Bundle;
        import android.view.Gravity;
        import android.view.KeyEvent;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.fragment.app.Fragment;

        import com.geekhive.studentsoft.R;
        import com.geekhive.studentsoft.fragment.GuestHomeFragment;
        import com.geekhive.studentsoft.utils.Prefs;
        import com.google.android.material.bottomappbar.BottomAppBar;
        import com.google.android.material.bottomsheet.BottomSheetDialog;
        import com.google.android.material.floatingactionbutton.FloatingActionButton;
        import com.google.android.material.navigation.NavigationView;

public class GuestuserDashboard extends AppCompatActivity {
    BottomAppBar bar;
    BottomSheetDialog bottomSheetDialog;
    Dialog dialogSuccess;
    String login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_guest_dashboard);

        FloatingActionButton fab = findViewById(R.id.fav);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuestuserDashboard.this, GuestuserDashboard.class));
            }
        });

        Fragment fragment = null;
        fragment = new GuestHomeFragment();
        loadFragment(fragment);
        bar = findViewById(R.id.bar);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Prefs.getPrefRefId(GuestuserDashboard.this).equals("")){
                    openNavigationMenu();
                }else {

                }

            }
        });

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.login_gradient);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }

    private void openNavigationMenu() {

        //this will get the menu layout
        final View bootomNavigation = getLayoutInflater().inflate(R.layout.guest_navigation_menu,null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bootomNavigation);
        bottomSheetDialog.show();

        //this will find NavigationView from id
        NavigationView navigationView = bootomNavigation.findViewById(R.id.navigation_menu);

        //This will handle the onClick Action for the menu item
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.logout:
                        Initializepopup();
                        initializdeletePopup();
                        bottomSheetDialog.dismiss();
                        break;
                }
                return loadFragment(fragment);
            }
        });
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void Initializepopup() {
        dialogSuccess = new Dialog(this);
        dialogSuccess.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogSuccess.setContentView(R.layout.popup_success);
        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
    }

    private void initializdeletePopup() {
        dialogSuccess.setContentView(R.layout.popup_success);
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
        final TextView vT_psd_yes = (TextView) dialogSuccess.findViewById(R.id.vT_psd_yes);
        final TextView vT_psd_no = (TextView) dialogSuccess.findViewById(R.id.vT_psd_no);
        final TextView vT_sucess = dialogSuccess.findViewById(R.id.vT_sucess);
        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
//        deletePopup.getWindow().setLayout(width, height);
        dialogSuccess.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogSuccess.getWindow().setGravity(Gravity.CENTER);

        vT_psd_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    dialogSuccess.dismiss();
                    Prefs.setUserId(GuestuserDashboard.this, null);
                    Prefs.setUserType(GuestuserDashboard.this, null);
                    startActivity(new Intent(GuestuserDashboard.this, LoginActivity.class));
                    GuestuserDashboard.this.finish();
                }
            }
        });

        vT_psd_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogSuccess.isShowing()) {
                    dialogSuccess.dismiss();
                }
            }
        });

        dialogSuccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSuccess.setCancelable(true);
        dialogSuccess.setCanceledOnTouchOutside(true);
        dialogSuccess.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            GuestuserDashboard.this.finish();

        }
        return super.onKeyDown(keyCode, event);
    }

}

