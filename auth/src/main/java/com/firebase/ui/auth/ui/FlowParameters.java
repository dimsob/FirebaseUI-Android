/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.firebase.ui.auth.ui;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import com.firebase.ui.auth.AuthUI.IdpConfig;
import com.firebase.ui.auth.util.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the core parameters and data captured during the authentication flow, in
 * a serializable manner, in order to pass data between activities.
 */
public class FlowParameters implements Parcelable {

    @NonNull
    public final String appName;

    @NonNull
    public final List<IdpConfig> providerInfo;

    @StyleRes
    public final int themeId;

    @DrawableRes
    public final int logoId;

    @Nullable
    public final String termsOfServiceUrl;

    public final boolean smartLockEnabled;

    public FlowParameters(
            @NonNull String appName,
            @NonNull List<IdpConfig> providerInfo,
            @StyleRes int themeId,
            @DrawableRes int logoId,
            @Nullable String termsOfServiceUrl,
            boolean smartLockEnabled) {
        this.appName = Preconditions.checkNotNull(appName, "appName cannot be null");
        this.providerInfo = Preconditions.checkNotNull(providerInfo, "providerInfo cannot be null");
        this.themeId = themeId;
        this.logoId = logoId;
        this.termsOfServiceUrl = termsOfServiceUrl;
        this.smartLockEnabled = smartLockEnabled;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appName);
        dest.writeTypedList(providerInfo);
        dest.writeInt(themeId);
        dest.writeInt(logoId);
        dest.writeString(termsOfServiceUrl);
        dest.writeInt(smartLockEnabled ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FlowParameters> CREATOR = new Creator<FlowParameters>() {
        @Override
        public FlowParameters createFromParcel(Parcel in) {
            String appName = in.readString();
            List<IdpConfig> providerInfo = in.createTypedArrayList(IdpConfig.CREATOR);
            int themeId = in.readInt();
            int logoId = in.readInt();
            String termsOfServiceUrl = in.readString();
            int smartLockEnabledInt = in.readInt();
            boolean smartLockEnabled = (smartLockEnabledInt != 0);

            return new FlowParameters(
                    appName,
                    providerInfo,
                    themeId,
                    logoId,
                    termsOfServiceUrl,
                    smartLockEnabled);
        }

        @Override
        public FlowParameters[] newArray(int size) {
            return new FlowParameters[size];
        }
    };
}
