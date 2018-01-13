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
            var Company = 4

            var Count = 5
        }
    }

    class IntentKey {
        companion object {
            val KeyTitle = "key_title"
            val KeyUrl = "key_url"
        }
    }

    class CommentDetailUI {
        companion object {
            val KeyComment = "key_comment"
            val KeyNeedKeyboard = "key_need_keyboard"
        }
    }

    class CompanySelectListUI {
        companion object {
            val KeySelectCompany = "key_select_company"
        }
    }
    class InsuranceSelectListUI {
        companion object {
            val KeySelectInsuranceType = "key_select_insurance_type"
        }
    }
}