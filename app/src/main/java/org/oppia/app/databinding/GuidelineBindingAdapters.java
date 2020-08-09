package org.oppia.app.databinding;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.BindingAdapter;

public final class GuidelineBindingAdapters {
  /** Binding adapter for setting the `guidePercent` for a [Guideline]. */
  @BindingAdapter("app:layout_constraintGuide_percent")
  public static void setGuidelinePercentage(@NonNull Guideline guideline, float percentage) {
    ConstraintLayout.LayoutParams params =
        (ConstraintLayout.LayoutParams) guideline.getLayoutParams();
    params.guidePercent = percentage;
    guideline.setLayoutParams(params);
  }
}
