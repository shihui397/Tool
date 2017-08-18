package com.jsd.library.permission;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by tangshuai on 16/6/20.
 */
public class PermissionActionStore {

    private HashMap<Integer, IPermissionAction> permissionActionHashMap = new HashMap<>();
    private Activity activity;
    private Fragment fragment;

    public PermissionActionStore(@NonNull Activity activity) {
        this.activity = activity;
    }

    public PermissionActionStore(@NonNull Fragment fragment) {
        this.fragment = fragment;
    }

    public IPermissionAction create(int requestCode) {
        IPermissionAction action;
        if ((action = permissionActionHashMap.get(requestCode)) != null) {
            return action;
        }
        if(activity != null){
            action = new PermissionAction(activity);
        }else{
            action = new PermissionAction(fragment);
        }
        ((PermissionAction) action).setActionId(requestCode);
        permissionActionHashMap.put(requestCode, action);
        return action;
    }


    public void donePermissionAction(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        IPermissionAction action;
        if ((action = permissionActionHashMap.get(requestCode)) != null) {
            action.done(requestCode, permissions, grantResults);
        }
    }

}
