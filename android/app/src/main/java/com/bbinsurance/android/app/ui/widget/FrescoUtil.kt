package com.bbinsurance.android.app.ui.widget

import com.bbinsurance.android.app.BBApplication
import com.bbinsurance.android.app.R
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams

/**
 * Created by jiaminchen on 18/1/7.
 */
class FrescoUtil {
    companion object {
        fun getCornerRound5DPHierarchy(): GenericDraweeHierarchy {
            var cornerSize = BBApplication.ApplicationContext.resources.getDimensionPixelSize(R.dimen.CornerRoundSize5DP).toFloat()
            val cornerRoundParams = RoundingParams()
            cornerRoundParams.setCornersRadii(cornerSize, cornerSize, cornerSize, cornerSize)
            return GenericDraweeHierarchyBuilder.newInstance(BBApplication.ApplicationContext.resources)
                    .setRoundingParams(cornerRoundParams).build()
        }

        fun getTopCornerRound5DPHierarchy(): GenericDraweeHierarchy {
            var cornerSize = BBApplication.ApplicationContext.resources.getDimensionPixelSize(R.dimen.CornerRoundSize5DP).toFloat()
            val cornerRoundParams = RoundingParams()
            cornerRoundParams.setCornersRadii(cornerSize, cornerSize, 0f, 0f)
            return GenericDraweeHierarchyBuilder.newInstance(BBApplication.ApplicationContext.resources)
                    .setRoundingParams(cornerRoundParams).build()
        }

        fun getCircleHierarchy() : GenericDraweeHierarchy {
            val cornerRoundParams = RoundingParams()
            cornerRoundParams.roundAsCircle = true
            return GenericDraweeHierarchyBuilder.newInstance(BBApplication.ApplicationContext.resources)
                    .setRoundingParams(cornerRoundParams).build()
        }
    }
}