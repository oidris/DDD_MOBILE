package org.fhi360.ddd;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View theMenu;
    private View menu1;
    private View menu2;
    private View menu3;
    private View menu4;
    private View menu5;
    private View menu6;
    private View overlay;

    private boolean menuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        theMenu = findViewById(R.id.the_menu);
        menu1 = findViewById(R.id.menu1);
        menu2 = findViewById(R.id.menu2);
        menu4 = findViewById(R.id.menu4);
        menu5 = findViewById(R.id.menu5);
        menu6 = findViewById(R.id.menu6);
        overlay = findViewById(R.id.overlay);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.report) {

            if (!menuOpen) {
                revealMenu();
            } else {
                hideMenu();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void revealMenu() {
        menuOpen = true;
        theMenu.setVisibility(View.INVISIBLE);
        int cx = theMenu.getRight() - 200;
        int cy = theMenu.getTop();
        int finalRadius = Math.max(theMenu.getWidth(), theMenu.getHeight());
        Animator anim =
                ViewAnimationUtils.createCircularReveal(theMenu, cx, cy, 0, finalRadius);
        anim.setDuration(600);
        theMenu.setVisibility(View.VISIBLE);
        overlay.setVisibility(View.VISIBLE);
        anim.start();
        Animation popeup1 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popeup2 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popeup3 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popeup4 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popeup5 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        Animation popeup6 = AnimationUtils.loadAnimation(this, R.anim.popeup);
        popeup1.setStartOffset(50);
        popeup2.setStartOffset(100);
        popeup3.setStartOffset(150);
        popeup4.setStartOffset(200);
        popeup5.setStartOffset(250);
        popeup6.setStartOffset(300);
        menu1.startAnimation(popeup1);
        menu2.startAnimation(popeup2);
        menu4.startAnimation(popeup4);
        menu5.startAnimation(popeup5);
        menu6.startAnimation(popeup6);

    }

    public void hideMenu() {
        menuOpen = false;
        int cx = theMenu.getRight() - 200;
        int cy = theMenu.getTop();
        int initialRadius = theMenu.getWidth();
        Animator anim = ViewAnimationUtils.createCircularReveal(theMenu, cx, cy, initialRadius, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                theMenu.setVisibility(View.INVISIBLE);
                theMenu.setVisibility(View.GONE);
                overlay.setVisibility(View.INVISIBLE);
                overlay.setVisibility(View.GONE);
            }
        });
        anim.start();
    }

    @Override
    public void onBackPressed() {
        if (menuOpen) {
            hideMenu();
        } else {
            finishAfterTransition();
        }
    }

    public void overlayClick(View v) {
        hideMenu();
    }

    public void menuClick(View view) {
        hideMenu();
        if(view.getTag().equals("Dashboard")){
            Intent intent = new Intent(view.getContext(), FacilityDashboard.class);
            view.getContext().startActivity(intent);
        }

        if(view.getTag().equals("Routine reports")){
            Intent intent = new Intent(view.getContext(), ReportingPeriod1.class);
            view.getContext().startActivity(intent);
        }

        if(view.getTag().equals("List of registered DDD outlets")){
            Intent intent = new Intent(view.getContext(), ListDDDClient.class);
            view.getContext().startActivity(intent);
        }

        if(view.getTag().equals("List of clients for DDD")){
            Intent intent = new Intent(view.getContext(), PatientList2.class);
            view.getContext().startActivity(intent);
        }
        if(view.getTag().equals("Defaulters List")){
            Intent intent = new Intent(view.getContext(), DefaulterListActivity.class);
            view.getContext().startActivity(intent);
        }


    }
}
