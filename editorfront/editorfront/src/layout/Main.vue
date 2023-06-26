<script setup lang="ts">

import { ref, reactive } from 'vue'
import { ElContainer, ElHeader, ElAside, ElMain } from 'element3';
import LeftSide from './LeftSide.vue'
import TopMenu from './TopMenu.vue'
import ContentView from './ContentView.vue'

let msg = ref('Hello');

let curRobot: string | none = null 
let curSkill: string | none = null


function onRobotChange(robot: string) {
  curRobot = robot
  console.log(robot)
}

function onSkillChange(skill: string) {
  curSkill = skill
  console.log(skill)
}

</script>

<template>
    <el-container>
        <el-header>
            <TopMenu></TopMenu>
        </el-header>
        <el-aside>
            <LeftSide @robot-change="onRobotChange" @skill-change="onSkillChange" hello="Hello world"/>
        </el-aside>
        <el-container style="width: 100%; height: 100%;">
            <el-main robot=curRobot skill=curSkill>
                <ContentView></ContentView>
            </el-main>
        </el-container>
    </el-container>
</template>

<style>
  .el-header,
  .el-footer {
    background-color: #b3c0d1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #d3dce6;
    color: #333;
    text-align: center;
    line-height: 30px;
    height: 100vh;
  }

  .el-main {
    background-color: #e9eef3;
    color: #333;
    text-align: center;
    line-height: 160px;
    height: 100%;
  }

  body > .el-container {
    margin-bottom: 40px;
    width: 100%;
    height: 100vh;
  }

</style>