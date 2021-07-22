package com.example.test

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Environment

object AudioManager {

    private val TAG="AudioManager"

    val PLAY_MUSIC_FINISH="play_music_finish"

    private var taskKey2MediaPlayer= HashMap<String, MediaPlayer?>()

    fun existMediaPlayer(taskKey:String):Boolean{
        val filter= taskKey2MediaPlayer.keys.find { taskKey==it }
        if (filter.isNullOrEmpty()){
            return false
        }
        return true
    }

    fun getMediaPlayer(taskKey:String): MediaPlayer?{
        return taskKey2MediaPlayer.get(taskKey)
    }

    fun pauseAll(){
        taskKey2MediaPlayer.values.forEach{
            it?.pause()
        }
    }

    fun pauseOthers(taskKey:String){
        for (it in taskKey2MediaPlayer){
            if(it.key==taskKey){
                continue
            }
            it.value?.pause()
        }
    }

    fun play(context: Context, uri: Uri?=null, taskKey:String, playCallback:(()->Unit)?=null, errorCallback:(()->Unit)?=null) {
        pauseOthers(taskKey)

        //val descriptor = context.assets.openFd("aa.mp3")
        if(!existMediaPlayer(taskKey)){
            val mediaPlayer = MediaPlayer().apply {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                        .build()
//                }
                //setDataSource(descriptor.fileDescriptor,descriptor.startOffset, descriptor.length)
                setDataSource(context,uri!!)
                prepareAsync()
                setOnPreparedListener({
                    it?.start()
                    //it?.setVolume(1f, 1f)
                    if(playCallback!=null){
                        playCallback()
                    }
                })
                setOnCompletionListener {
                    if(playCallback!=null){
                        taskKey2MediaPlayer.remove(taskKey)
                        //playCallback()

                    }
                }
                setOnErrorListener { mp, what, extra ->
                    if(errorCallback!=null){
                        errorCallback()
                    }
                    true
                }
            }
            taskKey2MediaPlayer.put(taskKey,mediaPlayer)
        }else {
            val mediaPlayer=taskKey2MediaPlayer.get(taskKey)
            if(mediaPlayer!=null&&mediaPlayer.isPlaying()){
                mediaPlayer?.pause()
            }else{
                mediaPlayer?.start()
            }
            if(playCallback!=null){
                playCallback()
            }
        }

    }

    fun release() {
        taskKey2MediaPlayer.values.forEach {
            it?.release()
        }
        taskKey2MediaPlayer.clear()
    }

    fun removePlay(taskKey: String) {
        var mediaPlayer= taskKey2MediaPlayer.remove(taskKey)
        mediaPlayer?.release()
        mediaPlayer=null
    }

    fun test(){

    }
}