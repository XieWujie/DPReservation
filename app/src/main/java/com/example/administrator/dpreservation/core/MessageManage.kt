package com.example.administrator.dpreservation.core

import android.content.Context
import android.util.Log
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.GetCallback
import com.avos.avoscloud.im.v2.*
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage
import com.avos.avoscloud.im.v2.messages.AVIMVideoMessage
import com.example.administrator.dpreservation.data.AppDatabase
import com.example.administrator.dpreservation.data.message.Message
import com.example.administrator.dpreservation.data.message.MessageRepository
import com.example.administrator.dpreservation.data.user.User
import com.example.administrator.dpreservation.utilities.*
import java.lang.Exception
import java.util.*

object MessageManage{

    var repository: MessageRepository? = null
    var client: AVIMClient? = null
    var owner:User? = null
    var count:TempCountGet? = null

    private val avimMessageOption = AVIMMessageOption()
    private val conversationMap = HashMap<String, AVIMConversation>()

    init {
        avimMessageOption.isReceipt = true
    }

    fun logout(){
        client = null
        owner = null
    }

     fun sendMessage(message: Message, e: (e: Exception?) -> Unit) {
        if (message.sendState == SENDING) {
            repository?.insert(message)
        }
        getConversation(message.conversationId) {
            when (message.type) {
                TEXT_MESSAGE -> sendTextMessage(it, message, e)
                IMAGE_MESSAGE -> sendImageMessage(it, message, e)
                VOICE_MESSAGE -> sendVoiceMessage(it, message, e)
            }
        }
    }

    private fun getConversation(id: String, callback: (conversation: AVIMConversation) -> Unit) {
        getClient { client ->
            if (conversationMap.containsKey(id)) {
                callback(conversationMap[id]!!)
            } else {
                val conversation = client?.getConversation(id)
                if (conversation == null) {
                    return@getClient
                }
                if (conversation.isShouldFetch) {
                    conversation?.fetchInfoInBackground(object : AVIMConversationCallback() {
                        override fun done(e: AVIMException?) {
                            if (e == null) {
                                conversationMap[id] = conversation
                                callback(conversation)
                            } else {
                                e.printStackTrace()
                            }
                        }
                    })
                } else {
                    conversationMap[id] = conversation
                    callback(conversation)
                }
            }
        }
        }

    private fun sendTextMessage(conversation: AVIMConversation, message: Message, exeption: (e: Exception?) -> Unit) {
        val m = AVIMTextMessage()
        m.text = message.message
            conversation.sendMessage(m, avimMessageOption, object : AVIMConversationCallback() {
                override fun done(e: AVIMException?) {
                    if (e == null) {
                        repository?.delete(message)
                        val m = message.copy(id = m.messageId, createAt = m.timestamp, sendState = SEND_SUCCEED)
                        repository?.insert(m)
                        conversation.read()
                        exeption(e)
                    } else {
                        val message = message.copy(sendState = SEND_FAIL)
                        repository?.insert(message)
                        exeption(e)
                    }
                }
            })
        }

    fun finConversationById(conversationId:String,findCallback:(conversation:AVIMConversation)->Unit){
        getConversation(conversationId,findCallback)
    }


    private fun sendImageMessage(conversation: AVIMConversation, message: Message, exeption: (e: Exception?) -> Unit) {
        val imageMessage = AVIMImageMessage(message.message)
        conversation.sendMessage(imageMessage, avimMessageOption, object : AVIMConversationCallback() {
            override fun done(e: AVIMException?) {
                if (e == null) {
                    repository?.delete(message)
                    val m = message.copy(
                        id = imageMessage.messageId,
                        createAt = imageMessage.timestamp,
                        sendState = SEND_SUCCEED
                    )
                    repository?.insert(m)
                    conversation.read()
                    exeption(e)
                } else {
                    val message = message.copy(sendState = SEND_FAIL)
                    repository?.insert(message)
                    exeption(e)
                }
            }
        })
    }

    private fun sendVoiceMessage(conversation: AVIMConversation, message: Message, exeption: (e: Exception?) -> Unit) {
        val voiceMessage = AVIMVideoMessage(message.message)
        conversation.sendMessage(voiceMessage, avimMessageOption, object : AVIMConversationCallback() {
            override fun done(e: AVIMException?) {
                if (e == null) {
                    repository?.delete(message)
                    val m = message.copy(
                        id = voiceMessage.messageId,
                        createAt = voiceMessage.timestamp,
                        sendState = SEND_SUCCEED
                    )
                    repository?.insert(m)
                    conversation.read()
                    exeption(e)
                } else {
                    val message = message.copy(sendState = SEND_FAIL)
                    repository?.insert(message)
                    exeption(e)
                }
            }
        })
    }

     fun cacheMessage(message: Message, isUpdate: Boolean) {
        if (isUpdate) {
            repository?.update(message)
        } else {
            repository?.insert(message)
        }
    }


    fun freshMessage() {
        fetchNewMessage()
    }


    fun fetchNewMessage() {
        getClient { client->
            val query = client?.conversationsQuery
            query?.findInBackground(object : AVIMConversationQueryCallback() {
                override fun done(list: MutableList<AVIMConversation>?, e: AVIMException?) = if (e == null) {
                    list!!.forEach {
                        queryMessageByConversationId(it.conversationId, 1)
                    }
                } else {
                    e.printStackTrace()
                }
            })
        }
    }


