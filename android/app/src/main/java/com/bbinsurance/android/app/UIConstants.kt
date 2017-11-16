package com.bbinsurance.android.app

/**
 * Created by jiaminchen on 2017/10/25.
 */
class UIConstants {

    class ListViewType {
        companion object {
            var UnKnown = 0
            var RecommendInsurance = 1
            var LearnArticle = 2
            var Comment = 3

            var Count = 4
        }
    }

    class IntentKey {
        companion object {
            val KeyTitle = "key_title"
            val KeyUrl = "key_url"
        }
    }
}