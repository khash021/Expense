package tech.khash.expense.util;

import android.animation.Animator;
import android.view.View;

import androidx.annotation.NonNull;

public class ViewUtil {

    //TODO: cleanup this mess
    //TODO: write unit tests for these methods

    public static void fadeInView(@NonNull View view, @NonNull int duration, @NonNull float alpha) {
        if ( duration < 0 || alpha > 1 || alpha < 0)
            return;

        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);
        view.animate().setDuration(duration).alpha(alpha);
    }

    public static void fadeOutView(@NonNull final View view, @NonNull int duration) {
        view.animate().alpha(0).setDuration(duration);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.GONE);
            }
        }, duration);
    }

    public static void fadeInViewWithListener(@NonNull final View view, @NonNull int durationIn,
                                              @NonNull final float alpha,
                                              @NonNull final AnimationEndedListener listener) {
        if (durationIn < 0 || alpha < 0)
            return;

        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);

        view.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                listener.onAnimationEnded();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setDuration(durationIn).alpha(alpha);
    }

    public static void fadeOutViewWithListener(@NonNull final View view, @NonNull int durationOut,
                                              @NonNull final AnimationEndedListener listener) {
        if (durationOut < 0)
            return;

        view.setVisibility(View.VISIBLE);

        view.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                listener.onAnimationEnded();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setDuration(durationOut).alpha(0);
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

    public interface AnimationEndedListener {
        void onAnimationEnded();
    }
}