    fun getClient(getCallback:(client:AVIMClient?)->Unit){
        if (owner !=null && client == null){
            client = AVIMClient.getInstance(owner!!.userId)
            client!!.open(object :AVIMClientCallback(){

                override fun done(c: AVIMClient?, e: AVIMException?) {
                   getCallback(c)
                }
            })
        }else{
            getCallback(client)
        }
    }



     fun queryMessageByConversationId(id: String, limit: Int) {
        getConversation(id) { conversation ->
            conversation.queryMessages(limit, object : AVIMMessagesQueryCallback() {
                override fun done(list: MutableList<AVIMMessage>?, e: AVIMException?) {
                    if (e == null) {
                        convertMessages(conversation, list!!) {
                            repository?.insert(it)
                        }
                    }
                }
            })
        }
    }


     fun queryMessageByTime(id: String, timeStamp: Long) {
        getConversation(id) { conversation ->
            conversation.queryMessages(id, timeStamp, 20, object : AVIMMessagesQueryCallback() {

                override fun done(list: MutableList<AVIMMessage>?, e: AVIMException?) {
                    if (e == null) {
                        convertMessages(conversation, list!!) {
                            repository?.insert(it)
                        }
                    }
                }
            })
        }
    }

    private fun convertMessages(
        conversation: AVIMConversation,
        messages: List<AVIMMessage>,
        convertCallback: (m: List<Message>) -> Unit
    ) {
        val ownerId = owner?.userId
        if (messages.isEmpty()||ownerId.isNullOrBlank()) {
            return
        }
        val unReadCount = conversation.unreadMessagesCount
        val conId = conversation.conversationId
        val map = conversation["Info"] as Map<String, String?>
        val otherId = map[getKey(ownerId, USER_ID)]!!
        var name: String
        var avatar: String?
        var type: Int
        var content: String
        var voiceTime = 0.0
        findContactMessageById(otherId) { a, n ->
            val list = messages.asReversed()
                .filter {
                    (it is AVIMTextMessage || it is AVIMImageMessage || it is AVIMVideoMessage)
                }
                .map {
                    if (it.from == ownerId) {
                        name = owner!!.name
                        avatar = owner!!.avatar
                    } else {
                        name = n
                        avatar = a
                    }
                    when (it) {
                        is AVIMTextMessage -> {
                            content = it.text
                            type = TEXT_MESSAGE
                        }
                        is AVIMImageMessage -> {
                            content = it.fileUrl
                            type = IMAGE_MESSAGE
                        }
                        is AVIMVideoMessage -> {
                            content = it.fileUrl
                            type = VOICE_MESSAGE
                            voiceTime = it.duration
                        }
                        else -> {
                            content = it.content
                            type = UNKNOW_TYPE
                        }
                    }
                    Message(
                        it.messageId,
                        conId,
                        name,
                        content,
                        name,
                        it.from,
                        type,
                        voiceTime,
                        unReadCount,
                        it.timestamp,
                        ownerId,
                        SEND_SUCCEED,
                        avatar
                    )
                }.toList()
            convertCallback(list)
        }
    }

    fun findContactMessageById(contactId: String, findCallback: (avatar: String?, name: String) -> Unit) {
        if (contactId == owner?.userId){
            findCallback(owner!!.avatar,owner!!.name)
        }else{
            getUserObjectById(contactId){
                val avatar = it.getString(AVATAR)
                val name = it.getString(USER_NAME)
                findCallback(avatar,name)
            }
        }
    }

    fun getUserObjectById(userId:String,findCallback:(o:AVObject)->Unit){
        val o = AVObject.createWithoutData("_User",userId)
        o.fetchInBackground(object :GetCallback<AVObject>(){
            override fun done(p0: AVObject?, p1: AVException?) {
                if (p1 == null){
                    findCallback(p0!!)
                }else{
                    p1.printStackTrace()
                }
            }
        })
    }

    fun init(context: Context,user: User?){
        repository = MessageRepository.getInstance(AppDatabase.getInstance(context).getMessageDao())
        count = TempCountGet(context)
        this.owner = user
        if (user!=null){
            fetchNewMessage()
        }
    }

    fun deleteMessage(message: Message) {
        repository?.delete(message)
    }

    fun deleteMessages(conversationId: String) {
        repository?.delete(conversationId)
    }
    fun sendMessage(name:String,conversationId: String,type:Int,content:String,exception: (e:Exception?)->Unit){
        sendMessage(name,conversationId,type,content,0.0,exception)
    }

    fun sendMessage(name:String,conversationId: String,type:Int,content:String,voiceTime:Double,exception: (e:Exception?)->Unit){
        val ownerName = owner?.name!!
        val message = Message(getTempMessageId(),conversationId,name,
            content,ownerName, owner!!.userId,type, voiceTime,0, Date().time, owner!!.userId, SENDING, owner?.avatar)
        sendMessage(message.copy(id = getTempMessageId()),exception)
    }

    fun getTempMessageId() = count?.get()?:"0"


}