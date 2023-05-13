package com.example.runic;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.provider.Settings;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetData {

    String getAllData(Context context){
        SettingsFinder settingsFinder = new SettingsFinder(context);
        DeviceData deviceData = new DeviceData(context);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"data\":[");

        try {
            stringBuilder.append("\"").append(getAndroidID(context)).append("\",");
            stringBuilder.append("\"").append(getVerifyInstallerId(context)).append("\",");
            stringBuilder.append("\"").append(deviceData.getVerifyBootState()).append("\",");
            stringBuilder.append("\"").append(deviceData.getVerifyMode()).append("\",");
            stringBuilder.append("\"").append(deviceData.getSecurityPatchLevel()).append("\",");
            stringBuilder.append("\"").append(deviceData.getOemLocked(context)).append("\",");
            stringBuilder.append("\"").append(deviceData.getProductBrand()).append("\",");
            stringBuilder.append("\"").append(deviceData.getProductModel()).append("\",");
            stringBuilder.append("\"").append(deviceData.getOemUnlockSupported()).append("\",");
            stringBuilder.append("\"").append(getIsDebuggable(context)).append("\",");
            stringBuilder.append("\"").append(getPrimarySignature(context)).append("\",");
            stringBuilder.append("\"").append(getIsEmulator()).append("\",");
            stringBuilder.append("\"").append(settingsFinder.getFingerprintStatus()).append("\",");
            stringBuilder.append("\"").append(settingsFinder.getStorageEncryptionStatus()).append("\",");
            stringBuilder.append("\"").append(settingsFinder.nonMarketAppsEnabled()).append("\",");
            stringBuilder.append("\"").append(settingsFinder.getAdbEnabled()).append("\",");
            stringBuilder.append("\"").append(settingsFinder.getLockScreenTimeout()).append("\",");
            stringBuilder.append("\"").append(settingsFinder.getLockScreenType()).append("\",");
            stringBuilder.append("\"").append(settingsFinder.getNotificationVisibility()).append("\",");
            stringBuilder.append("\"").append(getOtherAppPermissions(context)).append("\"");
            // TODO: Add a ',' to the above if adding any after it

        }catch (PackageManager.NameNotFoundException | NoSuchFieldException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        stringBuilder.append("]}");
        return stringBuilder.toString();
        /*,
                ,debug_enabled_textbox,
                primary_certificate_textbox,emulator_textbox,,
        */

    }

    private String getOtherAppPermissions(Context context) {
        StringBuilder appNameAndPermissions = new StringBuilder();
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);
                appNameAndPermissions.append(packageInfo.packageName).append("*******");

                //Get Permissions
                String[] requestedPermissions = packageInfo.requestedPermissions;
                if (requestedPermissions != null) {
                    for (int i = 0; i < requestedPermissions.length; i++) {
                        appNameAndPermissions.append(requestedPermissions[i] + " ");
                    }
                    appNameAndPermissions.append("-");
                }
                appNameAndPermissions.append("&&");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return appNameAndPermissions.toString();
    }


    private boolean getIsEmulator() {
        return (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.HARDWARE.contains("goldfish")
                || Build.HARDWARE.contains("ranchu")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.PRODUCT.contains("sdk_google")
                || Build.PRODUCT.contains("google_sdk")
                || Build.PRODUCT.contains("sdk")
                || Build.PRODUCT.contains("sdk_x86")
                || Build.PRODUCT.contains("sdk_gphone64_arm64")
                || Build.PRODUCT.contains("vbox86p")
                || Build.PRODUCT.contains("emulator")
                || Build.PRODUCT.contains("simulator");
    }

    int getPrimarySignature(Context context) throws PackageManager.NameNotFoundException {
        Signature[] sigs = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
        return  sigs[0].hashCode();
    }

    boolean getIsDebuggable(Context context){

        return ( 0 != ( context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE ) );
    }

     boolean getVerifyInstallerId(Context context) {
        // A list with valid installers package name
        List<String> validInstallers = new ArrayList<>(Arrays.asList("com.android.vending", "com.google.android.feedback"));

        // The package name of the app that has installed your app
        final String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());

        // true if your app has been downloaded from Play Store
        return installer != null && validInstallers.contains(installer);
    }

    String getAndroidID(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }



}
