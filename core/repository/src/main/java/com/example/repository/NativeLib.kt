package com.example.repository

class NativeLib {

    /**
     * A native method that is implemented by the 'repository' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'repository' library on application startup.
        init {
            System.loadLibrary("repository")
        }
    }
}
