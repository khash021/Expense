package tech.khash.realmtest.util;

import android.view.View;

public class ViewUtil {

    public static void showFadeView(View view, int duration, float alpha) {
        if (view == null || duration < 0 || alpha > 1 || alpha < 0)
            return;

        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);
        view.animate().setDuration(duration).alpha(alpha);
    }

    public static void hideFadeView(final View view, int duration) {
        if (view == null || duration < 0)
            return;
        view.animate().alpha(0).setDuration(duration);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        }, duration);
    }

    public static void showView(View view) {
        if (view == null)
            return;
        view.setVisibility(View.VISIBLE);
    }

    public static void hideView(View view) {
        if (view == null)
            return;
        view.setVisibility(View.GONE);
    }
}
