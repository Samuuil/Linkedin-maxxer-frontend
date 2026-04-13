package com.linkedinmaxxer.app.data.session

import android.content.Context
import androidx.datastore.dataStore

val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)
