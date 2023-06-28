
<script setup lang="ts">

import { ref } from 'vue'
import PromptEditor from './PromptEditor.vue';

const formRef = ref({})
const robotData = { 
    name: 'Aya', 
    greeting: 'Hello I am your teacher ... ',
}

function inputRobotNameChange(robotName: string) {
    console.log(robotName)
}

function inputValueTest(value: string) {
    console.log(value)
}

</script>

<template>
    <el-form ref="formRef">
        <el-form-item label="Robot Name">
            <el-input v-mode="robotData.name" clearable @input="inputRobotNameChange"></el-input>
        </el-form-item>
        <el-form-item label="Robot Greeting">
            <el-input v-mode="robotData.greeting" clearable @input="inputValueTest"></el-input>
        </el-form-item>
        <el-form-item>
            <PromptEditor prompt=""></PromptEditor>
        </el-form-item>
    </el-form>

</template>

<style scoped>

</style>