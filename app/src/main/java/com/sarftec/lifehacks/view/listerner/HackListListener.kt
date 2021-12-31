package com.sarftec.lifehacks.view.listerner

import com.sarftec.lifehacks.view.advertisement.RewardVideoManager
import com.sarftec.lifehacks.view.handler.ReadWriteHandler
import com.sarftec.lifehacks.view.handler.ToolingHandler

interface HackListListener {
    fun toolingHandler() : ToolingHandler
    fun readWriteHandler() : ReadWriteHandler
    fun showLoadingDialog(isShown: Boolean)
    fun getRewardVideoManager() : RewardVideoManager
}