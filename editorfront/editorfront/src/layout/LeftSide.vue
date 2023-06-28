<script setup lang="ts">
import { ref, useAttrs, onMounted } from 'vue'
// import { ElMenu, ElMenuItem, ElSubmenu } from 'element3';

const attrs = useAttrs()
const emit = defineEmits(['robotChange', 'skillChange'])
defineProps()

const robotPrefix: string = "robot-";
const skillPrefix: string = "skill-";

onMounted(()=>{
    console.log(attrs.hello)
});

function handleOpen(index: string) {
    console.log("Open " + index);
}

function handleClose(index: string) {
    console.log("Close " + index);
}

function handleSelect(index: string) {
    if (index.startsWith(robotPrefix)) {
        emit('robotChange', index.substring(robotPrefix.length))
    } else if (index.startsWith(skillPrefix)) {
        emit('skillChange', index.substring(skillPrefix.length))
    } else {
        
    }
    console.log(index + " Selected")
}

let isCollapse = ref(false)

let fakeData = {
    bots: [{name: "Aya"}, {name: "Bob"}],
    skills: [{name: "DefaultSkill"}]
};

</script>

<template>
    <el-menu
        class="el-menu-vertical-demo"
        @open="handleOpen"
        @close="handleClose"
        @select="handleSelect"
        :collapse="isCollapse">

        <el-submenu index="robots">
            <template v-slot:title>
                <i class="el-icon-user"></i>
                <span>Robots</span>
            </template>
            <el-menu-item v-for="bot in fakeData.bots" :index="'robot-' + bot.name">{{ bot.name }}</el-menu-item>
        </el-submenu>

        <el-submenu index="skills">
            <template v-slot:title>
                <i class="el-icon-cpu"></i>
                <span>Skills</span>
            </template>
            <el-menu-item v-for="skill in fakeData.skills" :index="'skill-' + skill.name">{{ skill.name }}</el-menu-item>
        </el-submenu>
    </el-menu>
</template>

<style scoped>
.el-menu-item {
    background-color: #eee;
}
</style>