package com.dexcom.democonnectedpen.pendata

import com.dexcom.insulinpen.models.InsulinPenInfo

class PenDataProvider {
    companion object{
        var PenDataList = mutableListOf<InsulinPenInfo>()
    }
}