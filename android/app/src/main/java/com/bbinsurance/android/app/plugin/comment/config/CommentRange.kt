package com.bbinsurance.android.app.plugin.comment.config

/**
 * Created by jiaminchen on 17/11/22.
 */
class CommentRange : Comparable<CommentRange> {

    override fun compareTo(other: CommentRange): Int {
        if (Top > other.Top) {
            return 1
        } else if (Top < other.Top) {
            return -1
        } else {
            if (Bottom > other.Bottom) {
                return 1
            } else if (Bottom < other.Bottom) {
                return -1
            }
            return 0
        }
    }

    var Top: Long = 0    // 较大ID
    var Bottom: Long = 0   //较小ID

    fun merge(other: CommentRange): Boolean {
        if (Top >= other.Bottom && Bottom <= other.Top) {
            if (Bottom > other.Bottom) {
                Bottom = other.Bottom
            }
            if (Top < other.Top) {
                Top = other.Top
            }
            return true
        } else {
            return false
        }
    }
}