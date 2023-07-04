
import { createStore } from 'vuex'


let fakeAyaRobot = {
    name: "Aya",
    prompt: [
        {type: "System", content: "This is a System prompt"},
        {type: "Assistant", content: "This is a Assistant prompt"},
        {type: "User", content: "This is a User prompt"}
    ],
    greet: "Hi I'am Aya! A beautiful girl of 16 years old"
}

let fakeBobRobot = {
    name: "Bob",
    prompt: [
        {type: "System", content: "This is a System prompt"},
        {type: "Assistant", content: "This is a Assistant prompt"},
        {type: "User", content: "This is a User prompt"}
    ],
    greet: "Hi I'am Bob! A beautiful girl of 16 years old"
}

let robots = [fakeAyaRobot, fakeBobRobot]

let fakeSkill = {
    name: "default",
    description: "",
    content: "this is a skill config default"
}

let skills = [fakeSkill]

export const store = createStore({
    state() {
        return {
            robots,
            skills,
            curRobot: "Aya",
            curSkill: "default",
        }
    }, 

    mutations: {
        setCurRobot(newRobot) {
            state.curRobot = newRobot
        },

        setCurSkill(newSkill) {
            state.curSkill = newSkill
        }
    }
})
