package com.multiapk.library.base

import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent

open class CMAppCompatActivity : AppCompatActivity() {

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            window.decorView.clearAnimation()

            val fragments = supportFragmentManager.fragments
            if (fragments != null && fragments.size > 0) {
                val fragment = fragments[0]
                if (fragment is CMFragmentOnBackListener) {
                    val canPropagate = fragment.onBackPressed()
                    if (canPropagate) {
                        try {
                            supportFragmentManager.popBackStackImmediate()
                        } catch (ignored: Exception) {
                        }

                        return true
                    }
                }
            }
            if (supportFragmentManager.backStackEntryCount == 0) {
                finish()
            } else {
                try {
                    supportFragmentManager.popBackStackImmediate()
                } catch (ignored: Exception) {
                }

            }
            return true
        } else {
            return super.onKeyDown(keyCode, event)
        }
    }
}
