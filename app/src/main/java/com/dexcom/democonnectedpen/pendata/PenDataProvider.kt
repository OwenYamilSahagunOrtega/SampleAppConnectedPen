package com.dexcom.democonnectedpen.pendata

import com.dexcom.insulinpen.controller.IBasePenController
import com.dexcom.insulinpen.models.InsulinPenDose

class PenDataProvider {
    companion object{
        private var insulinPenInfoList = mutableListOf<IBasePenController>()
        private var insulinPenDoseList = emptyList<InsulinPenDose>()

        fun setInsulinPenInfoList(registeredDevices : MutableMap<String, IBasePenController>) : Boolean {
            var isAddInsulinPenInfo = false
            registeredDevices.forEach { (uuid, controller) ->
                if (!insulinPenInfoList.filter { it.info.uuid == uuid }.any()) {
                    isAddInsulinPenInfo = insulinPenInfoList.add(controller)
                }
            }
            return isAddInsulinPenInfo
        }

        fun getInsulinPenInfoList() : MutableList<IBasePenController> {
            return insulinPenInfoList
        }

        fun getInsulinPenDoseList() : List<InsulinPenDose> {
            return insulinPenDoseList
        }

        fun setInsulinPenDoseList(penDoses : List<InsulinPenDose>) {
            insulinPenDoseList = penDoses
        }
    }
}