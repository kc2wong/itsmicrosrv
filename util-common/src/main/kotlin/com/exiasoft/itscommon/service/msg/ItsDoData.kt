package com.exiasoft.itscommon.service.msg

import com.thoughtworks.xstream.annotations.XStreamImplicit

class ItsDoData<T> {
    @XStreamImplicit
    var content: List<T> = emptyList()

    constructor(content: List<T>) {
        this.content = content
    }

    constructor()
}

