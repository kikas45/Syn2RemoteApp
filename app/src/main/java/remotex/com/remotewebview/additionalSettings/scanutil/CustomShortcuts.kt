package remotex.com.remotewebview.additionalSettings.scanutil


import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.*
import remotex.com.remotewebview.WebActivity
import remotex.com.remotewebview.additionalSettings.utils.Constants


@RequiresApi(Build.VERSION_CODES.N_MR1)
object CustomShortcuts {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun setUp(context: Context, textView: String, iconBitmap: Bitmap) {
        val shortcutManager =
            getSystemService<ShortcutManager>(context, ShortcutManager::class.java)


        val intents = arrayOf(
            Intent(Intent.ACTION_VIEW, null, context, WebActivity::class.java),
        )

        val shortcut2 = ShortcutInfo.Builder(context, Constants.shortcut_messages_id)
            .setShortLabel(textView)
            .setLongLabel("Open $textView")
            .setIcon(Icon.createWithBitmap(iconBitmap))
            .setIntents(intents)
            .build()


        shortcutManager!!.dynamicShortcuts = listOf(shortcut2)
    }

}