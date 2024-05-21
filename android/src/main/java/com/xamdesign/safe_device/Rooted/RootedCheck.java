package com.xamdesign.safe_device.Rooted;

import android.content.Context;

import com.scottyab.rootbeer.RootBeer;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;

public class RootedCheck {
    private static final String ONEPLUS = "oneplus";
    private static final String MOTO = "moto";
    private static final String XIAOMI = "xiaomi";
    private static final String LENOVO = "lenovo";


    /**
     * Checks if the device is rooted.
     *
     * @return <code>true</code> if the device is rooted, <code>false</code> otherwise.
     */
    public static boolean isJailBroken(Context context) {
        CheckApiVersion check;

        if (Build.VERSION.SDK_INT >= 23) {
            check = new GreaterThan23();
        } else {
            check = new LessThan23();
        }
        return check.checkRooted() || rootBeerCheck(context);
    }

    public static List<String> isJailBrokenVerbose(Context context) {
        CheckApiVersion check;

        if (Build.VERSION.SDK_INT >= 23) {
            check = new GreaterThan23();
        } else {
            check = new LessThan23();
        }

        List<String> issues = check.checkRootedVerbose();
        if (issues != null  && !issues.isEmpty()) {
            return issues;    
        }

        return rootBeerCheckVerbose(context);
    }

    private static List<String> rootBeerCheckVerbose(Context context) {
        List<String> issues = new ArrayList<String>();
        RootBeer rootBeer = new RootBeer(context);
        
        String brand = Build.BRAND.toLowerCase();
        if(brand.contains(ONEPLUS) || brand.contains(MOTO) || brand.contains(XIAOMI) || brand.contains(LENOVO)) {
            if(rootBeer.isRootedWithoutBusyBoxCheck()) {
                issues.add("RootBeer.isRootedWithoutBusyBoxCheck found an issue");
            }
        } else {
            if (rootBeer.isRooted()) {
                issues.add("RootBeer.isRooted found an issue");
            }
        }

        return issues;
    }

    private static Boolean rootBeerCheck(Context context) {
        RootBeer rootBeer = new RootBeer(context);
        
        String brand = Build.BRAND.toLowerCase();
        if(brand.contains(ONEPLUS) || brand.contains(MOTO) || brand.contains(XIAOMI) || brand.contains(LENOVO)) {
            return rootBeer.isRootedWithoutBusyBoxCheck();
        } else {
            return rootBeer.isRooted();
        }
    }
}

