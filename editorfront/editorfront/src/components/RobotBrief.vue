
<script setup lang="ts">

import { ref } from 'vue'
import { ElButton, ElInput } from 'element3';

const formRef = ref(null)
const robotData = { 
    name: 'Aya', 
    greeting: 'Hello I am your teacher ... ',
}

</script>

<template>

    <el-form ref="formRef">
        <el-form-item label="RobotName">
            <el-input v-mode="robotData.name"></el-input>
        </el-form-item>
    </el-form>

</template>

<style scoped>

</style>