
import { reactive } from 'vue'

export const store = reactive({
    curRobot: null,
    setCurRobot(newRobot) {
        this.curRobot = newRobot
    },

    curSkill: null,
    setCurSkill(newSkill) {
        this.curSkill = newSkill
    }
})