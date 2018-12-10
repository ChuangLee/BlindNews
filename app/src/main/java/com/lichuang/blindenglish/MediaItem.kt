package com.lichuang.blindenglish

import java.io.Serializable

class MediaItem(
    title: String,
    icon: Int,
    subtitle: String,
    urlString: String,
    rewindJS: String,
    replayJS: String
) : Serializable {
    var title: String = title
    var subtitle: String? = subtitle
    var urlString: String? = urlString
    var icon: Int = icon
    var rewindJS: String? = rewindJS
    var replayJS: String? = replayJS
}