package com.exiasoft.itscommon.service.msg

import com.thoughtworks.xstream.annotations.XStreamAlias

class ItsMsgBody<T> {
    @XStreamAlias("DO-DATA")
    lateinit var doData: ItsDoData<T>

    constructor(doData: ItsDoData<T>) {
        this.doData = doData
    }

    constructor()
}

