package com.lichuang.blindenglish

object DataProvider {


    private val voaReplayJS =
        "RFE.multimediaPlayers[1].core.isPaused()?" +
                "RFE.multimediaPlayers[1].core.play():RFE.multimediaPlayers[1].core.pause()"
    private val voaRewindJS =
        "currentTime=RFE.multimediaPlayers[1].core.getCurrentTime();" +
                "toTime=currentTime>15?currentTime-15:0;" +
                "RFE.multimediaPlayers[1].core.setCurrentTime(toTime)"

    fun getMediaItems(): ArrayList<MediaItem> {
        var items = ArrayList<MediaItem>()
        if (items.size <= 0) {
            items.add(
                generateNprMedia(
                    "NPR: All Things Considered",
                    R.mipmap.npr_all_things_considered,
                    "综合性新闻，晚上更新录音文稿。",
                    "https://www.npr.org/programs/all-things-considered/"
                )
            )
            items.add(
                generateVoaMedia(
                    "VOANews:  Learning English",
                    R.mipmap.voa_learning_english,
                    "经典VOA慢速英语 每日更新。",
                    "https://learningenglish.voanews.com/"
                )
            )
            items.add(
                generateNprMedia(
                    "NPR: Morning Edition",
                    R.mipmap.npr_morning_edition,
                    "每日早报，早上更新录音文稿。",
                    "https://www.npr.org/programs/morning-edition/"
                )
            )
            items.add(
                generateNprMedia(
                    "NPR: Weekend Edition Saturday",
                    R.mipmap.npr_weekend_edition,
                    "周六晚更新文稿",
                    "https://www.npr.org/programs/weekend-edition-saturday/"
                )
            )
            items.add(
                generateNprMedia(
                    "NPR: How I Built This with Guy Raz",
                    R.mipmap.npr_how_i_build_this_with_guy_raz,
                    "创业背后",
                    "https://www.npr.org/podcasts/510313/how-i-built-this"
                )
            )
            items.add(
                generateNprMedia(
                    "NPR: Planet Money",
                    R.mipmap.npr_planet_money,
                    "漫谈经济",
                    "https://www.npr.org/podcasts/510289/planet-money"
                )
            )
            items.add(
                generateNprMedia(
                    "NPR: Weekend Edition Sunday",
                    R.mipmap.npr_weekend_edition,
                    "周日晚更新文稿",
                    "https://www.npr.org/programs/weekend-edition-sunday/"
                )
            )
            items.add(
                generateNprMedia(
                    "NPR: Hidden Brain",
                    R.mipmap.npr_hidden_brain,
                    "趣味心理学",
                    "https://www.npr.org/podcasts/510308/hidden-brain/"
                )
            )
            items.add(
                generateNprMedia(
                    "NPR: Fresh Air",
                    R.mipmap.npr_fresh_air,
                    "新气象...",
                    "https://www.npr.org/programs/fresh-air"
                )
            )
        }
        return items
    }

    private fun generateNprMedia(
        title: String,
        icon: Int,
        subtitle: String,
        urlString: String
    ): MediaItem {
        return MediaItem(
            title,
            icon,
            subtitle,
            urlString,
            "\$('.player-rewind').click()",
            "\$('.player-play-pause-stop').click()"
        )

    }

    private fun generateVoaMedia(
        title: String,
        icon: Int,
        subtitle: String,
        urlString: String
    ): MediaItem {
        return MediaItem(
            title,
            icon,
            subtitle,
            urlString,
            voaRewindJS,
            voaReplayJS
        )
    }
}