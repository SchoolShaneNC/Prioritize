package com.Prioritize.View;

import java.awt.Color;

/** Provides continuous, accessible supporting colors for urgency scores from 1 to 10. */
public final class UrgencyColor {

    private static final Color LOW = new Color(46, 125, 50);
    private static final Color MEDIUM = new Color(251, 192, 45);
    private static final Color HIGH = new Color(198, 40, 40);

    private UrgencyColor() {
    }

    public static Color forScore(double score) {
        double normalized = Math.max(1.0, Math.min(10.0, score));
        if (normalized <= 5.0) {
            return interpolate(LOW, MEDIUM, (normalized - 1.0) / 4.0);
        }
        return interpolate(MEDIUM, HIGH, (normalized - 5.0) / 5.0);
    }

    public static String labelForScore(double score) {
        if (score >= 7.0) {
            return "HIGH";
        }
        if (score >= 4.0) {
            return "MEDIUM";
        }
        return "LOW";
    }

    private static Color interpolate(Color from, Color to, double amount) {
        int red = (int) Math.round(from.getRed() + (to.getRed() - from.getRed()) * amount);
        int green = (int) Math.round(from.getGreen() + (to.getGreen() - from.getGreen()) * amount);
        int blue = (int) Math.round(from.getBlue() + (to.getBlue() - from.getBlue()) * amount);
        return new Color(red, green, blue);
    }
}
